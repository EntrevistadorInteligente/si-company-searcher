package com.entrevistador.analizadorempresa.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDto {
    @JsonProperty("nombreEmpresa")
    private String nombreEmpresa;
    @JsonProperty("tituloOferta")
    private String tituloOferta;
    @JsonProperty("detalleAdicional")
    private String detalleAdicional;
    @JsonProperty("puntuacion")
    private Double puntuacion;
    @JsonProperty("preguntas")
    private List<QuestionDto> preguntas;
    @JsonProperty("detallesExperiencia")
    private List<String> detallesExperiencia;
}

