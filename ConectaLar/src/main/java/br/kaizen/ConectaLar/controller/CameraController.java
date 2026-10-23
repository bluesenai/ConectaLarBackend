
package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.DTO.CadastrarCameraRequest;
import br.kaizen.ConectaLar.model.Camera;
import br.kaizen.ConectaLar.service.CameraService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/camera")
public class CameraController {

    private final CameraService cameraService;

    public CameraController(CameraService cameraService) {
        this.cameraService = cameraService;
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> cadastrar(
            @RequestBody CadastrarCameraRequest request) {

        Camera camera = cameraService.cadastrar(
                request.getNome(),
                request.getEndereco(),
                request.getLocalizacao(),
                request.getComunidadeId(),
                request.getResponsavelId()
        );

        return Map.of(
                "mensagem", "Câmera cadastrada com sucesso",
                "id", camera.getId(),
                "nome", camera.getNome(),
                "endereco", camera.getEndereco(),
                "ativa", camera.isAtiva()
        );
    }

    @GetMapping("/comunidade/{comunidadeId}")
    public List<Map<String, Object>> listarPorComunidade(
            @PathVariable Long comunidadeId) {

        return cameraService.listarPorComunidade(comunidadeId)
                .stream()
                .map(camera -> Map.<String, Object>of(
                        "id", camera.getId(),
                        "nome", camera.getNome(),
                        "endereco", camera.getEndereco(),
                        "localizacao",
                        camera.getLocalizacao() == null
                                ? ""
                                : camera.getLocalizacao(),
                        "ativa", camera.isAtiva()
                ))
                .toList();
    }

    @GetMapping("/comunidade/{comunidadeId}/ativas")
    public List<Map<String, Object>> listarAtivas(
            @PathVariable Long comunidadeId) {

        return cameraService
                .listarAtivasPorComunidade(comunidadeId)
                .stream()
                .map(camera -> Map.<String, Object>of(
                        "id", camera.getId(),
                        "nome", camera.getNome(),
                        "endereco", camera.getEndereco(),
                        "ativa", camera.isAtiva()
                ))
                .toList();
    }
    
@PatchMapping("/{cameraId}/ativar")
public Map<String, Object> ativar(
        @PathVariable Long cameraId) {

    Camera camera = cameraService.alterarEstado(cameraId, true);

    return Map.of(
            "mensagem", "Câmera ativada com sucesso",
            "id", camera.getId(),
            "nome", camera.getNome(),
            "ativa", camera.isAtiva()
    );
}

@PatchMapping("/{cameraId}/desativar")
public Map<String, Object> desativar(
        @PathVariable Long cameraId) {

    Camera camera = cameraService.alterarEstado(cameraId, false);

    return Map.of(
            "mensagem", "Câmera desativada com sucesso",
            "id", camera.getId(),
            "nome", camera.getNome(),
            "ativa", camera.isAtiva()
    );
}
}
