package com.entrevistador.analizadorempresa.infrastructure.rest.controller;

import com.entrevistador.analizadorempresa.infrastructure.adapter.dto.WhatsappWebhookDto;
import com.entrevistador.analizadorempresa.infrastructure.services.WhatsappMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/whatsapp_webhook")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class EntrevistaController {

    private final WhatsappMessageService whatsappMessageService;

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
    
    @PostMapping("/webhook")
    public Mono<ResponseEntity<Map<String, String>>> recibirMensajeWhatsapp(@RequestBody WhatsappWebhookDto webhookDto) {
        // Generar un ID único para esta solicitud de webhook
        String webhookRequestId = UUID.randomUUID().toString();
        log.info("[Webhook {}] Recibida solicitud POST", webhookRequestId);
        
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
                                                
                                                // Obtenemos el estado de la conversación
                                                return whatsappMessageService.getConversationIgnoredState(sender)
                                                    .flatMap(isIgnored -> {
                                                        log.debug("[Webhook {}] Mensaje ID={}: Ignored={}", 
                                                                webhookRequestId, messageId, isIgnored);
                                                        
                                                        // Procesamos el mensaje
                                                        return whatsappMessageService.processIncomingMessage(message, isIgnored);
                                                    });
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
    }
    
    private ResponseEntity<Map<String, String>> createSuccessResponse(String webhookRequestId) {
        log.info("[Webhook {}] Procesamiento completado.", webhookRequestId);
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        return ResponseEntity.ok(response);
    }
}