package com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.out;

import com.entrevistador.analizadorempresa.domain.valueobject.MensajeAnalizadorEmpresa;
import com.entrevistador.analizadorempresa.infrastructure.adapter.dto.out.KafkaMensajeAnalizadorOutput;
import com.entrevistador.analizadorempresa.utils.AnalizadorEmpresaMock;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class KafkaPublisherMapperTest {
    private final KafkaPublisherMapper mapper = Mappers.getMapper(KafkaPublisherMapper.class);

    @Test
    void mapOutKafkaMensajeAnalizadorOutput() throws IOException {
        MensajeAnalizadorEmpresa mensajeAnalizadorEmpresa = AnalizadorEmpresaMock.getInstance().getMensajeAnalizadorEmpresa();
        KafkaMensajeAnalizadorOutput kafkaMensajeAnalizadorOutput = this.mapper.mapOutKafkaMensajeAnalizadorOutput(mensajeAnalizadorEmpresa);

        assertNotNull(kafkaMensajeAnalizadorOutput);
        assertNotNull(kafkaMensajeAnalizadorOutput.getIdEntrevista());
        assertNotNull(kafkaMensajeAnalizadorOutput.getIdInformacionEmpresaRag());
        assertNotNull(kafkaMensajeAnalizadorOutput.getProcesoEntrevista());
        assertNotNull(kafkaMensajeAnalizadorOutput.getProcesoEntrevista().getUuid());
        assertNotNull(kafkaMensajeAnalizadorOutput.getProcesoEntrevista().getEstado());
        assertNotNull(kafkaMensajeAnalizadorOutput.getProcesoEntrevista().getFuente());
        assertNotNull(kafkaMensajeAnalizadorOutput.getProcesoEntrevista().getError());
    }
}