package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.model.Usuario;
import br.kaizen.ConectaLar.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import br.kaizen.ConectaLar.model.Comunidade;
import br.kaizen.ConectaLar.DTO.MoradorResponse;
import java.util.List;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> login(String email, String senha) {

        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if (usuario.isPresent() && usuario.get().getSenha().equals(senha)) {
            return usuario;
        }

        return Optional.empty();
    }

    public Usuario cadastrar(Usuario usuario) {

        return usuarioRepository.save(usuario);
    }
    public Comunidade buscarComunidade(Long usuarioId) {

    Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() ->
                    new RuntimeException("Usuário não encontrado"));

    if (usuario.getComunidade() == null) {
        throw new RuntimeException(
                "Usuário ainda não pertence a uma comunidade");
    }

    return usuario.getComunidade();
}
    
public List<MoradorResponse> buscarMoradores(Long comunidadeId) {

    List<Usuario> usuarios =
            usuarioRepository.findByComunidadeId(comunidadeId);

    return usuarios.stream()
            .map(usuario -> new MoradorResponse(
                    usuario.getId(),
                    usuario.getNome()
            ))
            .toList();
}
}
