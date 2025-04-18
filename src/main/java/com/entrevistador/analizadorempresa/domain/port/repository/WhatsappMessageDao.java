package com.entrevistador.analizadorempresa.domain.port.repository;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto para acceder a operaciones de persistencia de mensajes de WhatsApp
 * Expone solo los métodos necesarios para la lógica de dominio
 */
public interface WhatsappMessageDao {
    
    /**
     * Guarda un mensaje de WhatsApp
     * 
     * @param message El mensaje a guardar
     * @return El mensaje guardado
     */
    Mono<WhatsappMessage> save(WhatsappMessage message);
    
    /**
     * Busca mensajes no procesados ordenados por fecha de recepción
     * Útil para procesar mensajes pendientes en orden de llegada
     * 
     * @return Flujo de mensajes no procesados
     */
    Flux<WhatsappMessage> findByProcessedFalseOrderByReceivedAtAsc();
    
    /**
     * Busca un mensaje por su ID
     * 
     * @param id El ID del mensaje
     * @return El mensaje encontrado
     */
    Mono<WhatsappMessage> findById(String id);
} 