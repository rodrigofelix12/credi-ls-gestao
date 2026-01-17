package com.felixsoftwares.credilsgestao.emprestimo.entity;

import com.felixsoftwares.credilsgestao.emprestimo.enums.StatusEmprestimoEnum;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

  private BigDecimal multa;
  private BigDecimal jurosAtraso;
  private Integer diasEmAtraso;

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
    this.proximoVencimento = dataInicioContrato.plusMonths(numParcelasPagas + 1L);
  }

  public void calcularAtraso(BigDecimal valorBase, LocalDate hoje) {

    if (statusEmprestimoEnum != StatusEmprestimoEnum.ATRASO) {
      this.multa = BigDecimal.ZERO;
      this.jurosAtraso = BigDecimal.ZERO;
      this.diasEmAtraso = 0;
      return;
    }

    long dias = ChronoUnit.DAYS.between(proximoVencimento, hoje);
    this.diasEmAtraso = (int) Math.max(dias, 0);

    // Multa (uma vez)
    this.multa =
        valorBase.multiply(PoliticaAtraso.MULTA_PERCENTUAL).setScale(2, RoundingMode.HALF_UP);

    // Juros por dia
    this.jurosAtraso =
        valorBase
            .multiply(PoliticaAtraso.JUROS_DIA)
            .multiply(BigDecimal.valueOf(dias))
            .setScale(2, RoundingMode.HALF_UP);

    // Atualiza valor em aberto
    this.valorEmAberto = this.valorEmAberto.add(multa).add(jurosAtraso);
  }
}
