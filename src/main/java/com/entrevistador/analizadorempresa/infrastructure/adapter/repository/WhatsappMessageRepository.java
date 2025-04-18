package com.entrevistador.analizadorempresa.infrastructure.adapter.repository;

import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.WhatsappMessageEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface WhatsappMessageRepository extends ReactiveMongoRepository<WhatsappMessageEntity, String> {
    
    Flux<WhatsappMessageEntity> findBySenderIdOrderByReceivedAtDesc(String senderId);
    
    Flux<WhatsappMessageEntity> findByProcessedFalseOrderByReceivedAtAsc();
    
    Mono<Long> countByProcessedFalse();
    
    Flux<WhatsappMessageEntity> findByReceivedAtBeforeAndProcessedFalse(LocalDateTime cutoffTime);
    
    Mono<WhatsappMessageEntity> findByMessageId(String messageId);
} 