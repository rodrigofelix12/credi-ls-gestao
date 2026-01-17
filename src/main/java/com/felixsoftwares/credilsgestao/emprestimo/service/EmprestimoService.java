package com.felixsoftwares.credilsgestao.emprestimo.service;

import com.felixsoftwares.credilsgestao.cliente.repository.ClienteRepository;
import com.felixsoftwares.credilsgestao.emprestimo.controller.dto.EmprestimoRequest;
import com.felixsoftwares.credilsgestao.emprestimo.entity.Emprestimo;
import com.felixsoftwares.credilsgestao.emprestimo.entity.InfoEmprestimo;
import com.felixsoftwares.credilsgestao.emprestimo.entity.StatusEmprestimo;
import com.felixsoftwares.credilsgestao.emprestimo.enums.StatusEmprestimoEnum;
import com.felixsoftwares.credilsgestao.emprestimo.repository.EmprestimoRepository;
import com.felixsoftwares.credilsgestao.exceptions.ClienteNaoEncontradoException;
import com.felixsoftwares.credilsgestao.exceptions.EmprestimoNaoEncontradoException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmprestimoService {
    
    private final EmprestimoRepository repository;
    private final ClienteRepository clienteRepository;
    
    public List<Emprestimo> findAll() {
        return repository.findAll();
    }

    public Emprestimo findById(final Long id) {
        return repository.findById(id).orElseThrow( () -> new EmprestimoNaoEncontradoException("Emprestimo nao encontrado"));
    }

    @Transactional
    public Emprestimo create(Long idCliente, EmprestimoRequest request) {
        var cliente =
            clienteRepository
                .findById(idCliente)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente nao encontrado"));
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setValorEmprestimo(request.getValorEmprestimo());
        emprestimo.setTaxaJurosMensal(request.getTaxaJurosMensal());
        emprestimo.setPrazo(request.getPrazo());
        emprestimo.setDataInicioContrato(request.getDataInicioContrato());
        emprestimo.setCliente(cliente);

        // Embedded: Status
        StatusEmprestimo status = new StatusEmprestimo();
        status.setTotalPago(
                request.getTotalPago() != null ? request.getTotalPago() : BigDecimal.ZERO
        );
        status.setNumParcelasPagas(
                request.getParcelasPagas() != null ? request.getParcelasPagas() : 0
        );
        status.setStatusEmprestimoEnum(StatusEmprestimoEnum.ATIVO);

        emprestimo.setStatusEmprestimo(status);

        // Embedded: Info
        InfoEmprestimo info = new InfoEmprestimo();
        info.setValorDividaAnterior(request.getValorDividaAnterior());
        info.setValorGarantia(request.getValorGarantia());
        info.setDetalhesGarantia(request.getDetalheGarantia());

        emprestimo.setInfoEmprestimo(info);

        // 🔥 REGRA DE NEGÓCIO
        emprestimo.calcularValores();

        return repository.save(emprestimo);
    }
}
