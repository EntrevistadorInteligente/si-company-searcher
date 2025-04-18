package com.entrevistador.analizadorempresa.domain.port.kafka;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import reactor.core.publisher.Mono;

public interface WhatsappPublisher {
    
    /**
     * Publica un mensaje de WhatsApp en el topic de mensajes entrantes
     * 
     * @param message Mensaje a publicar
     * @return Mono que completa cuando el mensaje ha sido publicado
     */
    Mono<Void> publishIncomingMessage(WhatsappMessage message);
} 