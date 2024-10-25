package com.entrevistador.analizadorempresa.infrastructure.adapter.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMensajeAnalizadorOutput {
    @JsonProperty("proceso_entrevista")
    private KafkaProcesoEntrevistaOutput procesoEntrevista;
    @JsonProperty("id_entrevista")
    private String idEntrevista;
    @JsonProperty("id_informacion_empresa_rag")
    private String idInformacionEmpresaRag;
}
