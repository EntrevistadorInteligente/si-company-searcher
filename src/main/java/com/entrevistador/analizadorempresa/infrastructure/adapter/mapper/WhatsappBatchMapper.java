package com.entrevistador.analizadorempresa.infrastructure.adapter.mapper;

import com.entrevistador.analizadorempresa.domain.model.WhatsappBatch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WhatsappBatchMapper {
    
    private final ObjectMapper objectMapper;
    
    /**
     * Convierte un lote de mensajes a una cadena JSON
     * 
     * @param batch Lote de mensajes a convertir
     * @return Cadena JSON que representa el lote
     * @throws JsonProcessingException si ocurre un error en la serialización
     */
    public String toJsonString(WhatsappBatch batch) throws JsonProcessingException {
        return objectMapper.writeValueAsString(batch);
    }
    
    /**
     * Convierte una cadena JSON a un lote de mensajes
     * 
     * @param json Cadena JSON a convertir
     * @return Lote de mensajes
     * @throws JsonProcessingException si ocurre un error en la deserialización
     */
    public WhatsappBatch fromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, WhatsappBatch.class);
    }
} 