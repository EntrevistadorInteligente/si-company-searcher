package com.entrevistador.analizadorempresa.utils;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Explanation;
import org.springframework.data.elasticsearch.core.document.NestedMetaData;

import java.util.List;
import java.util.Map;

public class CustomSearchHit<T> extends SearchHit<T> {
    public CustomSearchHit(String index, String id, String routing, float score, Object[] sortValues, Map<String,
            List<String>> highlightFields, Map<String, SearchHits<?>> innerHits, NestedMetaData nestedMetaData,
                           Explanation explanation, List<String> matchedQueries, T content) {
        super(index, id, routing, score, sortValues, highlightFields, innerHits, nestedMetaData, explanation, matchedQueries, content);
    }

    public CustomSearchHit(T content, float score) {
        super(null, null, null, score, null, null, null,
                null, null, null, content);
    }
}
