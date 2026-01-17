package com.felixsoftwares.credilsgestao.emprestimo.controller;

import com.felixsoftwares.credilsgestao.emprestimo.controller.dto.EmprestimoRequest;
import com.felixsoftwares.credilsgestao.emprestimo.controller.dto.EmprestimoResponse;
import com.felixsoftwares.credilsgestao.emprestimo.mapper.EmprestimoMapper;
import com.felixsoftwares.credilsgestao.emprestimo.service.EmprestimoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class EmprestimoController {
    
    private final EmprestimoMapper mapper;
    private final EmprestimoService service;

    @GetMapping("/emprestimos")
    public List<EmprestimoResponse> findAll() {
        return mapper.toEmprestimoResponseList(service.findAll());
    }

    @GetMapping("/emprestimos/{id}")
    public EmprestimoResponse findById(@PathVariable Long id) {
        return mapper.toEmprestimoResponse(service.findById(id));
    }

    @PostMapping("/emprestimos/{idCliente}")
    public ResponseEntity<Void> createEmprestimo(@PathVariable Long idCliente, @RequestBody EmprestimoRequest request) {
        service.create(idCliente, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
