package com.entrevistador.analizadorempresa.domain.port.service;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import reactor.core.publisher.Mono;

/**
 * Puerto para el servicio de procesamiento de mensajes de WhatsApp
 */
public interface ProcesarWhatsappMessagePort {
    
    /**
     * Procesa un mensaje de WhatsApp
     * 
     * @param message El mensaje a procesar
     * @return El mensaje procesado
     */
    Mono<WhatsappMessage> process(WhatsappMessage message);
} 