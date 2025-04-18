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
public class WhatsappMetadataDto {
    @JsonProperty("display_phone_number")
    private String displayPhoneNumber;
    
    @JsonProperty("phone_number_id")
    private String phoneNumberId;
} 