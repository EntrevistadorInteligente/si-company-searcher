package com.entrevistador.analizadorempresa.application.service;

import com.entrevistador.analizadorempresa.application.usecases.ProcesarMensajeWhatsapp;
import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import com.entrevistador.analizadorempresa.domain.port.kafka.WhatsappPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcesarMensajeWhatsappService implements ProcesarMensajeWhatsapp {


    @Override
    public Mono<WhatsappMessage> ejecutar(WhatsappMessage message) {
        log.info("Ejecutando caso de uso: procesar mensaje WhatsApp ID={}, sender={}", 
                message.getMessageId(), message.getSenderId());
        
        return Mono.just(message);
    }

    @Override
    public Mono<Void> reprocesarPendientes() {
        log.info("Ejecutando caso de uso: reprocesar mensajes WhatsApp pendientes");
        
        // Usamos el servicio de dominio para obtener los mensajes pendientes
        return
                Mono.empty();
    }
} 