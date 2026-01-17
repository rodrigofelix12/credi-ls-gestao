package com.felixsoftwares.credilsgestao.cliente.repository;

import com.felixsoftwares.credilsgestao.cliente.entity.Cliente;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
  @Query(
      name =
        """
        SELECT cliente c from cliente where c.cpf = ?
        """,
          nativeQuery = true)
  Cliente findByCpf(@NotBlank String cpf);
}
