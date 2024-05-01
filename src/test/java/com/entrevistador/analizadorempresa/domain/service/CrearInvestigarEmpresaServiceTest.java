package com.entrevistador.analizadorempresa.domain.service;

import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.domain.model.dto.MensajeAnalizadorEmpresaDto;
import com.entrevistador.analizadorempresa.domain.model.dto.PosicionEntrevistaDto;
import com.entrevistador.analizadorempresa.domain.port.InformacionEmpresaDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrearInvestigarEmpresaServiceTest {
    @InjectMocks
    private CrearInvestigarEmpresaService crearInvestigarEmpresaService;
    @Mock
    private InformacionEmpresaDao informacionEmpresaDao;

    @Test
    void testCreate() {
        InformacionEmpresaDto informacionEmpresaDto = InformacionEmpresaDto.builder()
                .empresa("empresa")
                .idInformacionEmpresaRag("idInformacionEmpresaRag")
                .descripcionVacante("descripcionVacante")
                .pais("pais")
                .perfil("perfil")
                .seniority("seniority")
                .build();
        when(this.informacionEmpresaDao.create(any())).thenReturn(Mono.just(informacionEmpresaDto));

        Mono<MensajeAnalizadorEmpresaDto> publisher = this.crearInvestigarEmpresaService.create(PosicionEntrevistaDto.builder()
                        .formulario(informacionEmpresaDto)
                        .idEntrevista("idEntrevista")
                .eventoEntrevistaId("eventoEntrevistaId")
                .build());

        verify(this.informacionEmpresaDao, times(1)).create(any());

    }
}