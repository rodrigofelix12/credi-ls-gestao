package com.felixsoftwares.credilsgestao.historico.service;

import com.felixsoftwares.credilsgestao.historico.entity.HistoricoFinanceiro;
import com.felixsoftwares.credilsgestao.historico.repository.HistoricoFinanceiroRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoricoFinanceiroService {

  private final HistoricoFinanceiroRepository repository;

  public HistoricoFinanceiro findById(final Long id) {
    return repository.findById(id).orElse(null);
  }

  public List<HistoricoFinanceiro> findAll() {
    return repository.findAll();
  }

  public List<HistoricoFinanceiro> findByEmprestimo(final Long idEmprestimo) {
    return repository.findByEmprestimoIdOrderByDataEventoDesc(idEmprestimo);
  }
}
