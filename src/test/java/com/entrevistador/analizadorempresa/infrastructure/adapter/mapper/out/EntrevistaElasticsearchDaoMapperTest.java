package com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.out;

import com.entrevistador.analizadorempresa.domain.model.Entrevista;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.analizadorempresa.utils.AnalizadorEmpresaMock;
import com.entrevistador.analizadorempresa.utils.CustomSearchHit;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EntrevistaElasticsearchDaoMapperTest {
    private final EntrevistaElasticsearchDaoMapper mapper = Mappers.getMapper(EntrevistaElasticsearchDaoMapper.class);

    @Test
    void mapOutInterview() throws IOException {
        EntrevistaEntity entrevistaEntity = AnalizadorEmpresaMock.getInstance().getEntrevistaEntity();
        CustomSearchHit<EntrevistaEntity> searchHit = new CustomSearchHit<>(entrevistaEntity, 1.0f);
        Entrevista entrevista = this.mapper.mapOutInterview(entrevistaEntity, searchHit);

        assertNotNull(entrevista);
        assertNotNull(entrevista.getTitulo());
        assertNotNull(entrevista.getNombreEmpresa());
        assertNotNull(entrevista.getDetalleAdicional());
        assertNotNull(entrevista.getPuntuacion());
        assertNotNull(entrevista.getPreguntas());
        assertNotNull(entrevista.getPreguntas().get(0));
        assertNotNull(entrevista.getPreguntas().get(0).getTitulo());
        assertNotNull(entrevista.getPreguntas().get(0).getDescripcion());
        assertNotNull(entrevista.getDetallesExperiencia());
        assertNotNull(entrevista.getDetallesExperiencia().get(0));
        assertNotNull(entrevista.getDetallesExperiencia().get(1));
        assertNotNull(entrevista.getDetallesExperiencia().get(2));
        assertNotNull(entrevista.getDetallesExperiencia().get(3));
    }

}