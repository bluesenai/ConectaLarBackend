
package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.model.Camera;
import br.kaizen.ConectaLar.model.GravacaoCamera;
import br.kaizen.ConectaLar.repository.CameraRepository;
import br.kaizen.ConectaLar.repository.GravacaoCameraRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GravacaoCameraService {

    private final GravacaoCameraRepository gravacaoRepository;
    private final CameraRepository cameraRepository;

    public GravacaoCameraService(
            GravacaoCameraRepository gravacaoRepository,
            CameraRepository cameraRepository) {

        this.gravacaoRepository = gravacaoRepository;
        this.cameraRepository = cameraRepository;
    }

    @Transactional
    public GravacaoCamera cadastrar(
            Long cameraId,
            LocalDateTime inicio,
            LocalDateTime fim,
            String urlArquivoNuvem,
            String status) {

        if (cameraId == null || inicio == null) {
            throw new IllegalArgumentException(
                    "Informe a câmera e o início da gravação.");
        }

        if (fim != null && fim.isBefore(inicio)) {
            throw new IllegalArgumentException(
                    "O término não pode ser anterior ao início.");
        }

        Camera camera = cameraRepository.findById(cameraId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Câmera não encontrada."));

        GravacaoCamera gravacao = new GravacaoCamera();
        gravacao.setCamera(camera);
        gravacao.setInicio(inicio);
        gravacao.setFim(fim);
        gravacao.setUrlArquivoNuvem(urlArquivoNuvem);
        gravacao.setStatus(
                status == null || status.isBlank()
                        ? "PROCESSANDO"
                        : status.trim().toUpperCase());

        return gravacaoRepository.save(gravacao);
    }

    public List<GravacaoCamera> listarPorCamera(Long cameraId) {
        return gravacaoRepository.findByCameraId(cameraId);
    }

    public List<GravacaoCamera> listarPorComunidade(
            Long comunidadeId) {

        return gravacaoRepository
                .findByCameraComunidadeId(comunidadeId);
    }
}
