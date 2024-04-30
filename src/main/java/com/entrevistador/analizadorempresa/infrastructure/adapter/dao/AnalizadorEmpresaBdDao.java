package com.entrevistador.analizadorempresa.infrastructure.adapter.dao;

import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.domain.port.AnalizadorEmpresaDao;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InformacionEmpresaEntityRag;
import com.entrevistador.analizadorempresa.infrastructure.adapter.repository.AnalizadorEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnalizadorEmpresaBdDao implements AnalizadorEmpresaDao {
    private final AnalizadorEmpresaRepository analizadorEmpresaRepository;

    private final List<String> preguntas = new ArrayList<>() {{
        add("¿Qué diferencias se encuentran entre interfaces y clases?");
        add("¿Qué problemas se pueden encontrar dentro de la multi herencia?");
        add("¿Por qué se necesitan métodos por defecto y pueden éstos anular un método Object?");
        add("¿Cómo se pueden encontrar duplicados en una base de datos relacional utilizando SQL?");
    }};

    @Override
    public Mono<InformacionEmpresaDto> create(InformacionEmpresaDto analizadorEmpresaDto) {
        return Mono.just(analizadorEmpresaDto)
                .map(informacionEmpresaDto -> InformacionEmpresa.builder()
                        .empresa(informacionEmpresaDto.getEmpresa())
                        .perfil(informacionEmpresaDto.getPerfil())
                        .seniority(informacionEmpresaDto.getSeniority())
                        .pais(informacionEmpresaDto.getPais())
                        .informacionEmpresaVect(preguntas)
                        .build())
                .doOnNext(informacionEmpresa -> InformacionEmpresa.validatePrice(informacionEmpresa.getEmpresa()))
                .doOnNext(informacionEmpresa -> InformacionEmpresa.validateStock(informacionEmpresa.getInformacionEmpresaVect()))
                .map(informacionEmpresa -> InformacionEmpresaEntityRag.builder()
                        .empresa(informacionEmpresa.getEmpresa())
                        .perfil(informacionEmpresa.getPerfil())
                        .seniority(informacionEmpresa.getSeniority())
                        .pais(informacionEmpresa.getPais())
                        .informacionEmpresaVect(informacionEmpresa.getInformacionEmpresaVect())
                        .build())
                .flatMap(this.analizadorEmpresaRepository::save)
                .map(informacionEmpresaEntityRag -> InformacionEmpresaDto.builder()
                        .idInformacionEmpresaRag(informacionEmpresaEntityRag.getUuid())
                        .empresa(analizadorEmpresaDto.getEmpresa())
                        .perfil(analizadorEmpresaDto.getPerfil())
                        .seniority(analizadorEmpresaDto.getSeniority())
                        .pais(analizadorEmpresaDto.getPais())
                        .preguntas(preguntas)
                        .build());
    }
}
