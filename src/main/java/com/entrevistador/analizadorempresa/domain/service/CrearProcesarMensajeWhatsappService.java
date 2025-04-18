package com.entrevistador.analizadorempresa.domain.service;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import com.entrevistador.analizadorempresa.domain.port.repository.WhatsappMessageDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Servicio de dominio para crear y procesar mensajes de WhatsApp
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CrearProcesarMensajeWhatsappService {
    
    private final WhatsappMessageDao whatsappMessageDao;
    
    /**
     * Crea un mensaje de WhatsApp en la base de datos
     * 
     * @param message El mensaje a crear
     * @return El mensaje creado
     */
    public Mono<WhatsappMessage> create(WhatsappMessage message) {
        log.info("Creando mensaje de WhatsApp: ID={}, sender={}", 
                message.getMessageId(), message.getSenderId());
        
        return whatsappMessageDao.save(message)
                .doOnSuccess(savedMessage -> log.info("Mensaje de WhatsApp guardado correctamente: ID={}", 
                        savedMessage.getMessageId()))
                .doOnError(error -> log.error("Error al guardar mensaje de WhatsApp: ID={}, error={}", 
                        message.getMessageId(), error.getMessage()));
    }
    
    /**
     * Marca un mensaje como procesado en la base de datos
     * 
     * @param message El mensaje a marcar como procesado
     * @return El mensaje actualizado
     */
    public Mono<WhatsappMessage> markAsProcessed(WhatsappMessage message) {
        log.info("Marcando mensaje de WhatsApp como procesado: ID={}", message.getMessageId());
        
        WhatsappMessage processedMessage = WhatsappMessage.builder()
                .id(message.getId())
                .messageId(message.getMessageId())
                .senderId(message.getSenderId())
                .textContent(message.getTextContent())
                .rawPayload(message.getRawPayload())
                .receivedAt(message.getReceivedAt())
                .processed(true)
                .processingStatus("PROCESSED")
                .processedAt(LocalDateTime.now())
                .build();
        
        return whatsappMessageDao.save(processedMessage)
                .doOnSuccess(updatedMessage -> log.info("Mensaje de WhatsApp marcado como procesado: ID={}", 
                        updatedMessage.getMessageId()))
                .doOnError(error -> log.error("Error al marcar mensaje de WhatsApp como procesado: ID={}, error={}", 
                        message.getMessageId(), error.getMessage()));
    }
} 