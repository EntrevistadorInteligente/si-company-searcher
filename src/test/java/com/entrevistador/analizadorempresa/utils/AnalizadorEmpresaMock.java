package com.entrevistador.analizadorempresa.utils;

import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.Interview;
import com.entrevistador.analizadorempresa.domain.model.MensajeAnalizadorEmpresa;
import com.entrevistador.analizadorempresa.domain.model.Question;
import com.entrevistador.analizadorempresa.infrastructure.adapter.dto.PosicionEntrevistaDto;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InformacionEmpresaEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalizadorEmpresaMock {

    private static final AnalizadorEmpresaMock INSTANCE = new AnalizadorEmpresaMock();

    private final ObjectMapper mapper = new ObjectMapper();

    public static AnalizadorEmpresaMock getInstance() {
        return INSTANCE;
    }

    public PosicionEntrevistaDto getPosicionEntrevistaDto() throws IOException {
        return this.mapper.readValue(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("mocks/analizadorEmpresa/PosicionEntrevistaDto.json"), PosicionEntrevistaDto.class);
    }

    public InformacionEmpresa getInformacionEmpresa() throws IOException {
        return this.mapper.readValue(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("mocks/analizadorEmpresa/InformacionEmpresa.json"), InformacionEmpresa.class);
    }

    public List<Interview> getInterviews() throws IOException {
        return this.mapper.readValue(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("mocks/analizadorEmpresa/Interviews.json"), new TypeReference<List<Interview>>() {
        });
    }

    public Question getQuestion() throws IOException {
        return this.mapper.readValue(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("mocks/analizadorEmpresa/Question.json"), Question.class);
    }

    public MensajeAnalizadorEmpresa getMensajeAnalizadorEmpresa() throws IOException {
        return this.mapper.readValue(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("mocks/analizadorEmpresa/MensajeAnalizadorEmpresa.json"), MensajeAnalizadorEmpresa.class);
    }

    public InformacionEmpresaEntity getInformacionEmpresaEntity() throws IOException {
        return this.mapper.readValue(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("mocks/analizadorEmpresa/InformacionEmpresaEntity.json"), InformacionEmpresaEntity.class);
    }

    public EntrevistaEntity getEntrevistaEntity() throws IOException {
        return this.mapper.readValue(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("mocks/analizadorEmpresa/EntrevistaEntity.json"), EntrevistaEntity.class);
    }
}
