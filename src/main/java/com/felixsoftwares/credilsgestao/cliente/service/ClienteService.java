package com.felixsoftwares.credilsgestao.cliente.service;

import com.felixsoftwares.credilsgestao.cliente.controller.dto.ClienteRequest;
import com.felixsoftwares.credilsgestao.cliente.entity.Cliente;
import com.felixsoftwares.credilsgestao.cliente.repository.ClienteRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.repository = clienteRepository;
    }

    public Cliente findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public void createClient(ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setName(request.getName());
        cliente.setRg(request.getRg());
        cliente.setCpf(request.getCpf());
        cliente.setTelefone(request.getTelefone());
        cliente.setEndereco(request.getEndereco());
        repository.save(cliente);
    }
}
