package com.entrevistador.analizadorempresa.domain.port.kafka;

import com.entrevistador.analizadorempresa.domain.model.WhatsappBatch;
import reactor.core.publisher.Mono;

public interface WhatsappBatchPublisher {
    
    /**
     * Publica un lote de mensajes de WhatsApp en el topic de mensajes agrupados
     * 
     * @param batch Lote de mensajes a publicar
     * @return Mono que completa cuando el lote ha sido publicado
     */
    Mono<Void> publishBatchedMessages(WhatsappBatch batch);
} 