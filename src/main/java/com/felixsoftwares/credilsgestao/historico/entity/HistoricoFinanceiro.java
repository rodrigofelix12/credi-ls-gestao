package com.felixsoftwares.credilsgestao.historico.entity;

import com.felixsoftwares.credilsgestao.emprestimo.entity.Emprestimo;
import com.felixsoftwares.credilsgestao.historico.enums.TipoHistorico;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "historico_financeiro")
@Getter
@Setter
public class HistoricoFinanceiro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "emprestimo_id", nullable = false)
  private Emprestimo emprestimo;

  @Enumerated(EnumType.STRING)
  private TipoHistorico tipo;

  private BigDecimal valor;

  private LocalDate dataEvento;

  private String observacao;
}
