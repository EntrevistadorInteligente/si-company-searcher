package com.entrevistador.analizadorempresa.application.usecases;

import com.entrevistador.analizadorempresa.domain.model.dto.AnalizadorEmpresaDto;
import reactor.core.publisher.Mono;

public interface AnalizadorEmpresa {
    Mono<AnalizadorEmpresaDto> crear(AnalizadorEmpresaDto analizadorEmpresaDto);
}
