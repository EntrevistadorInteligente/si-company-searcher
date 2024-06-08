package com.entrevistador.analizadorempresa.infrastructure.adapter.repository;

import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InterviewEntity;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface InterviewRepository extends ReactiveElasticsearchRepository<InterviewEntity, String> {

    @Query("""
    {
        "bool": {
            "must": [
                { "term": { "company_name": "?0" } },
                {
                    "bool": {
                        "should": [
                            ?1
                        ],
                        "minimum_should_match": 1
                    }
                }
            ]
        }
    }
    """)
    Flux<InterviewEntity> findByCompanyName(String companyName, String multiMatchQueries);
}
