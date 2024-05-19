package com.entrevistador.analizadorempresa.domain.model.enums;

import lombok.Getter;

@Getter
public enum EstadoEntrevistaEnum {
    AC("AC"),
    CVA("CVA"),
    FN("FN");

    private final String estado;

    EstadoEntrevistaEnum(String estado) {
        this.estado = estado;
    }
}
