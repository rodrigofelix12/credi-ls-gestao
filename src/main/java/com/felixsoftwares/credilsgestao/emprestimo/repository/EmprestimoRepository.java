package com.felixsoftwares.credilsgestao.emprestimo.repository;

import com.felixsoftwares.credilsgestao.emprestimo.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {}
