package com.entrevistador.analizadorempresa.infrastructure.adapter.repository.document;

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
@Document(collection = "whatsappMessages")
public class WhatsappMessageDocument {
    @Id
    private String id;
    private String messageId;
    private String senderId;
    private String textContent;
    private String rawPayload;
    private LocalDateTime receivedAt;
    private boolean processed;
    private String processingStatus;
    private String errorDetails;
    private LocalDateTime processedAt;
} 