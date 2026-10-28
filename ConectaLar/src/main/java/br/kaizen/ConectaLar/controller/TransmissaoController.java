
package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.StatusTransmissao;
import br.kaizen.ConectaLar.model.Camera;
import br.kaizen.ConectaLar.repository.CameraRepository;
import br.kaizen.ConectaLar.service.TransmissaoService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/transmissao")
public class TransmissaoController {

    private final TransmissaoService transmissaoService;
    private final CameraRepository cameraRepository;

    public TransmissaoController(
            TransmissaoService transmissaoService,
            CameraRepository cameraRepository) {
        this.transmissaoService = transmissaoService;
        this.cameraRepository = cameraRepository;
    }

    @GetMapping("/camera/{cameraId}/status")
    public StatusTransmissao consultarStatus(
            @PathVariable Long cameraId) {
        return transmissaoService.consultarStatus(cameraId);
    }

    @GetMapping("/camera/{cameraId}")
    public Map<String, Object> consultarCamera(
            @PathVariable Long cameraId) {

        Camera camera = cameraRepository.findById(cameraId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Câmera não encontrada."));

        return Map.of(
                "cameraId", camera.getId(),
                "nome", camera.getNome(),
                "ativa", camera.isAtiva(),
                "statusUrl", "/raiz_do_endpoint/transmissao/camera/"
                        + camera.getId() + "/status",
                "mensagem", "Cadastro consultado. A transmissão de vídeo ainda precisa ser configurada."
        );
    }
}
