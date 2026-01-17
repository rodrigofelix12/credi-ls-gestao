package com.felixsoftwares.credilsgestao.cliente.service;

import com.felixsoftwares.credilsgestao.cliente.entity.Cliente;
import com.felixsoftwares.credilsgestao.cliente.repository.ClienteRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
}
