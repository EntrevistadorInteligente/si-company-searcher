package com.entrevistador.analizadorempresa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WhatsappMessage {
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