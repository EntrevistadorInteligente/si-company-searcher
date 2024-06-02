package com.entrevistador.analizadorempresa.domain.port;

import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface InformacionEmpresaDaoPostgres {
    Mono<InformacionEmpresaDto> create(InformacionEmpresaDto analizadorEmpresaDto);
    List<String> obtenerPreguntas(InformacionEmpresaDto informacionEmpresaDto);
}
