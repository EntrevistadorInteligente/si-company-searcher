package com.entrevistador.analizadorempresa.infrastructure.adapter.dao;

import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.Interview;
import com.entrevistador.analizadorempresa.domain.port.InformacionEmpresaDao;
import com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.AnalizadorEmpresaMapper;
import com.entrevistador.analizadorempresa.infrastructure.adapter.repository.AnalizadorEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InformacionEmpresaBdDao implements InformacionEmpresaDao {
    private final AnalizadorEmpresaRepository analizadorEmpresaRepository;
    private final AnalizadorEmpresaMapper mapper;

    @Override
    public Mono<InformacionEmpresa> create(InformacionEmpresa informacionEmpresa,
                                           List<Interview> interviews) {
        return Mono.just(this.mapper.mapInInformacionEmpresaEntity(informacionEmpresa, interviews))
                .flatMap(this.analizadorEmpresaRepository::save)
                .map(this.mapper::mapOutInformacionEmpresa);
    }
}
