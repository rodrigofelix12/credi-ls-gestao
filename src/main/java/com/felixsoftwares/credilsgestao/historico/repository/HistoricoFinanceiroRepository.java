package com.felixsoftwares.credilsgestao.historico.repository;

import com.felixsoftwares.credilsgestao.historico.entity.HistoricoFinanceiro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoFinanceiroRepository extends JpaRepository<HistoricoFinanceiro, Long> {
    List<HistoricoFinanceiro> findByEmprestimoIdOrderByDataEventoDesc(Long emprestimoId);
}
