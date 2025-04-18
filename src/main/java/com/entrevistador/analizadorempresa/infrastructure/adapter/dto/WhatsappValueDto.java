package com.entrevistador.analizadorempresa.infrastructure.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WhatsappValueDto {
    @JsonProperty("messaging_product")
    private String messagingProduct;
    
    @JsonProperty("metadata")
    private WhatsappMetadataDto metadata;
    
    @JsonProperty("contacts")
    private List<WhatsappContactDto> contacts;
    
    @JsonProperty("messages")
    private List<WhatsappTextMessageDto> messages;
} 