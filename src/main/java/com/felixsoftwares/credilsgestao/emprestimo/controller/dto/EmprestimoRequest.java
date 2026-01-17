package com.felixsoftwares.credilsgestao.emprestimo.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmprestimoRequest {
    private BigDecimal valorEmprestimo;
    private BigDecimal taxaJurosMensal;
    private Integer prazo;
    private LocalDate dataInicioContrato;
    private BigDecimal totalPago;
    private Integer parcelasPagas;
    private BigDecimal valorDividaAnterior;
    private BigDecimal valorGarantia;
    private String detalheGarantia;
}
