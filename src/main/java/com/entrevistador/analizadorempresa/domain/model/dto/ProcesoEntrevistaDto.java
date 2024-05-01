package com.entrevistador.analizadorempresa.domain.model.dto;

import com.entrevistador.analizadorempresa.domain.model.enums.EstadoEntrevistaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
