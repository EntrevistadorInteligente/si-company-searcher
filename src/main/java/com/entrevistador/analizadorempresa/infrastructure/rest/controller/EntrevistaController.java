package com.entrevistador.analizadorempresa.infrastructure.rest.controller;

import com.entrevistador.analizadorempresa.application.usecases.ProcesarMensajeWhatsapp;
import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import com.entrevistador.analizadorempresa.infrastructure.adapter.dto.WhatsappWebhookDto;
import com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.WhatsappMessageMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Controlador para recibir y procesar webhooks de WhatsApp
 * Esta clase es el punto de entrada a la aplicación y delega la lógica de negocio a los casos de uso
 */
@Slf4j
@RestController
@RequestMapping("/v1/whatsapp_webhook")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class EntrevistaController {

    // Caso de uso de aplicación
    private final ProcesarMensajeWhatsapp procesarMensajeWhatsapp;
    
    // Mapper para convertir DTOs a objetos de dominio
    private final WhatsappMessageMapper whatsappMessageMapper;
    
    // Para serialización/deserialización
    private final ObjectMapper objectMapper;

    @GetMapping(params = {"hub.mode", "hub.verify_token", "hub.challenge"})
    public Mono<Integer> verificarWebhook(
            @RequestParam("hub.mode") String hubMode,
            @RequestParam("hub.verify_token") String hubVerifyToken,
            @RequestParam("hub.challenge") String hubChallenge) {
        
        log.info("Inicio proceso de verificación webhook");
        
        if ("subscribe".equals(hubMode) && "HAPPY".equals(hubVerifyToken)) {
            log.info("Webhook verificado, challenge: {}", hubChallenge);
            return Mono.just(Integer.parseInt(hubChallenge));
        }
        
        log.error("Verificación de webhook fallida");
        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Verificación fallida"));
    }
    
    @PostMapping()
    public Mono<ResponseEntity<Map<String, String>>> recibirMensajeWhatsapp(
            @RequestBody WhatsappWebhookDto webhookDto) {
        // Generar un ID único para esta solicitud de webhook
        String webhookRequestId = UUID.randomUUID().toString();
        log.info("[Webhook {}] Recibida solicitud POST", webhookRequestId);
        
        try {
            // Convertimos el webhook a JSON para persistencia
            String rawPayload = whatsappMessageMapper.toJsonString(webhookDto);
            
            return Mono.defer(() -> {
                log.debug("[Webhook {}] Body recibido: {}", webhookRequestId, webhookDto);
                
                if (webhookDto.getEntry() != null && !webhookDto.getEntry().isEmpty()) {
                    // Procesamos cada entrada usando Flux
                    return Flux.fromIterable(webhookDto.getEntry())
                        .flatMap(entry -> {
                            if (entry.getChanges() != null && !entry.getChanges().isEmpty()) {
                                // Procesamos cada cambio en la entrada
                                return Flux.fromIterable(entry.getChanges())
                                    .flatMap(change -> {
                                        if (change.getValue() != null && change.getValue().getMessages() != null) {
                                            // Procesamos cada mensaje en el cambio
                                            return Flux.fromIterable(change.getValue().getMessages())
                                                .flatMap(message -> {
                                                    String messageId = message.getId();
                                                    String sender = message.getFrom();
                                                    log.info("[Webhook {}] Procesando mensaje entrante: ID={}, From={}", 
                                                             webhookRequestId, messageId, sender);
                                                    
                                                    // Creamos objeto de dominio a partir del DTO
                                                    WhatsappMessage whatsappMessage = whatsappMessageMapper.fromMessageDto(message, rawPayload);
                                                    
                                                    // Invocamos el caso de uso para procesar el mensaje
                                                    return procesarMensajeWhatsapp.ejecutar(whatsappMessage);
                                                });
                                        }
                                        return Mono.empty();
                                    });
                            }
                            return Mono.empty();
                        })
                        .then()
                        .thenReturn(createSuccessResponse(webhookRequestId));
                }
                
                log.info("[Webhook {}] No hay entradas para procesar", webhookRequestId);
                return Mono.just(createSuccessResponse(webhookRequestId));
            })
            .onErrorResume(e -> {
                log.error("[Webhook {}] Error procesando webhook: {}", webhookRequestId, e.getMessage(), e);
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("status", "error");
                errorResponse.put("message", e.getMessage());
                // Siempre devolvemos 200 para evitar reenvíos desde WhatsApp
                return Mono.just(ResponseEntity.ok(errorResponse));
            });
        } catch (Exception e) {
            log.error("[Webhook {}] Error general al procesar webhook: {}", webhookRequestId, e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return Mono.just(ResponseEntity.ok(errorResponse));
        }
    }
    
    @GetMapping("/reprocess")
    public Mono<ResponseEntity<Map<String, String>>> reprocesarMensajesPendientes() {
        log.info("Iniciando reprocesamiento de mensajes pendientes");
        
        return procesarMensajeWhatsapp.reprocesarPendientes()
                .then(Mono.defer(() -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("status", "ok");
                    response.put("message", "Reprocesamiento de mensajes pendientes iniciado");
                    return Mono.just(ResponseEntity.ok(response));
                }))
                .onErrorResume(e -> {
                    log.error("Error al reprocesar mensajes pendientes: {}", e.getMessage(), e);
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("status", "error");
                    errorResponse.put("message", e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
                });
    }
    
    private ResponseEntity<Map<String, String>> createSuccessResponse(String webhookRequestId) {
        log.info("[Webhook {}] Procesamiento completado.", webhookRequestId);
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        return ResponseEntity.ok(response);
    }
}