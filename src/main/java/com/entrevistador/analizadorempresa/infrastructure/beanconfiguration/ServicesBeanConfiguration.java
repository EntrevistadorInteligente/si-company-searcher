package com.entrevistador.analizadorempresa.infrastructure.beanconfiguration;

import com.entrevistador.analizadorempresa.domain.port.repository.WhatsappMessageDao;
import com.entrevistador.analizadorempresa.domain.service.CrearProcesarMensajeWhatsappService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesBeanConfiguration {
    @Bean
    public CrearProcesarMensajeWhatsappService crearProcesarMensajeWhatsappService(WhatsappMessageDao whatsappMessageDao) {
        return new CrearProcesarMensajeWhatsappService(whatsappMessageDao);
    }
}
