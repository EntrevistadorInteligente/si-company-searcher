package com.entrevistador.analizadorempresa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MensajeAnalizadorEmpresa {
    private ProcesoEntrevista procesoEntrevista;
    private String idEntrevista;
    private String idInformacionEmpresaRag;
}
