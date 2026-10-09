package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.model.Comunidade;
import br.kaizen.ConectaLar.model.Usuario;
import br.kaizen.ConectaLar.repository.ComunidadeRepository;
import br.kaizen.ConectaLar.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class ComunidadeService {

    private final ComunidadeRepository comunidadeRepository;
    private final UsuarioRepository usuarioRepository;

    public ComunidadeService(
            ComunidadeRepository comunidadeRepository,
            UsuarioRepository usuarioRepository) {

        this.comunidadeRepository = comunidadeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario entrarNaComunidade(Long usuarioId, String cep) {

        Comunidade comunidade = comunidadeRepository.findByCep(cep)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Comunidade não encontrada para este CEP"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"));

        usuario.setComunidade(comunidade);

        return usuarioRepository.save(usuario);
    }

    public Comunidade criarComunidade(Comunidade comunidade) {

        if (comunidadeRepository.findByCep(comunidade.getCep()).isPresent()) {

            throw new RuntimeException(
                    "Já existe uma comunidade para este CEP");
        }

        return comunidadeRepository.save(comunidade);
    }
}