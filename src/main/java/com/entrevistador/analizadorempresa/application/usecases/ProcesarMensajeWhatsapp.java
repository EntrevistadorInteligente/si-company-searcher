package com.entrevistador.analizadorempresa.application.usecases;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para procesar mensajes entrantes de WhatsApp
 */
public interface ProcesarMensajeWhatsapp {
    
    /**
     * Procesa un mensaje entrante de WhatsApp
     * - Lo persiste en la base de datos
     * - Lo publica en Kafka para su procesamiento asíncrono
     * 
     * @param message Mensaje a procesar
     * @return Mensaje procesado
     */
    Mono<WhatsappMessage> ejecutar(WhatsappMessage message);
    
    /**
     * Reprocesa mensajes pendientes que no pudieron ser publicados
     * 
     * @return Mono que completa cuando todos los mensajes han sido reprocesados
     */
    Mono<Void> reprocesarPendientes();
} 