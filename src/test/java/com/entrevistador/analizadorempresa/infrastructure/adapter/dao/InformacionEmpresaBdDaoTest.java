package com.entrevistador.analizadorempresa.infrastructure.adapter.dao;

import com.entrevistador.analizadorempresa.domain.model.Entrevista;
import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.Pregunta;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InformacionEmpresaEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.out.InformacionEmpresaBdDaoMapper;
import com.entrevistador.analizadorempresa.infrastructure.adapter.repository.AnalizadorEmpresaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

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
    @Mock
    private InformacionEmpresaBdDaoMapper mapper;

    @Test
    void testCreate() {
        InformacionEmpresaEntity informacionEmpresaEntity = InformacionEmpresaEntity.builder().empresa("any").build();

        InformacionEmpresa informacionEmpresa = InformacionEmpresa.builder()
                .empresa("any")
                .build();
        Pregunta pregunta = Pregunta.builder().build();
        List<Entrevista> entrevistas = List.of(Entrevista.builder().preguntas(List.of(pregunta)).build());

        when(this.mapper.mapInInformacionEmpresaEntity(any(), any())).thenReturn(informacionEmpresaEntity);
        when(this.analizadorEmpresaRepository.save(any())).thenReturn(Mono.just(informacionEmpresaEntity));
        when(this.mapper.mapOutInformacionEmpresa(any())).thenReturn(informacionEmpresa);

        Mono<InformacionEmpresa> publisher = this.informacionEmpresaBdDao.create(informacionEmpresa, entrevistas);

        StepVerifier
                .create(publisher)
                .assertNext(informacion -> assertEquals(informacionEmpresa.getEmpresa(), informacion.getEmpresa()))
                .verifyComplete();

        verify(this.mapper, times(1)).mapInInformacionEmpresaEntity(any(), any());
        verify(this.analizadorEmpresaRepository, times(1)).save(any());
        verify(this.mapper, times(1)).mapOutInformacionEmpresa(any());
    }
}