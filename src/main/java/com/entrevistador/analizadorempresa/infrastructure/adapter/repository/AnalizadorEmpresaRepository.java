package com.entrevistador.analizadorempresa.infrastructure.adapter.repository;

import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InformacionEmpresaEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AnalizadorEmpresaRepository extends ReactiveMongoRepository<InformacionEmpresaEntity, String> {
}
