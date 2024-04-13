package com.entrevistador.analizadorempresa.infrastructure.beanconfiguration;

import com.entrevistador.analizadorempresa.domain.service.CrearAnalizadorEmpresaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesBeanConfiguration {
    @Bean
    public CrearAnalizadorEmpresaService feedbackConstruccionService() {
        return new CrearAnalizadorEmpresaService();
    }
}
