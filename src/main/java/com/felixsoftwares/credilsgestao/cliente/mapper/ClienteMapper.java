package com.felixsoftwares.credilsgestao.cliente.mapper;

import com.felixsoftwares.credilsgestao.cliente.controller.dto.ClienteResponse;
import com.felixsoftwares.credilsgestao.cliente.entity.Cliente;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    List<ClienteResponse> toClienteResponseList(List<Cliente> clientes);
    ClienteResponse toClienteResponse(Cliente cliente);
}
