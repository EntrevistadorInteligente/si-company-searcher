package com.entrevistador.analizadorempresa.infrastructure.beanconfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;

@Configuration
public class ElasticConfiguration extends ReactiveElasticsearchConfiguration {
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("https://elasticsearch.pruebas-entrevistador-inteligente.site:443")
                .build();
    }
}
