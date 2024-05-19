package com.entrevistador.analizadorempresa.application.usecases;

import com.entrevistador.analizadorempresa.domain.model.dto.PosicionEntrevistaDto;
import reactor.core.publisher.Mono;

public interface InvestigarEmpresa {
    Mono<Void> ejecutar(PosicionEntrevistaDto posicionEntrevistaDto);
}
