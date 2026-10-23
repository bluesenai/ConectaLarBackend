
package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.model.Camera;
import br.kaizen.ConectaLar.model.Comunidade;
import br.kaizen.ConectaLar.model.Usuario;
import br.kaizen.ConectaLar.repository.CameraRepository;
import br.kaizen.ConectaLar.repository.ComunidadeRepository;
import br.kaizen.ConectaLar.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CameraService {

    private final CameraRepository cameraRepository;
    private final ComunidadeRepository comunidadeRepository;
    private final UsuarioRepository usuarioRepository;

    public CameraService(
            CameraRepository cameraRepository,
            ComunidadeRepository comunidadeRepository,
            UsuarioRepository usuarioRepository) {

        this.cameraRepository = cameraRepository;
        this.comunidadeRepository = comunidadeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Camera cadastrar(
            String nome,
            String endereco,
            String localizacao,
            Long comunidadeId,
            Long responsavelId) {

        if (nome == null || nome.isBlank()
                || endereco == null || endereco.isBlank()
                || comunidadeId == null
                || responsavelId == null) {

            throw new IllegalArgumentException(
                    "Informe o nome, endereço, comunidade e responsável.");
        }

        Comunidade comunidade = comunidadeRepository
                .findById(comunidadeId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Comunidade não encontrada."));

        Usuario responsavel = usuarioRepository
                .findById(responsavelId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Morador responsável não encontrado."));

        if (responsavel.getComunidade() == null
                || !responsavel.getComunidade().getId()
                        .equals(comunidade.getId())) {

            throw new IllegalArgumentException(
                    "O responsável precisa pertencer à comunidade da câmera.");
        }

        Camera camera = new Camera();
        camera.setNome(nome.trim());
        camera.setEndereco(endereco.trim());
        camera.setLocalizacao(localizacao);
        camera.setComunidade(comunidade);
        camera.setResponsavel(responsavel);
        camera.setDataCadastro(LocalDateTime.now());

        // Uma câmera começa desativada até ser configurada.
        camera.setAtiva(false);

        return cameraRepository.save(camera);
    }

    public List<Camera> listarPorComunidade(Long comunidadeId) {
        return cameraRepository.findByComunidadeId(comunidadeId);
    }

    public List<Camera> listarAtivasPorComunidade(
            Long comunidadeId) {

        return cameraRepository
                .findByComunidadeIdAndAtivaTrue(comunidadeId);
    }
    
@Transactional
public Camera alterarEstado(Long cameraId, boolean ativa) {

    Camera camera = cameraRepository.findById(cameraId)
            .orElseThrow(() ->
                    new IllegalArgumentException(
                            "Câmera não encontrada."));

    camera.setAtiva(ativa);

    return cameraRepository.save(camera);
}
}
