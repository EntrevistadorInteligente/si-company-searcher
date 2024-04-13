package com.entrevistador.analizadorempresa.infrastructure.adapter.repository;

import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.AnalizadorEmpresaEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AnalizadorEmpresaRepository extends ReactiveMongoRepository<AnalizadorEmpresaEntity, String> {
}
