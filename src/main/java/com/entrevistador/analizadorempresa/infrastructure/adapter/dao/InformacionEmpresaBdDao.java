package com.entrevistador.analizadorempresa.infrastructure.adapter.dao;

import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.domain.port.InformacionEmpresaDao;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InformacionEmpresaEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.repository.AnalizadorEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InformacionEmpresaBdDao implements InformacionEmpresaDao {
    private final AnalizadorEmpresaRepository analizadorEmpresaRepository;

    private final List<String> preguntas = new ArrayList<>() {{
        add("¿Qué diferencias se encuentran entre interfaces y clases?");
        add("¿Qué problemas se pueden encontrar dentro de la multi herencia?");
        add("¿Por qué se necesitan métodos por defecto y pueden éstos anular un método Object?");
        add("¿Cómo se pueden encontrar duplicados en una base de datos relacional utilizando SQL?");
    }};

    @Override
    public Mono<InformacionEmpresaDto> create(InformacionEmpresaDto informacionEmpresaDto) {
        return Mono.just(informacionEmpresaDto)
                .map(informacionEmpresa -> InformacionEmpresa.builder()
                        .empresa(informacionEmpresa.getEmpresa())
                        .perfil(informacionEmpresa.getPerfil())
                        .seniority(informacionEmpresa.getSeniority())
                        .pais(informacionEmpresa.getPais())
                        .informacionEmpresaVect(preguntas)
                        .build())
                .doOnNext(informacionEmpresa -> InformacionEmpresa.validatePrice(informacionEmpresa.getEmpresa()))
                .doOnNext(informacionEmpresa -> InformacionEmpresa.validateStock(informacionEmpresa.getInformacionEmpresaVect()))
                .map(informacionEmpresa -> InformacionEmpresaEntity.builder()
                        .empresa(informacionEmpresa.getEmpresa())
                        .perfil(informacionEmpresa.getPerfil())
                        .seniority(informacionEmpresa.getSeniority())
                        .pais(informacionEmpresa.getPais())
                        .informacionEmpresaVect(informacionEmpresa.getInformacionEmpresaVect())
                        .build())
                .flatMap(this.analizadorEmpresaRepository::save)
                .map(informacionEmpresaEntity -> InformacionEmpresaDto.builder()
                        .idInformacionEmpresaRag(informacionEmpresaEntity.getUuid())
                        .empresa(informacionEmpresaDto.getEmpresa())
                        .perfil(informacionEmpresaDto.getPerfil())
                        .seniority(informacionEmpresaDto.getSeniority())
                        .pais(informacionEmpresaDto.getPais())
                        .build());
    }
}
