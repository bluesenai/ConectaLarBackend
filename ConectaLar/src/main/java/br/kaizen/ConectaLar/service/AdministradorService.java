
package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.model.Administrador;
import br.kaizen.ConectaLar.repository.AdministradorRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    public AdministradorService(
            AdministradorRepository administradorRepository,
            PasswordEncoder passwordEncoder) {

        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Administrador cadastrar(
            String nome,
            String email,
            String senha) {

        if (nome == null || nome.isBlank()
                || email == null || email.isBlank()
                || senha == null || senha.length() < 8) {

            throw new IllegalArgumentException(
                    "Informe nome e e-mail válidos e uma senha com pelo menos 8 caracteres.");
        }

        String emailNormalizado = email.trim().toLowerCase();

        if (administradorRepository.existsByEmail(emailNormalizado)) {
            throw new IllegalArgumentException(
                    "Já existe um administrador com esse e-mail.");
        }

        Administrador administrador = new Administrador();
        administrador.setNome(nome.trim());
        administrador.setEmail(emailNormalizado);
        administrador.setSenha(passwordEncoder.encode(senha));
        administrador.setAtivo(true);

        return administradorRepository.save(administrador);
    }
    
public boolean validarLogin(String email, String senha) {

    if (email == null || email.isBlank()
            || senha == null || senha.isBlank()) {
        return false;
    }

    return administradorRepository
            .findByEmail(email.trim().toLowerCase())
            .filter(Administrador::isAtivo)
            .filter(admin ->
                    passwordEncoder.matches(
                            senha, admin.getSenha()))
            .isPresent();
}

public Administrador autenticar(String email, String senha) {

    if (email == null || email.isBlank()
            || senha == null || senha.isBlank()) {
        return null;
    }

    return administradorRepository
            .findByEmail(email.trim().toLowerCase())
            .filter(Administrador::isAtivo)
            .filter(admin ->
                    passwordEncoder.matches(
                            senha, admin.getSenha()))
            .orElse(null);
}

}
