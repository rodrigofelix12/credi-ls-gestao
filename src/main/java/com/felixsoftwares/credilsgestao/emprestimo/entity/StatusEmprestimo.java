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

    public void calcularProximoVencimento(LocalDate dataInicioContrato, Integer prazo) {

        if (numParcelasPagas == null) {
            numParcelasPagas = 0;
        }

        // Se todas as parcelas já foram pagas
        if (numParcelasPagas >= prazo) {
            this.proximoVencimento = null;
            this.statusEmprestimoEnum = StatusEmprestimoEnum.QUITADO;
            return;
        }

        // Se estiver quitado explicitamente
        if (statusEmprestimoEnum == StatusEmprestimoEnum.QUITADO) {
            this.proximoVencimento = null;
            return;
        }

        // Próximo vencimento = próxima parcela
        this.proximoVencimento =
                dataInicioContrato.plusMonths(numParcelasPagas + 1L);
    }

}
