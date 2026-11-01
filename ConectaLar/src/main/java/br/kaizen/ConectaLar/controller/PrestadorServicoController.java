package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.DTO.PrestadorCadastroRequest;
import br.kaizen.ConectaLar.model.PrestadorServico;
import br.kaizen.ConectaLar.service.PrestadorServicoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/prestador")
public class PrestadorServicoController {

    private final PrestadorServicoService prestadorService;

    public PrestadorServicoController(
            PrestadorServicoService prestadorService) {

        this.prestadorService = prestadorService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(
            @RequestBody PrestadorCadastroRequest request) {

        try {
            PrestadorServico prestador =
                    prestadorService.cadastrar(request);

            return ResponseEntity.ok(
                    Map.of(
                            "mensagem", "Prestador cadastrado com sucesso",
                            "id", prestador.getId()
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of("erro", e.getMessage()));
        }
    }
}