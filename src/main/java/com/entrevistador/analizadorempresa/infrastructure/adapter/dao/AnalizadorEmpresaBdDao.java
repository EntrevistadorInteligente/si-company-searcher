package com.entrevistador.analizadorempresa.infrastructure.adapter.dao;

import com.entrevistador.analizadorempresa.domain.model.dto.AnalizadorEmpresaDto;
import com.entrevistador.analizadorempresa.domain.port.AnalizadorEmpresaDao;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.AnalizadorEmpresaEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.repository.AnalizadorEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AnalizadorEmpresaBdDao implements AnalizadorEmpresaDao {
    private final AnalizadorEmpresaRepository analizadorEmpresaRepository;

    @Override
    public Mono<AnalizadorEmpresaDto> crear(AnalizadorEmpresaDto analizadorEmpresaDto) {
        return analizadorEmpresaRepository.save(AnalizadorEmpresaEntity.builder()
                        .uuid(analizadorEmpresaDto.getUuid())
                        .build())
                .map(analizadorEmpresaEntity -> AnalizadorEmpresaDto.builder().build());
    }
}
