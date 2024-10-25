package com.entrevistador.analizadorempresa.application.usecases;

import com.entrevistador.analizadorempresa.domain.valueobject.PosicionEntrevista;
import reactor.core.publisher.Mono;

public interface InvestigarEmpresa {
    Mono<Void> ejecutar(PosicionEntrevista posicionEntrevista);
}
