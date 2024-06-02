package com.entrevistador.analizadorempresa.infrastructure.adapter.dao;

import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.domain.port.InformacionEmpresaDaoPostgres;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InformacionEmpresaEntityPostgres;
import com.entrevistador.analizadorempresa.infrastructure.adapter.repository.AnalizadorEmpresaRepositoryPostgres;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InformacionEmpresaBdDaoPostgres implements InformacionEmpresaDaoPostgres {
    private final AnalizadorEmpresaRepositoryPostgres analizadorEmpresaRepositoryPostgres;

    private final List<String> preguntas = new ArrayList<>() {{
        add("¿Qué diferencias se encuentran entre interfaces y clases?");
        add("¿Qué problemas se pueden encontrar dentro de la multi herencia?");
        add("¿Por qué se necesitan métodos por defecto y pueden éstos anular un método Object?");
        add("¿Cómo se pueden encontrar duplicados en una base de datos relacional utilizando SQL?");
    }};

    public List<String> obtenerPreguntas(InformacionEmpresaDto informacionEmpresaDto){
        String preguntas = this.analizadorEmpresaRepositoryPostgres.findByCompanyName(informacionEmpresaDto.getEmpresa()).block();
        List<String> preguntasLista = Arrays.stream(preguntas.split("P:")).toList();
        return preguntasLista;
    }

    @Override
    public Mono<InformacionEmpresaDto> create(InformacionEmpresaDto informacionEmpresaDto) {
        return Mono.just(informacionEmpresaDto)
                .map(informacionEmpresa -> InformacionEmpresa.builder()
                        .empresa(informacionEmpresa.getEmpresa())
                        .perfil(informacionEmpresa.getPerfil())
                        .seniority(informacionEmpresa.getSeniority())
                        .pais(informacionEmpresa.getPais())
                        .informacionEmpresaVect(this.obtenerPreguntas(informacionEmpresa))
                        .build())
                .doOnNext(informacionEmpresa -> InformacionEmpresa.validatePrice(informacionEmpresa.getEmpresa()))
                .doOnNext(informacionEmpresa -> InformacionEmpresa.validateStock(informacionEmpresa.getInformacionEmpresaVect()))
                .map(informacionEmpresa -> InformacionEmpresaEntityPostgres.builder()
                        .empresa(informacionEmpresa.getEmpresa())
                        .perfil(informacionEmpresa.getPerfil())
                        .seniority(informacionEmpresa.getSeniority())
                        .pais(informacionEmpresa.getPais())
                        .informacionEmpresaVect(informacionEmpresa.getInformacionEmpresaVect())
                        .build())
                .flatMap(this.analizadorEmpresaRepositoryPostgres::save)
                .map(informacionEmpresaEntity -> InformacionEmpresaDto.builder()
                        .idInformacionEmpresaRag(informacionEmpresaEntity.getId().toString())
                        .empresa(informacionEmpresaDto.getEmpresa())
                        .perfil(informacionEmpresaDto.getPerfil())
                        .seniority(informacionEmpresaDto.getSeniority())
                        .pais(informacionEmpresaDto.getPais())
                        .build());
    }
}
