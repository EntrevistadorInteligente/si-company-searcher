package com.entrevistador.analizadorempresa.infrastructure.beanconfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;

import java.time.Duration;

@Configuration
public class ElasticConfiguration extends ReactiveElasticsearchConfiguration {
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("elasticsearch.pruebas-entrevistador-inteligente.site:9200")
                .withConnectTimeout(Duration.ofSeconds(5))
                .build();
    }
}
