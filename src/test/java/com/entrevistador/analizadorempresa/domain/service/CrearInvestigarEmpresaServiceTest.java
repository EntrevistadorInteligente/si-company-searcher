package com.entrevistador.analizadorempresa.domain.service;

import com.entrevistador.analizadorempresa.domain.model.Entrevista;
import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.valueobject.MensajeAnalizadorEmpresa;
import com.entrevistador.analizadorempresa.domain.valueobject.PosicionEntrevista;
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

import java.util.List;

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

        Entrevista entrevista1 = Entrevista.builder()
                .puntuacion(80.0)
                .build();

        Entrevista entrevista2 = Entrevista.builder()
                .puntuacion(50.0)
                .build();

        List<Entrevista> entrevistas = List.of(entrevista1, entrevista2);

        when(this.informacionEmpresaDao.create(any(), anyList())).thenReturn(Mono.just(informacionEmpresaDto));
        when(this.entrevistaElasticsearch.obtenerEntrevistasPorRepo(any())).thenReturn(Flux.fromIterable(entrevistas));

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