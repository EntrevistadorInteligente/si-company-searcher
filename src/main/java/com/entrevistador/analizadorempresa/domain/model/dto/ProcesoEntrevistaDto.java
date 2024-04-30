package com.entrevistador.analizadorempresa.domain.model.dto;

import com.entrevistador.analizadorempresa.domain.model.enums.EstadoEntrevistaEnum;
import lombok.Builder;

@Builder
public class ProcesoEntrevistaDto {
    private String uuid;
    private EstadoEntrevistaEnum estado;
    private String fuente;
    private String error;
}
