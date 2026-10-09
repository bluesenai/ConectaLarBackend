package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.DTO.CadastroRequest;
import br.kaizen.ConectaLar.DTO.LoginRequest;
import br.kaizen.ConectaLar.model.Usuario;
import br.kaizen.ConectaLar.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.kaizen.ConectaLar.model.Comunidade;

import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Optional<Usuario> usuario = usuarioService.login(
                request.getEmail(),
                request.getSenha()
        );

        if (usuario.isPresent()) {

            return ResponseEntity.ok(
                    "Login realizado com sucesso"
            );
        }

        return ResponseEntity
                .status(401)
                .body("E-mail ou senha incorretos");
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