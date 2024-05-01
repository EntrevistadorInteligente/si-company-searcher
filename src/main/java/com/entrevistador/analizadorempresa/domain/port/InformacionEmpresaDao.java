package com.entrevistador.analizadorempresa.domain.port;

import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import reactor.core.publisher.Mono;

public interface InformacionEmpresaDao {
    Mono<InformacionEmpresaDto> create(InformacionEmpresaDto analizadorEmpresaDto);
}
