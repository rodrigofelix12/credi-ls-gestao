package com.felixsoftwares.credilsgestao.historico.repository;

import com.felixsoftwares.credilsgestao.historico.entity.HistoricoFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoFinanceiroRepository extends JpaRepository<HistoricoFinanceiro, Long> {}
