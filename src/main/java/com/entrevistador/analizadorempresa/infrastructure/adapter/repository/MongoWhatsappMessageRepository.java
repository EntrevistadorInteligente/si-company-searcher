package com.entrevistador.analizadorempresa.infrastructure.adapter.repository;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import com.entrevistador.analizadorempresa.domain.port.repository.WhatsappMessageRepository;
import com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.WhatsappMessageDocumentMapper;
import com.entrevistador.analizadorempresa.infrastructure.adapter.repository.document.WhatsappMessageDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MongoWhatsappMessageRepository implements WhatsappMessageRepository {
    
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final WhatsappMessageDocumentMapper mapper;
    private Sinks.Many<WhatsappMessage> messagesSink;
    
    @PostConstruct
    public void init() {
        messagesSink = Sinks.many().multicast().onBackpressureBuffer();
        
        // Configurar Change Stream para detectar nuevos mensajes
        // Crear opciones para filtrar solo operaciones de inserción
        ChangeStreamOptions options = ChangeStreamOptions.builder()
            .filter(Aggregation.newAggregation(
                Aggregation.match(
                    Criteria.where("operationType").is("insert")
                )
            ))
            .build();
        
        // Suscribirse al ChangeStream con las opciones configuradas
        reactiveMongoTemplate.changeStream("whatsappMessages", options, WhatsappMessageDocument.class)
            .doOnNext(event -> {
                WhatsappMessageDocument document = event.getBody();
                if (document != null) {
                    WhatsappMessage message = mapper.toDomain(document);
                    log.debug("Nuevo mensaje detectado en ChangeStream: {}", message.getMessageId());
                    messagesSink.tryEmitNext(message);
                }
            })
            .doOnError(e -> log.error("Error en ChangeStream de mensajes: {}", e.getMessage(), e))
            .subscribe();
    }
    
    @Override
    public Mono<WhatsappMessage> save(WhatsappMessage message) {
        return Mono.just(message)
            .map(mapper::toDocument)
            .flatMap(reactiveMongoTemplate::save)
            .map(mapper::toDomain)
            .doOnSuccess(saved -> log.debug("Mensaje guardado en MongoDB: {}", saved.getMessageId()));
    }
    
    @Override
    public Flux<WhatsappMessage> findUnprocessedMessages() {
        Query query = Query.query(Criteria.where("processed").is(false));
        
        return reactiveMongoTemplate.find(query, WhatsappMessageDocument.class)
            .map(mapper::toDomain)
            .doOnComplete(() -> log.debug("Consulta de mensajes no procesados completada"));
    }
    
    @Override
    public Flux<WhatsappMessage> subscribeToNewMessages() {
        return messagesSink.asFlux();
    }
    
    @Override
    public Mono<Void> markAsProcessed(List<String> messageIds) {
        if (messageIds.isEmpty()) {
            return Mono.empty();
        }
        
        Query query = Query.query(Criteria.where("messageId").in(messageIds));
        Update update = Update.update("processed", true);
        
        return reactiveMongoTemplate.updateMulti(query, update, WhatsappMessageDocument.class)
            .doOnSuccess(result -> log.debug("Marcados {} mensajes como procesados", result.getModifiedCount()))
            .then();
    }
} 