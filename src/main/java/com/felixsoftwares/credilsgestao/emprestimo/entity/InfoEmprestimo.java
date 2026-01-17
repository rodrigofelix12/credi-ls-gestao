package com.felixsoftwares.credilsgestao.emprestimo.entity;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class InfoEmprestimo {
    private BigDecimal valorDividaAnterior;
    private BigDecimal valorGarantia;
    private String detalhesGarantia;
}
