package com.entrevistador.analizadorempresa.domain.service;

import com.entrevistador.analizadorempresa.domain.model.WhatsappBatch;
import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import com.entrevistador.analizadorempresa.domain.port.kafka.WhatsappBatchPublisher;
import com.entrevistador.analizadorempresa.domain.port.repository.WhatsappMessageRepository;
import com.entrevistador.analizadorempresa.domain.port.service.WhatsappAggregatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class WhatsappAggregatorServiceImpl implements WhatsappAggregatorService {

    private final WhatsappMessageRepository messageRepository;
    private final WhatsappBatchPublisher batchPublisher;
    
    @Value("${aggregator.window.seconds:30}")
    private int windowSeconds;
    
    private Disposable aggregationTask;
    private final Map<String, Sinks.Many<WhatsappMessage>> senderSinks = new ConcurrentHashMap<>();
    
    @PostConstruct
    public void init() {
        startAggregation().subscribe();
    }
    
    @PreDestroy
    public void cleanup() {
        stopAggregation().subscribe();
    }
    
    @Override
    public Mono<Void> startAggregation() {
        if (aggregationTask != null && !aggregationTask.isDisposed()) {
            return Mono.empty();
        }
        
        log.info("Iniciando servicio de agregación de mensajes con ventana de {} segundos", windowSeconds);
        
        // Consultar mensajes no procesados de la base de datos
        aggregationTask = messageRepository.findUnprocessedMessages()
            .doOnNext(message -> {
                log.debug("Procesando mensaje no procesado: {}", message.getMessageId());
                routeMessageToSenderSink(message);
            })
            .thenMany(messageRepository.subscribeToNewMessages())
            .doOnNext(message -> {
                log.debug("Recibido nuevo mensaje: {}", message.getMessageId());
                routeMessageToSenderSink(message);
            })
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe();
            
        return Mono.empty();
    }
    
    @Override
    public Mono<Void> stopAggregation() {
        if (aggregationTask != null && !aggregationTask.isDisposed()) {
            log.info("Deteniendo servicio de agregación de mensajes");
            aggregationTask.dispose();
            
            // Cerrar todos los sinks
            senderSinks.values().forEach(sink -> {
                sink.tryEmitComplete();
            });
            senderSinks.clear();
        }
        return Mono.empty();
    }
    
    private void routeMessageToSenderSink(WhatsappMessage message) {
        String senderId = message.getSenderId();
        Sinks.Many<WhatsappMessage> senderSink = senderSinks.computeIfAbsent(senderId, 
            id -> createSenderSink(id));
        
        senderSink.tryEmitNext(message);
    }
    
    private Sinks.Many<WhatsappMessage> createSenderSink(String senderId) {
        Sinks.Many<WhatsappMessage> sink = Sinks.many().unicast().onBackpressureBuffer();
        
        sink.asFlux()
            .bufferTimeout(100, Duration.ofSeconds(windowSeconds))
            .filter(messages -> !messages.isEmpty())
            .flatMap(messages -> processBatch(senderId, messages))
            .doOnComplete(() -> {
                log.info("Sink para el remitente {} completado", senderId);
                senderSinks.remove(senderId);
            })
            .doOnError(e -> {
                log.error("Error en el procesamiento de mensajes para el remitente {}: {}", 
                    senderId, e.getMessage(), e);
                senderSinks.remove(senderId);
            })
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe();
            
        return sink;
    }
    
    private Mono<Void> processBatch(String senderId, List<WhatsappMessage> messages) {
        if (messages.isEmpty()) {
            return Mono.empty();
        }
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowStart = now.minusSeconds(windowSeconds);
        
        WhatsappBatch batch = WhatsappBatch.builder()
            .id(UUID.randomUUID().toString())
            .senderId(senderId)
            .windowStart(windowStart)
            .windowEnd(now)
            .messages(messages)
            .processed(false)
            .createdAt(now)
            .build();
            
        log.info("Creando batch para senderId={}: {} mensajes, ventana [{} - {}]", 
            senderId, messages.size(), windowStart, now);
            
        return batchPublisher.publishBatchedMessages(batch)
            .doOnSuccess(v -> {
                log.info("Batch publicado con éxito: batchId={}, senderId={}, mensajes={}", 
                    batch.getId(), senderId, messages.size());
            })
            .doOnError(e -> {
                log.error("Error al publicar batch: batchId={}, senderId={}, error={}", 
                    batch.getId(), senderId, e.getMessage(), e);
            });
    }
} 