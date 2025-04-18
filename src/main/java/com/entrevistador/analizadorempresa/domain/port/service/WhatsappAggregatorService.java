package com.entrevistador.analizadorempresa.domain.port.service;

import reactor.core.publisher.Mono;

public interface WhatsappAggregatorService {
    /**
     * Inicia el servicio de agregación de mensajes de WhatsApp
     * 
     * @return Mono que completa cuando el servicio ha iniciado
     */
    Mono<Void> startAggregation();
    
    /**
     * Detiene el servicio de agregación de mensajes de WhatsApp
     * 
     * @return Mono que completa cuando el servicio ha detenido
     */
    Mono<Void> stopAggregation();
} 