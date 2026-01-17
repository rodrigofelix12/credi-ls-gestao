package com.felixsoftwares.credilsgestao.cliente.controller;

import com.felixsoftwares.credilsgestao.cliente.controller.dto.ClienteRequest;
import com.felixsoftwares.credilsgestao.cliente.controller.dto.ClienteResponse;
import com.felixsoftwares.credilsgestao.cliente.entity.Cliente;
import com.felixsoftwares.credilsgestao.cliente.mapper.ClienteMapper;
import com.felixsoftwares.credilsgestao.cliente.service.ClienteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;
    private final ClienteMapper mapper;

    @GetMapping("/clientes")
    public List<ClienteResponse> findAll() {
        return mapper.toClienteResponseList(service.findAll());
    }

    @GetMapping("/clientes/{id}")
    public ClienteResponse findById(@PathVariable Long id) {
        return mapper.toClienteResponse(service.findById(id));
    }

    @PostMapping("/clientes")
    public ResponseEntity<Void> createClient(@RequestBody ClienteRequest request) {
        service.createClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/clientes/{id}")
    public ClienteResponse updateClient(@PathVariable Long id, @RequestBody ClienteRequest request) {
        Cliente cliente = service.updateClient(id, request);
        return mapper.toClienteResponse(cliente);
    }
}
