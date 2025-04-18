package com.entrevistador.analizadorempresa.infrastructure.services;

import com.entrevistador.analizadorempresa.infrastructure.adapter.dto.WhatsappTextMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class WhatsappMessageService {
    
    // Aquí inyectarías los servicios necesarios, por ejemplo para obtener el estado de conversación
    // private final ConversationRepository conversationRepository;
    
    /**
     * Procesa un mensaje entrante de WhatsApp
     * 
     * @param message El mensaje a procesar
     * @param isIgnored Si el mensaje debe ser ignorado
     * @return Mono que completa cuando el procesamiento finaliza
     */
    public Mono<Void> processIncomingMessage(WhatsappTextMessageDto message, boolean isIgnored) {
        String messageId = message.getId();
        String sender = message.getFrom();
        
        log.info("Procesando mensaje entrante: ID={}, From={}", messageId, sender);
        
        if (isIgnored) {
            log.debug("Mensaje ID={} ignorado por estado de conversación", messageId);
            return Mono.empty();
        }
        
        // Aquí iría la lógica de procesamiento del mensaje
        // Por ejemplo, verificar el tipo de mensaje y procesarlo según corresponda
        
        return Mono.empty(); // Por ahora solo devolvemos un Mono vacío
    }
    
    /**
     * Obtiene el estado de una conversación para un remitente específico
     * 
     * @param sender El ID del remitente
     * @return Mono con true si la conversación debe ser ignorada, false en caso contrario
     */
    public Mono<Boolean> getConversationIgnoredState(String sender) {
        // Aquí consultarías el repositorio para obtener el estado
        // return conversationRepository.getConversationState(sender)
        //         .map(state -> state.isIgnored())
        //         .defaultIfEmpty(false);
        
        // Por ahora, devolvemos false por defecto
        return Mono.just(false);
    }
} 