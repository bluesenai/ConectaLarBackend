package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.DTO.CadastroRequest;
import br.kaizen.ConectaLar.DTO.LoginRequest;
import br.kaizen.ConectaLar.model.Usuario;
import br.kaizen.ConectaLar.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.kaizen.ConectaLar.model.Comunidade;
import br.kaizen.ConectaLar.DTO.LoginResponse;
import br.kaizen.ConectaLar.model.PrestadorServico;
import br.kaizen.ConectaLar.security.JwtService;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public UsuarioController(
        UsuarioService usuarioService,
        JwtService jwtService) {

    this.usuarioService = usuarioService;
    this.jwtService = jwtService;
}

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {

    Object resultado = usuarioService.login(
            request.getEmail(),
            request.getSenha()
    );

    if (resultado == null) {
        return ResponseEntity
                .status(401)
                .body(Map.of(
                        "mensagem", "E-mail ou senha incorretos"
                ));
    }

    if (resultado instanceof Usuario usuario) {

        String token = jwtService.gerarToken(
                usuario.getId(),
                usuario.getEmail(),
                "MORADOR"
        );

        Long comunidadeId = usuario.getComunidade() != null
                ? usuario.getComunidade().getId()
                : null;

        return ResponseEntity.ok(Map.of(
                "id", usuario.getId(),
                "nome", usuario.getNome(),
                "email", usuario.getEmail(),
                "comunidadeId", comunidadeId == null ? 0 : comunidadeId,
                "tipoUsuario", "MORADOR",
                "token", token
        ));
    }

    if (resultado instanceof PrestadorServico prestador) {

        String token = jwtService.gerarToken(
                prestador.getId(),
                prestador.getEmail(),
                "PRESTADOR"
        );

        return ResponseEntity.ok(Map.of(
                "id", prestador.getId(),
                "nome", prestador.getNome(),
                "email", prestador.getEmail(),
                "tipoUsuario", "PRESTADOR",
                "token", token
        ));
    }

    return ResponseEntity.internalServerError().body(
            Map.of("mensagem", "Tipo de conta não reconhecido")
    );
}

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody CadastroRequest request) {

        if (!request.getSenha().equals(request.getConfirmarSenha())) {

            return ResponseEntity
                    .badRequest()
                    .body("As senhas não são iguais");
        }

        Usuario usuario = new Usuario();

        usuario.setNome(request.getNome());
        usuario.setDataNascimento(request.getDataNascimento());
        usuario.setTelefone(request.getTelefone());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(request.getSenha());
        usuario.setCep(request.getCep());
        usuario.setEstado(request.getEstado());
        usuario.setCidade(request.getCidade());
        usuario.setBairro(request.getBairro());
        usuario.setRua(request.getRua());
        usuario.setNumeroCasa(request.getNumeroCasa());

        Usuario usuarioSalvo = usuarioService.cadastrar(usuario);

        return ResponseEntity.ok(usuarioSalvo);
    }
    @GetMapping("/{usuarioId}/comunidade")
public ResponseEntity<?> buscarComunidade(
        @PathVariable Long usuarioId) {

    try {

        Comunidade comunidade =
                usuarioService.buscarComunidade(usuarioId);

        return ResponseEntity.ok(comunidade);

    } catch (RuntimeException e) {

        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}
}