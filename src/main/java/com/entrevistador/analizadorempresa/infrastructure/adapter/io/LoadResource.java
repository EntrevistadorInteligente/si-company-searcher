package com.entrevistador.analizadorempresa.infrastructure.adapter.io;

import com.entrevistador.analizadorempresa.domain.exception.QueryFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class LoadResource {
    @Value("classpath:elasticsearchqueries/query-with-company.json")
    private Resource queryWithCompanyResource;

    @Value("classpath:elasticsearchqueries/query-without-company.json")
    private Resource queryWithoutCompanyResource;

    public String loadQueryTemplateWithCompany() {
        return this.loadQueryTemplate(queryWithCompanyResource);
    }

    public String loadQueryTemplateWithoutCompany() {
        return this.loadQueryTemplate(queryWithoutCompanyResource);
    }

    private String loadQueryTemplate(Resource resource) {
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new QueryFileException("Failed to load query template", e);
        }
    }
}
