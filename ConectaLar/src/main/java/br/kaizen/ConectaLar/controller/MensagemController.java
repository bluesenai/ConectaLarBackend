package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.model.Mensagem;
import br.kaizen.ConectaLar.service.MensagemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagem")
public class MensagemController {

    private final MensagemService mensagemService;

    public MensagemController(MensagemService mensagemService) {
        this.mensagemService = mensagemService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<?> enviarMensagem(
            @RequestParam Long usuarioId,
            @RequestParam String texto) {

        try {

            Mensagem mensagem =
                    mensagemService.enviarMensagem(usuarioId, texto);

            return ResponseEntity.ok(mensagem);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarMensagens(
            @RequestParam Long usuarioId) {

        try {

            List<Mensagem> mensagens =
                    mensagemService.buscarMensagens(usuarioId);

            return ResponseEntity.ok(mensagens);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}