package com.felixsoftwares.credilsgestao.emprestimo.entity;


import com.felixsoftwares.credilsgestao.emprestimo.enums.StatusEmprestimoEnum;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class StatusEmprestimo {
    
    private BigDecimal totalPago;
    private Integer numParcelasPagas;
    private BigDecimal valorEmAberto;
    private LocalDate proximoVencimento;
    @Enumerated(EnumType.STRING)
    private StatusEmprestimoEnum statusEmprestimoEnum;
}
