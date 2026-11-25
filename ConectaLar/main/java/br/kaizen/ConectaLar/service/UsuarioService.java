package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.model.Usuario;
import br.kaizen.ConectaLar.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import br.kaizen.ConectaLar.model.Comunidade;
import br.kaizen.ConectaLar.DTO.MoradorResponse;
import java.util.List;
import br.kaizen.ConectaLar.model.PrestadorServico;
import br.kaizen.ConectaLar.repository.PrestadorServicoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PrestadorServicoRepository prestadorRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
        UsuarioRepository usuarioRepository,
        PrestadorServicoRepository prestadorRepository,
        PasswordEncoder passwordEncoder) {

    this.usuarioRepository = usuarioRepository;
    this.prestadorRepository = prestadorRepository;
    this.passwordEncoder = passwordEncoder;
}

    public Object login(String email, String senha) {

    Optional<Usuario> usuario =
            usuarioRepository.findByEmail(email);

    if (usuario.isPresent()) {

        Usuario morador = usuario.get();
        String senhaArmazenada = morador.getSenha();

        boolean senhaValida;

        if (senhaArmazenada.startsWith("$2a$")
                || senhaArmazenada.startsWith("$2b$")
                || senhaArmazenada.startsWith("$2y$")) {

            senhaValida = passwordEncoder.matches(
                    senha, senhaArmazenada);

        } else {

            senhaValida = senhaArmazenada.equals(senha);

            if (senhaValida) {
                morador.setSenha(passwordEncoder.encode(senha));
                usuarioRepository.save(morador);
            }
        }

        if (senhaValida) {
            return morador;
        }
    }

    Optional<PrestadorServico> prestador =
            prestadorRepository.findByEmail(email);

    if (prestador.isPresent()) {

        PrestadorServico conta = prestador.get();

        if (passwordEncoder.matches(senha, conta.getSenha())) {
            return conta;
        }
    }

    return null;
}

    public Usuario cadastrar(Usuario usuario) {

    usuario.setSenha(
            passwordEncoder.encode(usuario.getSenha()));

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
