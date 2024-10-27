package com.entrevistador.analizadorempresa.infrastructure.adapter.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaPosicionEntrevistaInput {
    @JsonProperty("id_entrevista")
    private String idEntrevista;
    @JsonProperty("evento_entrevista_id")
    private String eventoEntrevistaId;
    @JsonProperty("formulario")
    private KafkaInformacionEmpresaInput formulario;
}
