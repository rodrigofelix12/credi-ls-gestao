package com.felixsoftwares.credilsgestao.historico.controller;

import com.felixsoftwares.credilsgestao.historico.controller.dto.HistoricoFinanceiroResponse;
import com.felixsoftwares.credilsgestao.historico.mapper.HistoricoFinanceiroMapper;
import com.felixsoftwares.credilsgestao.historico.service.HistoricoFinanceiroService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HistoricoFinanceiroController {

  private final HistoricoFinanceiroMapper mapper;
  private final HistoricoFinanceiroService service;

  @GetMapping("/historico")
  public List<HistoricoFinanceiroResponse> findAll() {
    return mapper.toResponseList(service.findAll());
  }

  @GetMapping("/historico/{id}")
  public HistoricoFinanceiroResponse findById(@PathVariable Long id) {
    return mapper.toResponse(service.findById(id));
  }

  @GetMapping("/emprestimos/{idEmprestimo}/historico")
  public List<HistoricoFinanceiroResponse> historicoPorEmprestimo(@PathVariable Long idEmprestimo) {

    return mapper.toResponseList(service.findByEmprestimo(idEmprestimo));
  }
}
