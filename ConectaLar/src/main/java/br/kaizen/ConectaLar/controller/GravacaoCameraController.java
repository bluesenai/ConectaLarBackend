
package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.model.GravacaoCamera;
import br.kaizen.ConectaLar.service.GravacaoCameraService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gravacao")
public class GravacaoCameraController {

    private final GravacaoCameraService gravacaoService;

    public GravacaoCameraController(
            GravacaoCameraService gravacaoService) {
        this.gravacaoService = gravacaoService;
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> cadastrar(
            @RequestParam Long cameraId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime inicio,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fim,
            @RequestParam(required = false) String urlArquivoNuvem,
            @RequestParam(required = false) String status) {

        GravacaoCamera gravacao = gravacaoService.cadastrar(
                cameraId, inicio, fim, urlArquivoNuvem, status);

        return Map.of(
                "mensagem", "Metadados da gravação cadastrados",
                "id", gravacao.getId(),
                "cameraId", gravacao.getCamera().getId(),
                "inicio", gravacao.getInicio().toString(),
                "status", gravacao.getStatus()
        );
    }

    @GetMapping("/camera/{cameraId}")
    public List<Map<String, Object>> listarPorCamera(
            @PathVariable Long cameraId) {

        return gravacaoService.listarPorCamera(cameraId)
                .stream()
                .map(this::converterParaResposta)
                .toList();
    }

    @GetMapping("/comunidade/{comunidadeId}")
    public List<Map<String, Object>> listarPorComunidade(
            @PathVariable Long comunidadeId) {

        return gravacaoService.listarPorComunidade(comunidadeId)
                .stream()
                .map(this::converterParaResposta)
                .toList();
    }

    private Map<String, Object> converterParaResposta(
            GravacaoCamera gravacao) {

        Map<String, Object> resposta = new java.util.LinkedHashMap<>();

        resposta.put("id", gravacao.getId());
        resposta.put("cameraId", gravacao.getCamera().getId());
        resposta.put("inicio", gravacao.getInicio());
        resposta.put("fim", gravacao.getFim());
        resposta.put("status", gravacao.getStatus());

        return resposta;
    }
}
