package com.entrevistador.analizadorempresa.infrastructure.adapter.dao;

import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.Entrevista;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.io.LoadResource;
import com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.out.EntrevistaElasticsearchDaoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntrevistaElasticsearchDaoTest {
    @InjectMocks
    private EntrevistaElasticsearchDao entrevistaElasticsearchDao;
    @Mock
    private ReactiveElasticsearchOperations operations;
    @Mock
    private EntrevistaElasticsearchDaoMapper mapper;
    @Mock
    private LoadResource loadResource;

    @Test
    void testObtenerEntrevistasPorRepo() {
        InformacionEmpresa informacionEmpresa = InformacionEmpresa.builder()
                .empresa("empresa")
                .seniority("seniority")
                .perfil("perfil")
                .descripcionVacante("descripcionVacante")
                .build();

        SearchHit<EntrevistaEntity> searchHit = mock(SearchHit.class);

        Entrevista entrevista = Entrevista.builder().build();

        when(this.loadResource.loadQueryTemplateWithCompany()).thenReturn("{COMPANY_NAME} {PROFILE_SENIORITY} {DESCRIPTION_VACANCY}");
        when(this.loadResource.loadQueryTemplateWithoutCompany()).thenReturn("{PROFILE_SENIORITY} {DESCRIPTION_VACANCY}");
        when(this.operations.search(any(), eq(EntrevistaEntity.class)))
                .thenReturn(Flux.just(searchHit));
        when(this.mapper.mapOutInterview(any(), any()))
                .thenReturn(entrevista);

        Flux<Entrevista> publisher = this.entrevistaElasticsearchDao.obtenerEntrevistasPorRepo(informacionEmpresa);

        StepVerifier
                .create(publisher)
                .expectNext(entrevista)
                .verifyComplete();

        verify(this.loadResource, times(1)).loadQueryTemplateWithCompany();
        verify(this.loadResource, times(1)).loadQueryTemplateWithoutCompany();
        verify(this.operations, times(2)).search(any(), eq(EntrevistaEntity.class));
        verify(this.mapper, times(1)).mapOutInterview(any(), any());
    }
}