package com.entrevistador.analizadorempresa.application.service;

import com.entrevistador.analizadorempresa.application.usecases.AnalizadorEmpresa;
import com.entrevistador.analizadorempresa.domain.model.dto.AnalizadorEmpresaDto;
import com.entrevistador.analizadorempresa.domain.port.AnalizadorEmpresaDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AnalizadorEmpresaService implements AnalizadorEmpresa {
    private final AnalizadorEmpresaDao analizadorEmpresaDao;

    @Override
    public Mono<AnalizadorEmpresaDto> crear(AnalizadorEmpresaDto analizadorEmpresaDto) {
        return this.analizadorEmpresaDao.crear(analizadorEmpresaDto);
    }
}
