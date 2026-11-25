package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.DTO.MetodoPagamentoRequest;
import br.kaizen.ConectaLar.DTO.MetodoPagamentoResponse;
import br.kaizen.ConectaLar.model.MetodoPagamento;
import br.kaizen.ConectaLar.model.PrestadorServico;
import br.kaizen.ConectaLar.repository.MetodoPagamentoRepository;
import br.kaizen.ConectaLar.repository.PrestadorServicoRepository;
import org.springframework.stereotype.Service;

@Service
public class MetodoPagamentoService {

    private final MetodoPagamentoRepository pagamentoRepository;
    private final PrestadorServicoRepository prestadorRepository;

    public MetodoPagamentoService(
            MetodoPagamentoRepository pagamentoRepository,
            PrestadorServicoRepository prestadorRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.prestadorRepository = prestadorRepository;
    }

    public MetodoPagamento cadastrar(
            Long prestadorId,
            MetodoPagamentoRequest request) {

        if (request.getTipoChavePix() == null ||
                request.getTipoChavePix().isBlank()) {
            throw new RuntimeException("Informe o tipo da chave PIX");
        }

        if (request.getChavePix() == null ||
                request.getChavePix().isBlank()) {
            throw new RuntimeException("Informe a chave PIX");
        }

        String tipo = request.getTipoChavePix().toUpperCase().trim();

        if (!tipo.equals("CPF") &&
                !tipo.equals("EMAIL") &&
                !tipo.equals("TELEFONE") &&
                !tipo.equals("ALEATORIA")) {
            throw new RuntimeException("Tipo de chave PIX inválido");
        }

        if (pagamentoRepository.existsByPrestadorId(prestadorId)) {
            throw new RuntimeException(
                    "Este prestador já possui uma chave PIX cadastrada");
        }

        if (pagamentoRepository.existsByChavePix(request.getChavePix())) {
            throw new RuntimeException("Esta chave PIX já está cadastrada");
        }

        PrestadorServico prestador = prestadorRepository.findById(prestadorId)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));

        MetodoPagamento pagamento = new MetodoPagamento();
        pagamento.setPrestador(prestador);
        pagamento.setTipoChavePix(tipo);
        pagamento.setChavePix(request.getChavePix().trim());

        return pagamentoRepository.save(pagamento);
    }
    
    public MetodoPagamentoResponse consultar(Long prestadorId) {

    MetodoPagamento pagamento = pagamentoRepository
            .findByPrestadorId(prestadorId)
            .orElseThrow(() -> new RuntimeException(
                    "Método de pagamento não encontrado"));

    return new MetodoPagamentoResponse(
            pagamento.getId(),
            pagamento.getTipoChavePix(),
            pagamento.getChavePix()
    );
}
    public MetodoPagamento atualizar(
        Long prestadorId,
        MetodoPagamentoRequest request) {

    MetodoPagamento pagamento = pagamentoRepository
            .findByPrestadorId(prestadorId)
            .orElseThrow(() -> new RuntimeException(
                    "Método de pagamento não encontrado"));

    if (request.getTipoChavePix() == null ||
            request.getTipoChavePix().isBlank()) {
        throw new RuntimeException("Informe o tipo da chave PIX");
    }

    if (request.getChavePix() == null ||
            request.getChavePix().isBlank()) {
        throw new RuntimeException("Informe a chave PIX");
    }

    String tipo = request.getTipoChavePix().toUpperCase().trim();
    String chave = request.getChavePix().trim();

    if (!tipo.equals("CPF") &&
            !tipo.equals("EMAIL") &&
            !tipo.equals("TELEFONE") &&
            !tipo.equals("ALEATORIA")) {
        throw new RuntimeException("Tipo de chave PIX inválido");
    }

    boolean chaveEmUso = pagamentoRepository
            .findByChavePix(chave)
            .filter(outro -> !outro.getId().equals(pagamento.getId()))
            .isPresent();

    if (chaveEmUso) {
        throw new RuntimeException("Esta chave PIX já está cadastrada");
    }

    pagamento.setTipoChavePix(tipo);
    pagamento.setChavePix(chave);

    return pagamentoRepository.save(pagamento);
}
}