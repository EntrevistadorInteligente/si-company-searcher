package com.entrevistador.analizadorempresa.infrastructure.adapter.repository;

import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InformacionEmpresaEntityRag;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AnalizadorEmpresaRepository extends ReactiveMongoRepository<InformacionEmpresaEntityRag, String> {
}
