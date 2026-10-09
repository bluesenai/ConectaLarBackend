package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.DTO.CriarComunidadeRequest;
import br.kaizen.ConectaLar.DTO.EntrarComunidadeRequest;
import br.kaizen.ConectaLar.model.Comunidade;
import br.kaizen.ConectaLar.model.Usuario;
import br.kaizen.ConectaLar.service.ComunidadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comunidade")
public class ComunidadeController {

    private final ComunidadeService comunidadeService;

    public ComunidadeController(ComunidadeService comunidadeService) {
        this.comunidadeService = comunidadeService;
    }

    @PostMapping("/entrar")
    public ResponseEntity<?> entrar(
            @RequestBody EntrarComunidadeRequest request) {

        try {

            Usuario usuario = comunidadeService.entrarNaComunidade(
                    request.getUsuarioId(),
                    request.getCep()
            );

            return ResponseEntity.ok(
                    "Usuário entrou na comunidade com sucesso"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> criar(
            @RequestBody CriarComunidadeRequest request) {

        try {

            Comunidade comunidade = new Comunidade();

            comunidade.setCep(request.getCep());
            comunidade.setEstado(request.getEstado());
            comunidade.setCidade(request.getCidade());
            comunidade.setBairro(request.getBairro());
            comunidade.setRua(request.getRua());

            Comunidade comunidadeSalva =
                    comunidadeService.criarComunidade(comunidade);

            return ResponseEntity.ok(comunidadeSalva);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}