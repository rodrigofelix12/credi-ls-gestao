package com.felixsoftwares.credilsgestao.emprestimo.entity;

import com.felixsoftwares.credilsgestao.cliente.entity.Cliente;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.RoundingMode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "emprestimo")
@Getter
@Setter
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valorEmprestimo;
    private BigDecimal taxaJurosMensal;
    private Integer prazo;
    private LocalDate dataInicioContrato;
    private BigDecimal valorParcela;
    private BigDecimal totalAPagar;
    @Embedded
    private StatusEmprestimo statusEmprestimo;
    @Embedded
    private InfoEmprestimo infoEmprestimo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public void calcularValores() {
        this.totalAPagar = valorEmprestimo.multiply(
                BigDecimal.ONE.add(
                        taxaJurosMensal.multiply(BigDecimal.valueOf(prazo))
                )
        );

        this.valorParcela = totalAPagar.divide(
                BigDecimal.valueOf(prazo), 2, RoundingMode.HALF_UP
        );

        this.statusEmprestimo.setValorEmAberto(
                totalAPagar.subtract(statusEmprestimo.getTotalPago())
        );

        this.statusEmprestimo.calcularProximoVencimento(
                this.dataInicioContrato,
                this.prazo
        );
    }

}
