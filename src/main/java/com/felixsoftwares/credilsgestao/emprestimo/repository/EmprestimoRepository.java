package com.felixsoftwares.credilsgestao.emprestimo.repository;

import com.felixsoftwares.credilsgestao.cliente.entity.Cliente;
import com.felixsoftwares.credilsgestao.emprestimo.entity.Emprestimo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByCliente(Cliente cliente);
}
