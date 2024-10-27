package com.entrevistador.analizadorempresa.domain.valueobject;

import com.entrevistador.analizadorempresa.domain.model.enums.EstadoEntrevistaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Modelo que sirve como Value-Object para enviar mensajes al empresaListenerTopic
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcesoEntrevista {
    private String uuid;
    private EstadoEntrevistaEnum estado;
    private String fuente;
    private String error;
}
