package com.entrevistador.analizadorempresa.infrastructure.adapter.dto;

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
    private String nombreEmpresa;
    private String tituloOferta;
    private String detalleAdicional;
    private Double puntuacion;
    private List<QuestionDto> preguntas;
    private List<String> detallesExperiencia;
}

