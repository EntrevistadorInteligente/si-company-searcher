package com.entrevistador.analizadorempresa.domain.valueobject;

import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * Modelo que sirve como Value-Object para recibir informacion de empresaListenerTopic
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosicionEntrevista {
    private String idEntrevista;
    private String eventoEntrevistaId;
    private InformacionEmpresa formulario;
}
