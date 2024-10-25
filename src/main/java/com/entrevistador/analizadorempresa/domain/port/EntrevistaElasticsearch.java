package com.entrevistador.analizadorempresa.domain.port;

import com.entrevistador.analizadorempresa.domain.model.Entrevista;
import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import reactor.core.publisher.Flux;

public interface EntrevistaElasticsearch {
    Flux<Entrevista> obtenerEntrevistasPorRepo(InformacionEmpresa informacionEmpresa);
}
