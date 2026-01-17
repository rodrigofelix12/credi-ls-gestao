package com.felixsoftwares.credilsgestao.emprestimo.controller.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoEmprestimoResponse {
    private BigDecimal valorDividaAnterior;
    private BigDecimal valorGarantia;
    private String detalhesGarantia;
}
