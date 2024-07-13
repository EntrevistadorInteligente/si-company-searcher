package com.entrevistador.analizadorempresa.infrastructure.adapter.dto;

import com.entrevistador.analizadorempresa.domain.model.enums.EstadoEntrevistaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcesoEntrevistaDto {
    private String uuid;
    private EstadoEntrevistaEnum estado;
    private String fuente;
    private String error;
}
