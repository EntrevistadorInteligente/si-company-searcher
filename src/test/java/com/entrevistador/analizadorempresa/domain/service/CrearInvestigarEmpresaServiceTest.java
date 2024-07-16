package com.entrevistador.analizadorempresa.domain.service;

import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.MensajeAnalizadorEmpresa;
import com.entrevistador.analizadorempresa.domain.model.PosicionEntrevista;
import com.entrevistador.analizadorempresa.domain.port.EntrevistaElasticsearch;
import com.entrevistador.analizadorempresa.domain.port.InformacionEmpresaDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrearInvestigarEmpresaServiceTest {
    @InjectMocks
    private CrearInvestigarEmpresaService crearInvestigarEmpresaService;
    @Mock
    private InformacionEmpresaDao informacionEmpresaDao;
    @Mock
    private EntrevistaElasticsearch entrevistaElasticsearch;

    @Test
    void testCreate() {
        InformacionEmpresa informacionEmpresaDto = InformacionEmpresa.builder()
                .empresa("empresa")
                .idInformacionEmpresaRag("idInformacionEmpresaRag")
                .descripcionVacante("descripcionVacante")
                .pais("pais")
                .perfil("perfil")
                .seniority("seniority")
                .build();

        PosicionEntrevista posicionEntrevistaDto = PosicionEntrevista.builder()
                .formulario(informacionEmpresaDto)
                .idEntrevista("idEntrevista")
                .eventoEntrevistaId("eventoEntrevistaId")
                .build();

        when(this.informacionEmpresaDao.create(any(), anyList())).thenReturn(Mono.just(informacionEmpresaDto));
        when(this.entrevistaElasticsearch.obtenerEntrevistasPorRepo(any())).thenReturn(Flux.empty());

        Mono<MensajeAnalizadorEmpresa> publisher = this.crearInvestigarEmpresaService.create(posicionEntrevistaDto);

        StepVerifier
                .create(publisher)
                .consumeNextWith(mensajeAnalizadorEmpresaDto -> {
                    assertEquals(informacionEmpresaDto.getIdInformacionEmpresaRag(), mensajeAnalizadorEmpresaDto.getIdInformacionEmpresaRag());
                    assertEquals(posicionEntrevistaDto.getIdEntrevista(), mensajeAnalizadorEmpresaDto.getIdEntrevista());
                    assertEquals(posicionEntrevistaDto.getEventoEntrevistaId(), mensajeAnalizadorEmpresaDto.getProcesoEntrevista().getUuid());
                })
                .verifyComplete();

        verify(this.entrevistaElasticsearch, times(1)).obtenerEntrevistasPorRepo(any());
        verify(this.informacionEmpresaDao, times(1)).create(any(), anyList());
    }
}