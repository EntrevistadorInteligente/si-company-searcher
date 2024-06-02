package com.entrevistador.analizadorempresa.domain.service;

import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.domain.model.dto.MensajeAnalizadorEmpresaDto;
import com.entrevistador.analizadorempresa.domain.model.dto.PosicionEntrevistaDto;
import com.entrevistador.analizadorempresa.domain.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.analizadorempresa.domain.model.enums.EstadoEntrevistaEnum;
import com.entrevistador.analizadorempresa.domain.port.InformacionEmpresaDao;
import com.entrevistador.analizadorempresa.domain.port.InformacionEmpresaDaoPostgres;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CrearInvestigarEmpresaService {
    private static final String ANALIZADOR_EMPRESA = "ANALIZADOR_EMPRESA";

    //private final InformacionEmpresaDao informacionEmpresaDao;
    private final InformacionEmpresaDaoPostgres informacionEmpresaDaoPostgres;

    public Mono<MensajeAnalizadorEmpresaDto> create(PosicionEntrevistaDto posicionEntrevista) {
        return this.informacionEmpresaDaoPostgres.create(posicionEntrevista.getFormulario())
                .zipWith(Mono.just(posicionEntrevista), this::crearMensajeAnalizadorEmpresa);
    }

    private MensajeAnalizadorEmpresaDto crearMensajeAnalizadorEmpresa(InformacionEmpresaDto infoEmpresaDto,
                                                                      PosicionEntrevistaDto posicionEntrevistaDto) {
        ProcesoEntrevistaDto procesoEntrevista = this.crearProcesoEntrevistaDto(posicionEntrevistaDto);
        return MensajeAnalizadorEmpresaDto.builder()
                .procesoEntrevista(procesoEntrevista)
                .idEntrevista(posicionEntrevistaDto.getIdEntrevista())
                .idInformacionEmpresaRag(infoEmpresaDto.getIdInformacionEmpresaRag())
                .build();
    }

    private ProcesoEntrevistaDto crearProcesoEntrevistaDto(PosicionEntrevistaDto posicionEntrevistaDto) {
        return ProcesoEntrevistaDto.builder()
                .uuid(posicionEntrevistaDto.getEventoEntrevistaId())
                .estado(EstadoEntrevistaEnum.FN)
                .fuente(ANALIZADOR_EMPRESA)
                .build();
    }
}
