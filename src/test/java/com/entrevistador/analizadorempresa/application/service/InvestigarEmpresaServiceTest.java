package com.entrevistador.analizadorempresa.application.service;

import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.domain.model.dto.MensajeAnalizadorEmpresaDto;
import com.entrevistador.analizadorempresa.domain.model.dto.PosicionEntrevistaDto;
import com.entrevistador.analizadorempresa.domain.service.CrearInvestigarEmpresaService;
import com.entrevistador.analizadorempresa.infrastructure.adapter.jms.JmsPublisherAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvestigarEmpresaServiceTest {
    @InjectMocks
    private InvestigarEmpresaService investigarEmpresaService;
    @Mock
    private CrearInvestigarEmpresaService crearInvestigarEmpresaService;
    @Mock
    private JmsPublisherAdapter jmsPublisherAdapter;

    @Test
    void testEjecutar() {
        when(this.crearInvestigarEmpresaService.create(any())).thenReturn(Mono.just(MensajeAnalizadorEmpresaDto.builder().build()));
        when(this.jmsPublisherAdapter.empresaListenerTopic(any())).thenReturn(Mono.empty());

        Mono<Void> publisher = this.investigarEmpresaService.ejecutar(PosicionEntrevistaDto.builder().build());

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.crearInvestigarEmpresaService, times(1)).create(any());
        verify(this.jmsPublisherAdapter, times(1)).empresaListenerTopic(any());
    }
}