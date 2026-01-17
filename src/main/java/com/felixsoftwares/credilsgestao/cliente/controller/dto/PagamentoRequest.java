package com.felixsoftwares.credilsgestao.cliente.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoRequest {
    private BigDecimal valorPago;
    private LocalDate dataPagamento;
}
