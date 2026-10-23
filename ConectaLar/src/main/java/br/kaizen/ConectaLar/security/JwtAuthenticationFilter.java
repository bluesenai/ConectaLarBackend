
package br.kaizen.ConectaLar.security;

import br.kaizen.ConectaLar.model.Administrador;
import br.kaizen.ConectaLar.repository.AdministradorRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AdministradorRepository administradorRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            AdministradorRepository administradorRepository) {
        this.jwtService = jwtService;
        this.administradorRepository = administradorRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization != null
                && authorization.startsWith("Bearer ")) {

            String token = authorization.substring(7);

            if (!jwtService.validarToken(token)) {
                responderNaoAutorizado(response);
                return;
            }

            Long administradorId;
            String email;

            try {
                administradorId = jwtService.obterAdministradorId(token);
                email = jwtService.obterEmail(token);
            } catch (RuntimeException e) {
                responderNaoAutorizado(response);
                return;
            }

            Optional<Administrador> resultado =
                    administradorRepository.findById(administradorId);

            if (resultado.isEmpty()) {
                responderNaoAutorizado(response);
                return;
            }

            Administrador administrador = resultado.get();

            if (!administrador.isAtivo()
                    || !administrador.getEmail().equalsIgnoreCase(email)) {
                responderNaoAutorizado(response);
                return;
            }

            var autenticacao =
                    new UsernamePasswordAuthenticationToken(
                            administrador.getEmail(),
                            null,
                            List.of(new SimpleGrantedAuthority(
                                    "ROLE_ADMINISTRADOR"))
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(autenticacao);
        }

        filterChain.doFilter(request, response);
    }

    private void responderNaoAutorizado(
            HttpServletResponse response) throws IOException {

        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                "{\"mensagem\":\"Token inválido, expirado ou administrador inativo.\"}"
        );
    }
}
