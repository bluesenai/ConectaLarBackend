package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.DTO.MetodoPagamentoRequest;
import br.kaizen.ConectaLar.DTO.MetodoPagamentoResponse;
import br.kaizen.ConectaLar.model.MetodoPagamento;
import br.kaizen.ConectaLar.service.MetodoPagamentoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/prestador")
public class MetodoPagamentoController {

    private final MetodoPagamentoService pagamentoService;

    public MetodoPagamentoController(
            MetodoPagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/{prestadorId}/pagamento")
    public ResponseEntity<?> cadastrar(
            @PathVariable Long prestadorId,
            @RequestBody MetodoPagamentoRequest request) {

        try {
            MetodoPagamento pagamento =
                    pagamentoService.cadastrar(prestadorId, request);

            return ResponseEntity.ok(Map.of(
                    "mensagem", "Chave PIX cadastrada com sucesso",
                    "id", pagamento.getId()
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro", e.getMessage())
            );
        }
    }
    @GetMapping("/{prestadorId}/pagamento")
public ResponseEntity<?> consultar(@PathVariable Long prestadorId) {

    try {
        MetodoPagamentoResponse pagamento =
                pagamentoService.consultar(prestadorId);

        return ResponseEntity.ok(pagamento);

    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(
                Map.of("erro", e.getMessage())
        );
    }
}
@PutMapping("/{prestadorId}/pagamento")
public ResponseEntity<?> atualizar(
        @PathVariable Long prestadorId,
        @RequestBody MetodoPagamentoRequest request) {

    try {
        MetodoPagamento pagamento =
                pagamentoService.atualizar(prestadorId, request);

        return ResponseEntity.ok(Map.of(
                "mensagem", "Chave PIX atualizada com sucesso",
                "id", pagamento.getId()
        ));

    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(
                Map.of("erro", e.getMessage())
        );
    }
}
}