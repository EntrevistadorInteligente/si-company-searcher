package com.entrevistador.analizadorempresa.infrastructure.adapter.repository;

import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InformacionEmpresaEntityPostgres;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AnalizadorEmpresaRepositoryPostgres extends ReactiveCrudRepository<InformacionEmpresaEntityPostgres, Long> {
    @Query("SELECT preguntas_en_la_entrevista FROM interviews WHERE nombre_empresa ~* '(?<![a-zA-Z])%:name%(?![a-zA-Z])'")
    Mono<String> findByCompanyName(String name);
}
