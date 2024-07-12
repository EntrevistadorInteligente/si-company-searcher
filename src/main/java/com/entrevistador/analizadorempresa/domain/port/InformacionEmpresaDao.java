package com.entrevistador.analizadorempresa.domain.port;

import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.domain.model.dto.InterviewDto;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.EntrevistaEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface InformacionEmpresaDao {
    Mono<InformacionEmpresaDto> create(InformacionEmpresaDto analizadorEmpresaDto,
                                       List<InterviewDto> entrevistasDto);
}
