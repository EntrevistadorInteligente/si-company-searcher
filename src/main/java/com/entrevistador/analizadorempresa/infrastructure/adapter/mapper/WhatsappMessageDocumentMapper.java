package com.entrevistador.analizadorempresa.infrastructure.adapter.mapper;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import com.entrevistador.analizadorempresa.infrastructure.adapter.repository.document.WhatsappMessageDocument;
import org.springframework.stereotype.Component;

@Component
public class WhatsappMessageDocumentMapper {

    /**
     * Convierte de modelo de dominio a documento de MongoDB
     * 
     * @param message Mensaje de dominio
     * @return Documento para MongoDB
     */
    public WhatsappMessageDocument toDocument(WhatsappMessage message) {
        return WhatsappMessageDocument.builder()
                .id(message.getId())
                .messageId(message.getMessageId())
                .senderId(message.getSenderId())
                .textContent(message.getTextContent())
                .rawPayload(message.getRawPayload())
                .receivedAt(message.getReceivedAt())
                .processed(message.isProcessed())
                .processingStatus(message.getProcessingStatus())
                .errorDetails(message.getErrorDetails())
                .processedAt(message.getProcessedAt())
                .build();
    }

    /**
     * Convierte de documento de MongoDB a modelo de dominio
     * 
     * @param document Documento de MongoDB
     * @return Mensaje de dominio
     */
    public WhatsappMessage toDomain(WhatsappMessageDocument document) {
        return WhatsappMessage.builder()
                .id(document.getId())
                .messageId(document.getMessageId())
                .senderId(document.getSenderId())
                .textContent(document.getTextContent())
                .rawPayload(document.getRawPayload())
                .receivedAt(document.getReceivedAt())
                .processed(document.isProcessed())
                .processingStatus(document.getProcessingStatus())
                .errorDetails(document.getErrorDetails())
                .processedAt(document.getProcessedAt())
                .build();
    }
} 