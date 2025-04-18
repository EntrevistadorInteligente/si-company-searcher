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
    
    public String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Error al convertir objeto a JSON: {}", e.getMessage());
            return "{}";
        }
    }
} 