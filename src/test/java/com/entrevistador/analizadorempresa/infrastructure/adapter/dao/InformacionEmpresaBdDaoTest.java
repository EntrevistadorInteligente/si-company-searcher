package com.entrevistador.analizadorempresa.infrastructure.adapter.dao;

import com.entrevistador.analizadorempresa.domain.exception.PriceIsLessThanOrEqualToZero;
import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InformacionEmpresaEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.repository.AnalizadorEmpresaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InformacionEmpresaBdDaoTest {
    @InjectMocks
    private InformacionEmpresaBdDao informacionEmpresaBdDao;
    @Mock
    private AnalizadorEmpresaRepository analizadorEmpresaRepository;

    @Test
    void testCreate() {
        InformacionEmpresaEntity informacionEmpresaEntity = InformacionEmpresaEntity.builder().empresa("any").build();
        when(this.analizadorEmpresaRepository.save(any())).thenReturn(Mono.just(informacionEmpresaEntity));

        InformacionEmpresaDto informacionEmpresaDto = InformacionEmpresaDto.builder()
                .empresa("any")
                .build();
        Mono<InformacionEmpresaDto> publisher = this.informacionEmpresaBdDao.create(informacionEmpresaDto);

        StepVerifier
                .create(publisher)
                .assertNext(informacion -> assertEquals(informacionEmpresaDto.getEmpresa(), informacion.getEmpresa()))
                .verifyComplete();

        verify(this.analizadorEmpresaRepository, times(1)).save(any());
    }

    @Test
    void testCreatePriceIsLessThanOrEqualToZeroException() {
        Mono<InformacionEmpresaDto> publisher = this.informacionEmpresaBdDao.create(InformacionEmpresaDto.builder().build());

        StepVerifier
                .create(publisher)
                .expectError(PriceIsLessThanOrEqualToZero.class)
                .verify();

        verify(this.analizadorEmpresaRepository, times(0)).save(any());
    }
}