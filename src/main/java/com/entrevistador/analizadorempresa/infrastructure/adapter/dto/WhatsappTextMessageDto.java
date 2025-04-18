package com.entrevistador.analizadorempresa.infrastructure.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WhatsappTextMessageDto {
    @JsonProperty("from")
    private String from;
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("timestamp")
    private String timestamp;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("text")
    private WhatsappTextContentDto text;
} 