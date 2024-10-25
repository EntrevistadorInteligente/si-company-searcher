package com.entrevistador.analizadorempresa.infrastructure.adapter.dao;

import com.entrevistador.analizadorempresa.domain.model.Entrevista;
import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.port.InformacionEmpresaDao;
import com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.out.InformacionEmpresaBdDaoMapper;
import com.entrevistador.analizadorempresa.infrastructure.adapter.repository.AnalizadorEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InformacionEmpresaBdDao implements InformacionEmpresaDao {
    private final AnalizadorEmpresaRepository analizadorEmpresaRepository;
    private final InformacionEmpresaBdDaoMapper mapper;

    @Override
    public Mono<InformacionEmpresa> create(InformacionEmpresa informacionEmpresa,
                                           List<Entrevista> entrevistas) {
        return Mono.just(this.mapper.mapInInformacionEmpresaEntity(informacionEmpresa, entrevistas))
                .flatMap(this.analizadorEmpresaRepository::save)
                .map(this.mapper::mapOutInformacionEmpresa);
    }
}
