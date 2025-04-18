package com.entrevistador.analizadorempresa.application.service;

import com.entrevistador.analizadorempresa.application.usecases.ProcesarMensajeWhatsapp;
import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import com.entrevistador.analizadorempresa.domain.port.kafka.WhatsappPublisher;
import com.entrevistador.analizadorempresa.domain.port.service.ProcesarWhatsappMessagePort;
import com.entrevistador.analizadorempresa.domain.service.CrearProcesarMensajeWhatsappService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcesarMensajeWhatsappService implements ProcesarMensajeWhatsapp {

    private final CrearProcesarMensajeWhatsappService crearProcesarMensajeWhatsappService;
    private final WhatsappPublisher whatsappPublisher;
    private final ProcesarWhatsappMessagePort procesarWhatsappMessagePort;

    @Override
    public Mono<WhatsappMessage> ejecutar(WhatsappMessage message) {
        log.info("Ejecutando caso de uso: procesar mensaje WhatsApp ID={}, sender={}", 
                message.getMessageId(), message.getSenderId());
        
        return Mono.just(message)
                // Paso 1: Crear el mensaje en la base de datos
                .flatMap(crearProcesarMensajeWhatsappService::create)
                // Paso 2: Publicar el mensaje en Kafka
                .flatMap(createdMessage -> whatsappPublisher.publishIncomingMessage(createdMessage)
                        .thenReturn(createdMessage))
                // Paso 3: Procesar el mensaje mediante el adaptador de servicio
                .flatMap(procesarWhatsappMessagePort::process)
                // Paso 4: Marcar el mensaje como procesado
                .flatMap(createdMessage -> crearProcesarMensajeWhatsappService.markAsProcessed(createdMessage))
                .doOnSuccess(result -> log.info("Mensaje WhatsApp procesado correctamente: ID={}", message.getMessageId()))
                .doOnError(error -> log.error("Error al procesar mensaje WhatsApp: ID={}, error={}", 
                        message.getMessageId(), error.getMessage()));
    }

    @Override
    public Mono<Void> reprocesarPendientes() {
        log.info("Ejecutando caso de uso: reprocesar mensajes WhatsApp pendientes");
        
        return whatsappMessageRepository.findByProcessedFalseOrderByReceivedAtAsc()
                .flatMap(this::ejecutar)
                .then()
                .doOnSuccess(v -> log.info("Reprocesamiento de mensajes pendientes completado"));
    }
} 