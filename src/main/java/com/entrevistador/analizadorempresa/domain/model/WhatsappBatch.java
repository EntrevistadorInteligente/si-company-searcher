package com.entrevistador.analizadorempresa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WhatsappBatch {
    private String id;
    private String senderId;
    private LocalDateTime windowStart;
    private LocalDateTime windowEnd;
    private List<WhatsappMessage> messages;
    private boolean processed;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
} 