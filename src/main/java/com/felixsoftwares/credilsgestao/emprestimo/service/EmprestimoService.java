package com.felixsoftwares.credilsgestao.emprestimo.service;

import com.felixsoftwares.credilsgestao.cliente.controller.dto.PagamentoRequest;
import com.felixsoftwares.credilsgestao.cliente.repository.ClienteRepository;
import com.felixsoftwares.credilsgestao.emprestimo.controller.dto.EmprestimoRequest;
import com.felixsoftwares.credilsgestao.emprestimo.entity.Emprestimo;
import com.felixsoftwares.credilsgestao.emprestimo.entity.InfoEmprestimo;
import com.felixsoftwares.credilsgestao.emprestimo.entity.StatusEmprestimo;
import com.felixsoftwares.credilsgestao.emprestimo.enums.StatusEmprestimoEnum;
import com.felixsoftwares.credilsgestao.emprestimo.repository.EmprestimoRepository;
import com.felixsoftwares.credilsgestao.exceptions.ClienteNaoEncontradoException;
import com.felixsoftwares.credilsgestao.exceptions.EmprestimoNaoEncontradoException;
import com.felixsoftwares.credilsgestao.historico.entity.HistoricoFinanceiro;
import com.felixsoftwares.credilsgestao.historico.enums.TipoHistorico;
import com.felixsoftwares.credilsgestao.historico.repository.HistoricoFinanceiroRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

  private final EmprestimoRepository repository;
  private final ClienteRepository clienteRepository;
  private final HistoricoFinanceiroRepository historicoFinanceiroRepository;

  public Page<Emprestimo> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Transactional
  public Emprestimo findById(final Long id) {
    var emprestimo =
        repository
            .findById(id)
            .orElseThrow(() -> new EmprestimoNaoEncontradoException("Emprestimo nao encontrado"));
    atualizarStatusAtraso(emprestimo);

    return emprestimo;
  }

  private void atualizarStatusAtraso(Emprestimo emprestimo) {

    StatusEmprestimo status = emprestimo.getStatusEmprestimo();

    if (status.getStatusEmprestimoEnum() == StatusEmprestimoEnum.QUITADO) {
      return;
    }

    LocalDate hoje = LocalDate.now();

    if (status.getProximoVencimento() == null) {
      return;
    }

    if (status.getProximoVencimento().isBefore(hoje)) {

      if (status.getStatusEmprestimoEnum() != StatusEmprestimoEnum.ATRASO) {
        status.setStatusEmprestimoEnum(StatusEmprestimoEnum.ATRASO);
      }

      status.calcularAtraso(emprestimo.getValorParcela(), hoje);

    } else {
      // caso alguém pague antecipado ou regularize
      status.setStatusEmprestimoEnum(StatusEmprestimoEnum.ATIVO);
      status.setDiasEmAtraso(0);
      status.setMulta(BigDecimal.ZERO);
      status.setJurosAtraso(BigDecimal.ZERO);
    }
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
    status.setTotalPago(request.getTotalPago() != null ? request.getTotalPago() : BigDecimal.ZERO);
    status.setNumParcelasPagas(request.getParcelasPagas() != null ? request.getParcelasPagas() : 0);
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

  public List<Emprestimo> findEmprestimoByCliente(final Long idCliente) {
    var cliente =
        clienteRepository
            .findById(idCliente)
            .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente nao encontrado"));
    return repository.findByCliente(cliente);
  }

  @Transactional
  public Emprestimo registrarPagamento(Long id, PagamentoRequest request) {

    Emprestimo emprestimo =
        repository
            .findById(id)
            .orElseThrow(() -> new EmprestimoNaoEncontradoException("Empréstimo não encontrado"));

    StatusEmprestimo status = emprestimo.getStatusEmprestimo();

    // 1. Atualiza total pago
    BigDecimal novoTotalPago = status.getTotalPago().add(request.getValorPago());

    status.setTotalPago(novoTotalPago);

    // 2. Incrementa parcelas pagas
    status.setNumParcelasPagas(status.getNumParcelasPagas() + 1);

    // 3. Recalcula valores
    emprestimo.calcularValores();
    status.calcularProximoVencimento(emprestimo.getDataInicioContrato(), emprestimo.getPrazo());

    // 4. Atualiza status
    if (status.getValorEmAberto().compareTo(BigDecimal.ZERO) <= 0) {
      status.setStatusEmprestimoEnum(StatusEmprestimoEnum.QUITADO);
      status.setProximoVencimento(null);
    } else {
      status.setStatusEmprestimoEnum(StatusEmprestimoEnum.ATIVO);
    }

    // 5. 🔥 REGISTRA HISTÓRICO DO PAGAMENTO
    historicoFinanceiroRepository.save(criarHistoricoPagamento(emprestimo, request));

    return emprestimo;
  }

  private HistoricoFinanceiro criarHistoricoPagamento(
      Emprestimo emprestimo, PagamentoRequest request) {

    HistoricoFinanceiro h = new HistoricoFinanceiro();
    h.setEmprestimo(emprestimo);
    h.setTipo(TipoHistorico.PAGAMENTO);
    h.setValor(request.getValorPago());
    h.setDataEvento(request.getDataPagamento());
    h.setObservacao("Pagamento de parcela");
    return h;
  }
}
