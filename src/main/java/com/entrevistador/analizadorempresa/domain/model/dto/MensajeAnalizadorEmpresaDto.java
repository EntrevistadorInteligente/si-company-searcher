package com.entrevistador.analizadorempresa.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MensajeAnalizadorEmpresaDto {
    @JsonProperty("proceso_entrevista")
    private ProcesoEntrevistaDto procesoEntrevista;
    @JsonProperty("id_entrevista")
    private String idEntrevista;
    @JsonProperty("id_informacion_empresa_rag")
    private String idInformacionEmpresaRag;
}
