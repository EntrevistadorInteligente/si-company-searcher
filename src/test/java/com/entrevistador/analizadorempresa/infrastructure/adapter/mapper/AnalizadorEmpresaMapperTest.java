package com.entrevistador.analizadorempresa.infrastructure.adapter.mapper;

import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.Interview;
import com.entrevistador.analizadorempresa.domain.model.MensajeAnalizadorEmpresa;
import com.entrevistador.analizadorempresa.domain.model.PosicionEntrevista;
import com.entrevistador.analizadorempresa.domain.model.Question;
import com.entrevistador.analizadorempresa.infrastructure.adapter.dto.MensajeAnalizadorEmpresaDto;
import com.entrevistador.analizadorempresa.infrastructure.adapter.dto.PosicionEntrevistaDto;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InformacionEmpresaEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.PreguntaEntity;
import com.entrevistador.analizadorempresa.utils.AnalizadorEmpresaMock;
import com.entrevistador.analizadorempresa.utils.CustomSearchHit;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AnalizadorEmpresaMapperTest {
    private final AnalizadorEmpresaMapper mapper = Mappers.getMapper(AnalizadorEmpresaMapper.class);

    @Test
    void testMapInPosicionEntrevista() throws IOException {
        PosicionEntrevistaDto posicionEntrevistaDto = AnalizadorEmpresaMock.getInstance().getPosicionEntrevistaDto();
        PosicionEntrevista posicionEntrevista = this.mapper.mapInPosicionEntrevista(posicionEntrevistaDto);

        assertNotNull(posicionEntrevista);
        assertNotNull(posicionEntrevista.getIdEntrevista());
        assertNotNull(posicionEntrevista.getEventoEntrevistaId());
        assertNotNull(posicionEntrevista.getFormulario());
        assertNotNull(posicionEntrevista.getFormulario().getIdInformacionEmpresaRag());
        assertNotNull(posicionEntrevista.getFormulario().getDescripcionVacante());
        assertNotNull(posicionEntrevista.getFormulario().getEmpresa());
        assertNotNull(posicionEntrevista.getFormulario().getPerfil());
        assertNotNull(posicionEntrevista.getFormulario().getSeniority());
        assertNotNull(posicionEntrevista.getFormulario().getPais());
    }

    @Test
    void testMapInInformacionEmpresaEntity() throws IOException {
        InformacionEmpresa informacionEmpresa = AnalizadorEmpresaMock.getInstance().getInformacionEmpresa();
        List<Interview> interviews = AnalizadorEmpresaMock.getInstance().getInterviews();
        InformacionEmpresaEntity informacionEmpresaEntity = this.mapper
                .mapInInformacionEmpresaEntity(informacionEmpresa, interviews);

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
        Interview interview = AnalizadorEmpresaMock.getInstance().getInterviews().get(0);
        EntrevistaEntity entrevistaEntity = this.mapper.mapInEntrevistaEntity(interview);

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
        Question question = AnalizadorEmpresaMock.getInstance().getQuestion();
        PreguntaEntity preguntaEntity = this.mapper.mapInPreguntaEntity(question);

        assertNotNull(preguntaEntity);
        assertNotNull(preguntaEntity.getTitulo());
        assertNotNull(preguntaEntity.getDescripcion());
    }

    @Test
    void mapOutMensajeAnalizadorEmpresa() throws IOException {
        MensajeAnalizadorEmpresa mensajeAnalizadorEmpresa = AnalizadorEmpresaMock.getInstance().getMensajeAnalizadorEmpresa();
        MensajeAnalizadorEmpresaDto mensajeAnalizadorEmpresaDto = this.mapper.mapOutMensajeAnalizadorEmpresa(mensajeAnalizadorEmpresa);

        assertNotNull(mensajeAnalizadorEmpresaDto);
        assertNotNull(mensajeAnalizadorEmpresaDto.getIdEntrevista());
        assertNotNull(mensajeAnalizadorEmpresaDto.getIdInformacionEmpresaRag());
        assertNotNull(mensajeAnalizadorEmpresaDto.getProcesoEntrevista());
        assertNotNull(mensajeAnalizadorEmpresaDto.getProcesoEntrevista().getUuid());
        assertNotNull(mensajeAnalizadorEmpresaDto.getProcesoEntrevista().getEstado());
        assertNotNull(mensajeAnalizadorEmpresaDto.getProcesoEntrevista().getFuente());
        assertNotNull(mensajeAnalizadorEmpresaDto.getProcesoEntrevista().getError());
    }

    @Test
    void mapOutInformacionEmpresa() throws IOException {
        InformacionEmpresaEntity informacionEmpresaEntity = AnalizadorEmpresaMock.getInstance().getInformacionEmpresaEntity();
        InformacionEmpresa informacionEmpresa = this.mapper.mapOutInformacionEmpresa(informacionEmpresaEntity);

        assertNotNull(informacionEmpresa);
        assertNotNull(informacionEmpresa.getIdInformacionEmpresaRag());
        assertNotNull(informacionEmpresa.getEmpresa());
        assertNotNull(informacionEmpresa.getPerfil());
        assertNotNull(informacionEmpresa.getSeniority());
        assertNotNull(informacionEmpresa.getPais());
    }

    @Test
    void mapOutInterview() throws IOException {
        EntrevistaEntity entrevistaEntity = AnalizadorEmpresaMock.getInstance().getEntrevistaEntity();
        CustomSearchHit<EntrevistaEntity> searchHit = new CustomSearchHit<>(entrevistaEntity, 1.0f);
        Interview interview = this.mapper.mapOutInterview(entrevistaEntity, searchHit);

        assertNotNull(interview);
        assertNotNull(interview.getTituloOferta());
        assertNotNull(interview.getNombreEmpresa());
        assertNotNull(interview.getDetalleAdicional());
        assertNotNull(interview.getPuntuacion());
        assertNotNull(interview.getPreguntas());
        assertNotNull(interview.getPreguntas().get(0));
        assertNotNull(interview.getPreguntas().get(0).getTitulo());
        assertNotNull(interview.getPreguntas().get(0).getDescripcion());
        assertNotNull(interview.getDetallesExperiencia());
        assertNotNull(interview.getDetallesExperiencia().get(0));
        assertNotNull(interview.getDetallesExperiencia().get(1));
        assertNotNull(interview.getDetallesExperiencia().get(2));
        assertNotNull(interview.getDetallesExperiencia().get(3));
    }
}