
package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.StatusTransmissao;
import br.kaizen.ConectaLar.model.Camera;
import br.kaizen.ConectaLar.repository.CameraRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.net.Socket;

@Service
public class TransmissaoService {

    private final CameraRepository cameraRepository;

    @Value("${camera.rtsp.host:}")
    private String host;

    @Value("${camera.rtsp.port:554}")
    private int porta;

    @Value("${camera.connection-timeout:5000}")
    private int timeout;

    public TransmissaoService(CameraRepository cameraRepository) {
        this.cameraRepository = cameraRepository;
    }

    public StatusTransmissao consultarStatus(Long cameraId) {
        Camera camera = cameraRepository.findById(cameraId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Câmera não encontrada."));

        if (!camera.isAtiva()) {
            return new StatusTransmissao(
                    camera.getId(),
                    false,
                    "DESATIVADA",
                    "A câmera está cadastrada, mas está desativada."
            );
        }

        if (host == null || host.isBlank()) {
            return new StatusTransmissao(
                    camera.getId(),
                    false,
                    "AGUARDANDO_CONFIGURACAO",
                    "Informe o endereço IP da câmera para verificar a conexão."
            );
        }

        try (Socket socket = new Socket()) {
            socket.connect(
                    new InetSocketAddress(host, porta),
                    timeout
            );

            return new StatusTransmissao(
                    camera.getId(),
                    true,
                    "PORTA_RTSP_ACESSIVEL",
                    "A porta RTSP está acessível. A transmissão de vídeo ainda precisa ser validada."
            );

        } catch (Exception e) {
            return new StatusTransmissao(
                    camera.getId(),
                    false,
                    "SEM_CONEXAO",
                    "Não foi possível estabelecer conexão com a porta RTSP da câmera."
            );
        }
    }
}
