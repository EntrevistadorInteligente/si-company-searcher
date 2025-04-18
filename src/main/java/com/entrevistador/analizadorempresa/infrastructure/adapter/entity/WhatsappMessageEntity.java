package com.entrevistador.analizadorempresa.infrastructure.adapter.entity;

import com.entrevistador.analizadorempresa.infrastructure.adapter.dto.WhatsappValueDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "whatsapp_messages")
public class WhatsappMessageEntity {
    
    @Id
    private String id;
    
    private String messageId;
    private String senderId;
    private String textContent;
    private String rawPayload;
    private WhatsappValueDto messageValue;
    private LocalDateTime receivedAt;
    private boolean processed;
    private String processingStatus; // PENDING, PROCESSED, ERROR
    private String errorDetails;
    private LocalDateTime processedAt;
} 