package com.entrevistador.analizadorempresa.domain.service;

import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.domain.port.InformacionEmpresaDao;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CrearInvestigarEmpresaService {
    private final InformacionEmpresaDao informacionEmpresaDao;

    public Mono<InformacionEmpresaDto> create(InformacionEmpresaDto informacionEmpresaDto) {
        return this.informacionEmpresaDao.create(informacionEmpresaDto);
    }
}
