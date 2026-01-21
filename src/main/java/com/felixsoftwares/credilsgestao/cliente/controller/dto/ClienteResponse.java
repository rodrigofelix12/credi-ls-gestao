package com.felixsoftwares.credilsgestao.cliente.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponse {
    private Long id;
    private String name;
    private String cpf;
    private String rg;
    private String telefone;
    private String endereco;
}
