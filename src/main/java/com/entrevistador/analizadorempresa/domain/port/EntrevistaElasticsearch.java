package com.entrevistador.analizadorempresa.domain.port;

import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.domain.model.dto.InterviewDto;
import reactor.core.publisher.Flux;

public interface EntrevistaElasticsearch {

    Flux<InterviewDto> obtenerEntrevistasPorRepo(InformacionEmpresaDto analizadorEmpresaDto);
}
