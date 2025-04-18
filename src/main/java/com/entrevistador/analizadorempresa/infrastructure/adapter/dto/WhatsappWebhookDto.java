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
public class WhatsappWebhookDto {
    @JsonProperty("object")
    private String object;
    
    @JsonProperty("entry")
    private List<WhatsappEntryDto> entry;
} 