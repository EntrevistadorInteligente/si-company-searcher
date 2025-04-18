package com.entrevistador.analizadorempresa.infrastructure.adapter.repository;

import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.WhatsappMessageEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Repositorio ReactiveMongoRepository para entidades de mensaje de WhatsApp
 * Define solo las operaciones necesarias para la implementación del DAO
 */
@Repository
public interface WhatsappMessegeRepository extends ReactiveMongoRepository<WhatsappMessageEntity, String> {
    
    /**
     * Busca mensajes no procesados, ordenados por fecha de recepción ascendente
     */
    Flux<WhatsappMessageEntity> findByProcessedFalseOrderByReceivedAtAsc();
}