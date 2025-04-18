package com.entrevistador.analizadorempresa.domain.port.repository;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

public interface WhatsappMessageRepository {
    /**
     * Guarda un mensaje de WhatsApp en la base de datos
     * 
     * @param message Mensaje a guardar
     * @return Mono con el mensaje guardado
     */
    Mono<WhatsappMessage> save(WhatsappMessage message);
    
    /**
     * Obtiene mensajes no procesados de la base de datos
     * 
     * @return Flux con los mensajes no procesados
     */
    Flux<WhatsappMessage> findUnprocessedMessages();
    
    /**
     * Se suscribe a nuevos mensajes que lleguen a la base de datos
     * 
     * @return Flux con los nuevos mensajes
     */
    Flux<WhatsappMessage> subscribeToNewMessages();
    
    /**
     * Marca mensajes como procesados en la base de datos
     * 
     * @param messageIds Lista de IDs de mensajes a marcar como procesados
     * @return Mono que completa cuando todos los mensajes han sido marcados
     */
    Mono<Void> markAsProcessed(List<String> messageIds);
} 