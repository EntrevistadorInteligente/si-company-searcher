package com.entrevistador.analizadorempresa.infrastructure.adapter.mapper;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import com.entrevistador.analizadorempresa.infrastructure.adapter.dto.WhatsappTextMessageDto;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.WhatsappMessageEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class WhatsappMessageMapper {
    
    private final ObjectMapper objectMapper;
    
    public WhatsappMessageEntity toEntity(WhatsappMessage domain) {
        return WhatsappMessageEntity.builder()
                .id(domain.getId())
                .messageId(domain.getMessageId())
                .senderId(domain.getSenderId())
                .textContent(domain.getTextContent())
                .rawPayload(domain.getRawPayload())
                .receivedAt(domain.getReceivedAt())
                .processed(domain.isProcessed())
                .processingStatus(domain.getProcessingStatus())
                .errorDetails(domain.getErrorDetails())
                .processedAt(domain.getProcessedAt())
                .build();
    }
    
    public WhatsappMessage toDomain(WhatsappMessageEntity entity) {
        return WhatsappMessage.builder()
                .id(entity.getId())
                .messageId(entity.getMessageId())
                .senderId(entity.getSenderId())
                .textContent(entity.getTextContent())
                .rawPayload(entity.getRawPayload())
                .receivedAt(entity.getReceivedAt())
                .processed(entity.isProcessed())
                .processingStatus(entity.getProcessingStatus())
                .errorDetails(entity.getErrorDetails())
                .processedAt(entity.getProcessedAt())
                .build();
    }
    
    public WhatsappMessage fromMessageDto(WhatsappTextMessageDto dto, String rawPayload) {
        String messageContent = "";
        if (dto.getText() != null) {
            messageContent = dto.getText().getBody();
        }
        
        return WhatsappMessage.builder()
                .messageId(dto.getId())
                .senderId(dto.getFrom())
                .textContent(messageContent)
                .rawPayload(rawPayload)
                .receivedAt(LocalDateTime.now())
                .processed(false)
                .processingStatus("PENDING")
                .build();
    }
    
    /**
     * Convierte un mensaje a una cadena JSON
     * 
     * @param message Mensaje a convertir
     * @return Cadena JSON que representa el mensaje
     * @throws JsonProcessingException si ocurre un error en la serialización
     */
    public String toJsonString(WhatsappMessage message) throws JsonProcessingException {
        return objectMapper.writeValueAsString(message);
    }
    
    /**
     * Convierte una cadena JSON a un mensaje
     * 
     * @param json Cadena JSON a convertir
     * @return Mensaje
     * @throws JsonProcessingException si ocurre un error en la deserialización
     */
    public WhatsappMessage fromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, WhatsappMessage.class);
    }
} 