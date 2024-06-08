package com.entrevistador.analizadorempresa.domain.port;

import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InterviewEntity;
import reactor.core.publisher.Flux;

public interface EntrevistaElasticsearch {

    Flux<InterviewEntity> obtenerEntrevistas(InformacionEmpresaDto analizadorEmpresaDto);
}
