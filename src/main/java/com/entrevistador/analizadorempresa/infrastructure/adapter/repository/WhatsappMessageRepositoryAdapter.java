package com.entrevistador.analizadorempresa.infrastructure.adapter.repository;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import com.entrevistador.analizadorempresa.domain.port.repository.WhatsappMessageRepository;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.WhatsappMessageEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.WhatsappMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Repository
@RequiredArgsConstructor
public class WhatsappMessageRepositoryAdapter implements WhatsappMessageRepository {
    
    private final org.springframework.data.mongodb.repository.ReactiveMongoRepository<WhatsappMessageEntity, String> repository;
    private final WhatsappMessageMapper mapper;
    
    @Override
    public Mono<WhatsappMessage> save(WhatsappMessage message) {
        return Mono.just(message)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDomain)
                .doOnSuccess(saved -> log.info("Mensaje de WhatsApp guardado: {}", saved.getMessageId()))
                .doOnError(error -> log.error("Error al guardar mensaje de WhatsApp: {}", error.getMessage()));
    }
    
    @Override
    public Flux<WhatsappMessage> findBySenderIdOrderByReceivedAtDesc(String senderId) {
        return ((com.entrevistador.analizadorempresa.infrastructure.adapter.repository.WhatsappMessageRepository) repository)
                .findBySenderIdOrderByReceivedAtDesc(senderId)
                .map(mapper::toDomain);
    }
    
    @Override
    public Flux<WhatsappMessage> findByProcessedFalseOrderByReceivedAtAsc() {
        return ((com.entrevistador.analizadorempresa.infrastructure.adapter.repository.WhatsappMessageRepository) repository)
                .findByProcessedFalseOrderByReceivedAtAsc()
                .map(mapper::toDomain);
    }
    
    @Override
    public Mono<Long> countByProcessedFalse() {
        return ((com.entrevistador.analizadorempresa.infrastructure.adapter.repository.WhatsappMessageRepository) repository)
                .countByProcessedFalse();
    }
    
    @Override
    public Flux<WhatsappMessage> findByReceivedAtBeforeAndProcessedFalse(LocalDateTime cutoffTime) {
        return ((com.entrevistador.analizadorempresa.infrastructure.adapter.repository.WhatsappMessageRepository) repository)
                .findByReceivedAtBeforeAndProcessedFalse(cutoffTime)
                .map(mapper::toDomain);
    }
    
    @Override
    public Mono<WhatsappMessage> findByMessageId(String messageId) {
        return ((com.entrevistador.analizadorempresa.infrastructure.adapter.repository.WhatsappMessageRepository) repository)
                .findByMessageId(messageId)
                .map(mapper::toDomain);
    }
    
    @Override
    public Mono<WhatsappMessage> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }
} 