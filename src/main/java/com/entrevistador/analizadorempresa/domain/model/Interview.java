package com.entrevistador.analizadorempresa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interview {
    private String nombreEmpresa;
    private String tituloOferta;
    private String detalleAdicional;
    private Double puntuacion;
    private List<Question> preguntas;
    private List<String> detallesExperiencia;
}

