package com.felixsoftwares.credilsgestao.emprestimo.entity;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoliticaAtraso {

  public static final BigDecimal MULTA_PERCENTUAL = new BigDecimal("0.02");

  public static final BigDecimal JUROS_DIA = new BigDecimal("0.00033");
}
