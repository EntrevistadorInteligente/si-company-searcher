package com.entrevistador.analizadorempresa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * Clase que representa el dominio de EntrevistaEntity
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Entrevista {
    private String id;
    private String nombreEmpresa;
    private String titulo;
    private String detalleAdicional;
    private String descripcionEntrevista;
    private Double puntuacion;
    private List<Pregunta> preguntas;
    private List<String> detallesExperiencia;
}

