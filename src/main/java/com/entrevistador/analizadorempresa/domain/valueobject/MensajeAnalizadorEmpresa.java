package com.entrevistador.analizadorempresa.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * Modelo que sirve como Value-Object para enviar mensajes al empresaListenerTopic
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MensajeAnalizadorEmpresa {
    private ProcesoEntrevista procesoEntrevista;
    private String idEntrevista;
    private String idInformacionEmpresaRag;
}
