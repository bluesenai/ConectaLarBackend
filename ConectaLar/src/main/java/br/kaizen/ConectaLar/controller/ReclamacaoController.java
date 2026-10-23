package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.DTO.RegistrarReclamacaoRequest;
import br.kaizen.ConectaLar.model.CategoriaReclamacao;
import br.kaizen.ConectaLar.model.NivelRelevancia;
import br.kaizen.ConectaLar.service.ReclamacaoService;
import br.kaizen.ConectaLar.DTO.ReclamacaoResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reclamacao")
public class ReclamacaoController {

    private final ReclamacaoService reclamacaoService;

    public ReclamacaoController(
            ReclamacaoService reclamacaoService) {
        this.reclamacaoService = reclamacaoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(
            @RequestBody RegistrarReclamacaoRequest request) {

        try {
            boolean limiteAtingido =
                    reclamacaoService.registrarReclamacao(request);

            NivelRelevancia nivel =
                    reclamacaoService.definirNivel(request.getCategoria());

            int limite = reclamacaoService.definirLimite(nivel);

            Map<String, Object> resposta = Map.of(
                    "mensagem", "Reclamação registrada com sucesso",
                    "categoria", request.getCategoria().name(),
                    "nivelRelevancia", nivel.name(),
                    "limiteNecessario", limite,
                    "limiteAtingido", limiteAtingido
            );

            return ResponseEntity.ok(resposta);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro", e.getMessage())
            );
        }
    }

    @GetMapping("/limite")
    public ResponseEntity<?> consultarLimite(
            @RequestParam Long comunidadeId,
            @RequestParam CategoriaReclamacao categoria) {

        try {
            NivelRelevancia nivel =
                    reclamacaoService.definirNivel(categoria);

            long quantidade =
                    reclamacaoService.contarReclamacoesUltimas24Horas(
                            comunidadeId, categoria);

            int limite = reclamacaoService.definirLimite(nivel);

            return ResponseEntity.ok(Map.of(
                    "comunidadeId", comunidadeId,
                    "categoria", categoria.name(),
                    "nivelRelevancia", nivel.name(),
                    "quantidadeUltimas24Horas", quantidade,
                    "limiteNecessario", limite,
                    "limiteAtingido", quantidade >= limite
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro", e.getMessage())
            );
        }
    }
    

@GetMapping("/listar")
public ResponseEntity<?> listar(
        @RequestParam Long comunidadeId) {

    List<ReclamacaoResponse> reclamacoes =
            reclamacaoService.listarPorComunidade(comunidadeId);

    return ResponseEntity.ok(reclamacoes);
}
}