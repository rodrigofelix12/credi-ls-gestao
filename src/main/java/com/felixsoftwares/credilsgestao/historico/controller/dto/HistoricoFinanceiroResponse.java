package com.felixsoftwares.credilsgestao.historico.controller.dto;

import com.felixsoftwares.credilsgestao.emprestimo.controller.dto.EmprestimoResponse;
import com.felixsoftwares.credilsgestao.historico.enums.TipoHistorico;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoricoFinanceiroResponse {
    private Long id;
    private Long emprestimoId;
    private TipoHistorico tipo;
    private BigDecimal valor;
    private LocalDate dataEvento;
    private String observacao;
}
