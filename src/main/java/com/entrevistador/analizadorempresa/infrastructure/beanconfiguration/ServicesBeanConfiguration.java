package com.entrevistador.analizadorempresa.infrastructure.beanconfiguration;

import com.entrevistador.analizadorempresa.domain.service.CrearInvestigarEmpresaService;
import com.entrevistador.analizadorempresa.infrastructure.adapter.dao.InformacionEmpresaBdDao;
import com.entrevistador.analizadorempresa.infrastructure.adapter.dao.InformacionEmpresaBdDaoPostgres;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesBeanConfiguration {
    @Bean
    public CrearInvestigarEmpresaService feedbackConstruccionService(InformacionEmpresaBdDaoPostgres informacionEmpresaBdDao) {
        return new CrearInvestigarEmpresaService(informacionEmpresaBdDao);
    }
}
