package com.entrevistador.analizadorempresa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * Clase que representa el dominio de PreguntaEntity
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pregunta {
    private String titulo;
    private String descripcion;
}

