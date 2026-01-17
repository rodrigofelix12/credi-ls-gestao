package com.felixsoftwares.credilsgestao.emprestimo.mapper;

import com.felixsoftwares.credilsgestao.emprestimo.controller.dto.EmprestimoResponse;
import com.felixsoftwares.credilsgestao.emprestimo.entity.Emprestimo;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {
    List<EmprestimoResponse> toEmprestimoResponseList(List<Emprestimo> emprestimos);
    EmprestimoResponse toEmprestimoResponse(Emprestimo emprestimo);

    default Page<EmprestimoResponse> toResponsePage(Page<Emprestimo> page) {
        return page.map(this::toEmprestimoResponse);
    }
}
