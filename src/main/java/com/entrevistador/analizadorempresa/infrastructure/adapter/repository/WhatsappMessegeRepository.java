package com.entrevistador.analizadorempresa.infrastructure.adapter.repository;

import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.WhatsappMessageEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface WhatsappMessegeRepository extends ReactiveMongoRepository<WhatsappMessageEntity, String> {

    /**
     * Busca mensajes por ID de remitente, ordenados por fecha de recepción descendente
     */
    Flux<WhatsappMessageEntity> findBySenderIdOrderByReceivedAtDesc(String senderId);

    /**
     * Busca mensajes no procesados, ordenados por fecha de recepción ascendente
     */
    Flux<WhatsappMessageEntity> findByProcessedFalseOrderByReceivedAtAsc();

    /**
     * Cuenta los mensajes no procesados
     */
    Mono<Long> countByProcessedFalse();

    /**
     * Busca mensajes no procesados recibidos antes de una fecha determinada
     */
    Flux<WhatsappMessageEntity> findByReceivedAtBeforeAndProcessedFalse(LocalDateTime cutoffTime);

    /**
     * Busca un mensaje por su ID de mensaje
     */
    Mono<WhatsappMessageEntity> findByMessageId(String messageId);
}