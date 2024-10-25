package com.entrevistador.analizadorempresa.domain.port;

import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.Entrevista;
import reactor.core.publisher.Mono;

import java.util.List;

public interface InformacionEmpresaDao {
    Mono<InformacionEmpresa> create(InformacionEmpresa informacionEmpresa,
                                    List<Entrevista> entrevistas);
}
