package com.entrevistador.analizadorempresa.application.usecases;

import com.entrevistador.analizadorempresa.domain.model.PosicionEntrevista;
import reactor.core.publisher.Mono;

public interface InvestigarEmpresa {
    Mono<Void> ejecutar(PosicionEntrevista posicionEntrevistaDto);
}
