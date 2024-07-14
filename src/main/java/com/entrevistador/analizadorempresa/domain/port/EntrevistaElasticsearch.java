package com.entrevistador.analizadorempresa.domain.port;

import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.Interview;
import reactor.core.publisher.Flux;

public interface EntrevistaElasticsearch {
    Flux<Interview> obtenerEntrevistasPorRepo(InformacionEmpresa informacionEmpresa);
}
