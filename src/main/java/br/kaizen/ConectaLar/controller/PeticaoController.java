
package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.DTO.AssinarPeticaoRequest;
import br.kaizen.ConectaLar.DTO.CriarPeticaoRequest;
import br.kaizen.ConectaLar.DTO.PeticaoResponse;
import br.kaizen.ConectaLar.service.PeticaoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/peticao")
public class PeticaoController {

    private final PeticaoService peticaoService;

    public PeticaoController(PeticaoService peticaoService) {
        this.peticaoService = peticaoService;
    }

    // Criar uma petição
    @PostMapping("/criar")
    public ResponseEntity<?> criarPeticao(
            @RequestBody CriarPeticaoRequest request) {

        try {
            PeticaoResponse resposta = peticaoService.criarPeticao(
                    request.getUsuarioId(),
                    request.getTitulo(),
                    request.getDescricao()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(resposta);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro", e.getMessage())
            );
        }
    }

    // Listar petições da comunidade do usuário
    @GetMapping("/listar")
    public ResponseEntity<?> listarPeticoes(
            @RequestParam Long usuarioId) {

        try {
            List<PeticaoResponse> peticoes =
                    peticaoService.listarPeticoes(usuarioId);

            return ResponseEntity.ok(peticoes);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro", e.getMessage())
            );
        }
    }

    // Assinar uma petição
    @PostMapping("/{peticaoId}/assinar")
    public ResponseEntity<?> assinarPeticao(
            @PathVariable Long peticaoId,
            @RequestBody AssinarPeticaoRequest request) {

        try {
            peticaoService.assinarPeticao(
                    request.getUsuarioId(),
                    peticaoId
            );

            return ResponseEntity.ok(
                    Map.of("mensagem", "Petição assinada com sucesso")
            );

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro", e.getMessage())
            );
        }
    }
}