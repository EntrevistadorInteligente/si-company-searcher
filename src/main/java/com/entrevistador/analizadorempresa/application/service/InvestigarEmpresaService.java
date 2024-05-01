package com.entrevistador.analizadorempresa.application.service;

import com.entrevistador.analizadorempresa.application.usecases.InvestigarEmpresa;
import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.domain.model.dto.MensajeAnalizadorEmpresaDto;
import com.entrevistador.analizadorempresa.domain.model.dto.PosicionEntrevistaDto;
import com.entrevistador.analizadorempresa.domain.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.analizadorempresa.domain.model.enums.EstadoEntrevistaEnum;
import com.entrevistador.analizadorempresa.domain.service.CrearInvestigarEmpresaService;
import com.entrevistador.analizadorempresa.infrastructure.adapter.jms.JmsPublisherAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InvestigarEmpresaService implements InvestigarEmpresa {
    public static final String ANALIZADOR_EMPRESA = "ANALIZADOR_EMPRESA";

    private final CrearInvestigarEmpresaService crearInvestigarEmpresaService;
    private final JmsPublisherAdapter jmsPublisherAdapter;

    @Override
    public Mono<Void> ejecutar(PosicionEntrevistaDto posicionEntrevistaDto) {
        return this.prepararCreacion(posicionEntrevistaDto)
                .flatMap(this.jmsPublisherAdapter::empresaListenerTopic);
    }

    private Mono<MensajeAnalizadorEmpresaDto> prepararCreacion(PosicionEntrevistaDto posicionEntrevistaDto) {
        return this.crearInvestigarEmpresaService.create(posicionEntrevistaDto.getFormulario())
                .zipWith(Mono.just(posicionEntrevistaDto), this::crearMensajeAnalizadorEmpresa);
    }

    private MensajeAnalizadorEmpresaDto crearMensajeAnalizadorEmpresa(InformacionEmpresaDto infoEmpresaDto, PosicionEntrevistaDto posicionEntrevistaDto) {
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