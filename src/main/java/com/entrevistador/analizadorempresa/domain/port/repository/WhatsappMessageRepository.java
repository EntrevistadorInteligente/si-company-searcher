package com.entrevistador.analizadorempresa.domain.port.repository;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface WhatsappMessageRepository {
    
    Mono<WhatsappMessage> save(WhatsappMessage message);
    
    Flux<WhatsappMessage> findBySenderIdOrderByReceivedAtDesc(String senderId);
    
    Flux<WhatsappMessage> findByProcessedFalseOrderByReceivedAtAsc();
    
    Mono<Long> countByProcessedFalse();
    
    Flux<WhatsappMessage> findByReceivedAtBeforeAndProcessedFalse(LocalDateTime cutoffTime);
    
    Mono<WhatsappMessage> findByMessageId(String messageId);
    
    Mono<WhatsappMessage> findById(String id);
} 