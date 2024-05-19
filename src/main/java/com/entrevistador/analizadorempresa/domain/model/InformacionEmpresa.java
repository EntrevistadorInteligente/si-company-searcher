package com.entrevistador.analizadorempresa.domain.model;

import com.entrevistador.analizadorempresa.domain.exception.PriceIsLessThanOrEqualToZero;
import com.entrevistador.analizadorempresa.domain.exception.StockIsLessThanOrEqualToZero;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InformacionEmpresa {
    private String empresa;
    private String perfil;
    private String seniority;
    private String pais;
    private String descripcionVacante;
    private List<String> informacionEmpresaVect;

    public static void validatePrice(String idEmpresa) {
        if (idEmpresa == null || idEmpresa.isEmpty()) {
            throw new PriceIsLessThanOrEqualToZero();
        }
    }

    public static void validateStock(List<String> informacionEmpresaVect) {
        if (informacionEmpresaVect.isEmpty()) {
            throw new StockIsLessThanOrEqualToZero();
        }
    }
}
