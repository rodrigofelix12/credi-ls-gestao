package com.felixsoftwares.credilsgestao.historico.mapper;

import com.felixsoftwares.credilsgestao.historico.controller.dto.HistoricoFinanceiroResponse;
import com.felixsoftwares.credilsgestao.historico.entity.HistoricoFinanceiro;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoricoFinanceiroMapper {
  @Mapping(source = "emprestimo.id", target = "emprestimoId")
  HistoricoFinanceiroResponse toResponse(HistoricoFinanceiro historicoFinanceiro);

  List<HistoricoFinanceiroResponse> toResponseList(List<HistoricoFinanceiro> historicoFinanceiros);
}
