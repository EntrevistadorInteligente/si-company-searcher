package com.entrevistador.analizadorempresa.utils;

import com.entrevistador.analizadorempresa.domain.model.Entrevista;
import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.valueobject.MensajeAnalizadorEmpresa;
import com.entrevistador.analizadorempresa.domain.model.Pregunta;
import com.entrevistador.analizadorempresa.infrastructure.adapter.dto.in.KafkaPosicionEntrevistaInput;
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

    public KafkaPosicionEntrevistaInput getPosicionEntrevistaDto() throws IOException {
        return this.mapper.readValue(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("mocks/analizadorEmpresa/KafkaPosicionEntrevistaInput.json"), KafkaPosicionEntrevistaInput.class);
    }

    public InformacionEmpresa getInformacionEmpresa() throws IOException {
        return this.mapper.readValue(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("mocks/analizadorEmpresa/InformacionEmpresa.json"), InformacionEmpresa.class);
    }

    public List<Entrevista> getInterviews() throws IOException {
        return this.mapper.readValue(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("mocks/analizadorEmpresa/Entrevista.json"), new TypeReference<List<Entrevista>>() {
        });
    }

    public Pregunta getQuestion() throws IOException {
        return this.mapper.readValue(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("mocks/analizadorEmpresa/Pregunta.json"), Pregunta.class);
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
