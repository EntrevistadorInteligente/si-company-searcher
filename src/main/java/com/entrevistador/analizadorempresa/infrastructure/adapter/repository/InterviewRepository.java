package com.entrevistador.analizadorempresa.infrastructure.adapter.repository;

import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InterviewEntity;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface InterviewRepository extends ReactiveElasticsearchRepository<InterviewEntity, String> {
}
