package com.entrevistador.analizadorempresa.infrastructure.beanconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesBeanConfiguration {
    @Bean
    public CrearInvestigarEmpresaService feedbackConstruccionService(InformacionEmpresaBdDao informacionEmpresaBdDao,
                                                                     EntrevistaElasticsearch entrevistaElasticsearch) {
        return new CrearInvestigarEmpresaService(informacionEmpresaBdDao, entrevistaElasticsearch);
    }
}
