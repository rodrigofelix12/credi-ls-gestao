package com.felixsoftwares.credilsgestao.emprestimo.controller.dto;

import com.felixsoftwares.credilsgestao.emprestimo.enums.StatusEmprestimoEnum;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusEmprestimoResponse {
    private BigDecimal totalPago;
    private Integer numParcelasPagas;
    private BigDecimal valorEmAberto;
    private LocalDate proximoVencimento;
    private StatusEmprestimoEnum statusEmprestimoEnum;
}
