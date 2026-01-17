package com.felixsoftwares.credilsgestao.cliente.service;

import com.felixsoftwares.credilsgestao.cliente.controller.dto.ClienteRequest;
import com.felixsoftwares.credilsgestao.cliente.entity.Cliente;
import com.felixsoftwares.credilsgestao.cliente.repository.ClienteRepository;
import com.felixsoftwares.credilsgestao.exceptions.ClienteCadastradoException;
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
        validarClienteCadastrado(request);
        Cliente cliente = new Cliente();
        cliente.setName(request.getName());
        cliente.setRg(cleanText(request.getRg()));
        cliente.setCpf(cleanText(request.getCpf()));
        cliente.setTelefone(request.getTelefone());
        cliente.setEndereco(cleanText(request.getEndereco()));
        repository.save(cliente);
    }

    private void validarClienteCadastrado(ClienteRequest request) {
        var clienteCadastrado = repository.findByCpf(cleanText(request.getCpf()));
        if (clienteCadastrado != null) {
            throw new ClienteCadastradoException("Cliente já cadastrado!");
        }
    }

    public Cliente updateClient(Long id, ClienteRequest request) {
    Cliente cliente =
        repository
            .findById(id)
            .orElseThrow(() -> new ClienteCadastradoException("Cliente não encontrado"));

        cliente.setName(request.getName());
        cliente.setCpf(cleanText(request.getCpf()));
        cliente.setRg(cleanText(request.getRg()));
        cliente.setEndereco(request.getEndereco());
        cliente.setTelefone(cleanText(request.getTelefone()));

        return repository.save(cliente);
    }

    public String cleanText(String text) {
        return text.replaceAll("[^a-zA-Z0-9\\s]", "").trim();
    }
}
