package com.felixsoftwares.credilsgestao.emprestimo.controller.dto;

import com.felixsoftwares.credilsgestao.cliente.controller.dto.ClienteResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmprestimoResponse {
    private Long id;
    private BigDecimal valorEmprestimo;
    private BigDecimal taxaJurosMensal;
    private Integer prazo;
    private LocalDate dataInicioContrato;
    private BigDecimal valorParcela;
    private BigDecimal totalAPagar;
    private StatusEmprestimoResponse statusEmprestimo;
    private InfoEmprestimoResponse infoEmprestimo;
    private ClienteResponse cliente;
}
