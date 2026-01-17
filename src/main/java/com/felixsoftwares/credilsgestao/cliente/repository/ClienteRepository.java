package com.felixsoftwares.credilsgestao.cliente.repository;

import com.felixsoftwares.credilsgestao.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
