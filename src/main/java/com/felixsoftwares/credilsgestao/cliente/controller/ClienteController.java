package com.felixsoftwares.credilsgestao.cliente.controller;

import com.felixsoftwares.credilsgestao.cliente.controller.dto.ClienteResponse;
import com.felixsoftwares.credilsgestao.cliente.entity.Cliente;
import com.felixsoftwares.credilsgestao.cliente.mapper.ClienteMapper;
import com.felixsoftwares.credilsgestao.cliente.service.ClienteService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {

    private final ClienteService service;
    private final ClienteMapper mapper;

    public ClienteController(ClienteService service, ClienteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/clientes")
    public List<ClienteResponse> findAll() {
        return mapper.toClienteResponseList(service.findAll());
    }

    @GetMapping("/clientes/{id}")
    public ClienteResponse findById(@PathVariable Long id) {
        return mapper.toClienteResponse(service.findById(id));
    }
}
