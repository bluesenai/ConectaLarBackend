package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.DTO.SegurancaResponse;
import br.kaizen.ConectaLar.service.SegurancaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/seguranca")
public class SegurancaController {

    private final SegurancaService segurancaService;

    public SegurancaController(SegurancaService segurancaService) {
        this.segurancaService = segurancaService;
    }

    @GetMapping("/morador/{usuarioId}")
    public ResponseEntity<?> consultar(@PathVariable Long usuarioId) {

        try {
            SegurancaResponse resposta =
                    segurancaService.consultar(usuarioId);

            return ResponseEntity.ok(resposta);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro", e.getMessage())
            );
        }
    }
}