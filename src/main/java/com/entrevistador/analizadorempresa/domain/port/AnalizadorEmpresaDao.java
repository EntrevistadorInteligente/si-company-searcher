package com.entrevistador.analizadorempresa.domain.port;

import com.entrevistador.analizadorempresa.domain.model.dto.AnalizadorEmpresaDto;
import reactor.core.publisher.Mono;

public interface AnalizadorEmpresaDao {
    Mono<AnalizadorEmpresaDto> crear(AnalizadorEmpresaDto analizadorEmpresaDto);
}
