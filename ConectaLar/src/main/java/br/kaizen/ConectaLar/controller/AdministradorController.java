
package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.model.Administrador;
import br.kaizen.ConectaLar.service.AdministradorService;

import br.kaizen.ConectaLar.DTO.LoginAdministradorRequest;
import br.kaizen.ConectaLar.security.JwtService;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {

    private final AdministradorService administradorService;
    private final JwtService jwtService;


    
public AdministradorController(
        AdministradorService administradorService,
        JwtService jwtService) {

    this.administradorService = administradorService;
    this.jwtService = jwtService;
}
    
@PostMapping("/login")
public ResponseEntity<Map<String, Object>> login(
        @RequestBody LoginAdministradorRequest request) {

    Administrador administrador = administradorService.autenticar(
            request.getEmail(),
            request.getSenha()
    );

    if (administrador == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "mensagem",
                        "E-mail ou senha inválidos."
                ));
    }

    String token = jwtService.gerarToken(
            administrador.getId(),
            administrador.getEmail()
    );

    return ResponseEntity.ok(Map.of(
            "mensagem", "Login realizado com sucesso",
            "token", token,
            "tipo", "Bearer",
            "administradorId", administrador.getId()
    ));
}
@PostMapping("/cadastrar")
public ResponseEntity<Map<String, Object>> cadastrar(
        @RequestBody Map<String, String> dados) {

    try {
        Administrador administrador = administradorService.cadastrar(
            dados.get("nome"),
            dados.get("email"),
            dados.get("senha")
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
            Map.of(
                "mensagem", "Administrador cadastrado com sucesso",
                "id", administrador.getId(),
                "email", administrador.getEmail()
            )
        );

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(
            Map.of("mensagem", e.getMessage())
        );
    }
}
}
