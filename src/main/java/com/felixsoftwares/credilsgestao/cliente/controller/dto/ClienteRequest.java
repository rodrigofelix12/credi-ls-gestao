package com.felixsoftwares.credilsgestao.cliente.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String cpf;
    @NotBlank
    private String rg;
    @NotBlank
    private String telefone;
    @NotBlank
    private String endereco;
}
