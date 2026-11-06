
package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.StatusTransmissao;
import br.kaizen.ConectaLar.model.Camera;
import br.kaizen.ConectaLar.repository.CameraRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransmissaoService {

    private final CameraRepository cameraRepository;
    private final MediaMtxService mediaMtxService;

    @Value("${camera.rtsp.host:}")
    private String host;

    public TransmissaoService(
            CameraRepository cameraRepository,
            MediaMtxService mediaMtxService) {

        this.cameraRepository = cameraRepository;
        this.mediaMtxService = mediaMtxService;
    }

    public StatusTransmissao consultarStatus(Long cameraId) {

        Camera camera = cameraRepository.findById(cameraId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Câmera não encontrada."
                        ));

        if (!camera.isAtiva()) {
            return new StatusTransmissao(
                    camera.getId(),
                    false,
                    "DESATIVADA",
                    "A câmera está cadastrada, mas está desativada."
            );
        }

        String caminho = mediaMtxService.gerarCaminho(camera.getId());

        boolean transmitindo =
                mediaMtxService.estaTransmitindo(caminho);

        if (transmitindo) {
            return new StatusTransmissao(
                    camera.getId(),
                    true,
                    "TRANSMITINDO",
                    "O MediaMTX está recebendo uma transmissão no caminho da câmera."
            );
        }

        return new StatusTransmissao(
                camera.getId(),
                false,
                "SEM_TRANSMISSAO",
                "Não existe uma transmissão ativa no MediaMTX para esta câmera."
        );
    }
}
