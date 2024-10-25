package com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.out;

import com.entrevistador.analizadorempresa.domain.model.Entrevista;
import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.Pregunta;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InformacionEmpresaEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.PreguntaEntity;
import com.entrevistador.analizadorempresa.utils.AnalizadorEmpresaMock;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class InformacionEmpresaBdDaoMapperTest {
    private final InformacionEmpresaBdDaoMapper mapper = Mappers.getMapper(InformacionEmpresaBdDaoMapper.class);

    @Test
    void testMapInInformacionEmpresaEntity() throws IOException {
        InformacionEmpresa informacionEmpresa = AnalizadorEmpresaMock.getInstance().getInformacionEmpresa();
        List<Entrevista> entrevistas = AnalizadorEmpresaMock.getInstance().getInterviews();
        InformacionEmpresaEntity informacionEmpresaEntity = this.mapper
                .mapInInformacionEmpresaEntity(informacionEmpresa, entrevistas);

        assertNotNull(informacionEmpresaEntity);
        assertNotNull(informacionEmpresaEntity.getEmpresa());
        assertNotNull(informacionEmpresaEntity.getPerfil());
        assertNotNull(informacionEmpresaEntity.getSeniority());
        assertNotNull(informacionEmpresaEntity.getPais());
        assertNotNull(informacionEmpresaEntity.getDescripcionVacante());
        assertNotNull(informacionEmpresaEntity.getInformacionEmpresaVect());
        assertNotNull(informacionEmpresaEntity.getInformacionEmpresaVect().get(0).getTitulo());
        assertNotNull(informacionEmpresaEntity.getInformacionEmpresaVect().get(0).getNombreEmpresa());
        assertNotNull(informacionEmpresaEntity.getInformacionEmpresaVect().get(0).getDetalleAdicional());
        assertNotNull(informacionEmpresaEntity.getInformacionEmpresaVect().get(0).getPreguntas());
        assertNotNull(informacionEmpresaEntity.getInformacionEmpresaVect().get(0).getDetallesExperiencia());
        assertNotNull(informacionEmpresaEntity.getInformacionEmpresaVect().get(0).getDetallesExperiencia().get(0));
        assertNotNull(informacionEmpresaEntity.getInformacionEmpresaVect().get(0).getDetallesExperiencia().get(1));
        assertNotNull(informacionEmpresaEntity.getInformacionEmpresaVect().get(0).getPreguntas().get(0));
        assertNotNull(informacionEmpresaEntity.getInformacionEmpresaVect().get(0).getPreguntas().get(0).getTitulo());
        assertNotNull(informacionEmpresaEntity.getInformacionEmpresaVect().get(0).getPreguntas().get(0).getDescripcion());
    }

    @Test
    void mapInEntrevistaEntity() throws IOException {
        Entrevista entrevista = AnalizadorEmpresaMock.getInstance().getInterviews().get(0);
        EntrevistaEntity entrevistaEntity = this.mapper.mapInEntrevistaEntity(entrevista);

        assertNotNull(entrevistaEntity);
        assertNotNull(entrevistaEntity.getTitulo());
        assertNotNull(entrevistaEntity.getNombreEmpresa());
        assertNotNull(entrevistaEntity.getDetalleAdicional());
        assertNotNull(entrevistaEntity.getDetallesExperiencia());
        assertNotNull(entrevistaEntity.getDetallesExperiencia().get(0));
        assertNotNull(entrevistaEntity.getDetallesExperiencia().get(1));
        assertNotNull(entrevistaEntity.getPreguntas());
        assertNotNull(entrevistaEntity.getPreguntas().get(0).getTitulo());
        assertNotNull(entrevistaEntity.getPreguntas().get(0).getDescripcion());
    }

    @Test
    void mapInPreguntaEntity() throws IOException {
        Pregunta pregunta = AnalizadorEmpresaMock.getInstance().getQuestion();
        PreguntaEntity preguntaEntity = this.mapper.mapInPreguntaEntity(pregunta);

        assertNotNull(preguntaEntity);
        assertNotNull(preguntaEntity.getTitulo());
        assertNotNull(preguntaEntity.getDescripcion());
    }
}