
package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.DTO.NotificacaoResponse;
import br.kaizen.ConectaLar.model.Notificacao;
import br.kaizen.ConectaLar.service.NotificacaoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    public NotificacaoController(
            NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @GetMapping("/listar")
    public List<NotificacaoResponse> listar(
            @RequestParam Long usuarioId) {

        return notificacaoService.listarPorUsuario(usuarioId)
                .stream()
                .map(NotificacaoResponse::new)
                .toList();
    }

    @GetMapping("/nao-lidas")
    public long contarNaoLidas(
            @RequestParam Long usuarioId) {

        return notificacaoService.contarNaoLidas(usuarioId);
    }
    
@org.springframework.web.bind.annotation.PatchMapping("/marcar-todas-lidas")
public java.util.Map<String, Object> marcarTodasComoLidas(
        @org.springframework.web.bind.annotation.RequestParam Long usuarioId) {

    int quantidade = notificacaoService.marcarTodasComoLidas(usuarioId);

    return java.util.Map.of(
            "mensagem", "Notificações marcadas como lidas",
            "quantidadeAtualizada", quantidade
    );
}

}
