
package br.kaizen.ConectaLar.security;

import br.kaizen.ConectaLar.model.Administrador;
import br.kaizen.ConectaLar.model.Usuario;
import br.kaizen.ConectaLar.model.PrestadorServico;

import br.kaizen.ConectaLar.repository.AdministradorRepository;
import br.kaizen.ConectaLar.repository.UsuarioRepository;
import br.kaizen.ConectaLar.repository.PrestadorServicoRepository;

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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AdministradorRepository administradorRepository;
    private final UsuarioRepository usuarioRepository;
    private final PrestadorServicoRepository prestadorRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            AdministradorRepository administradorRepository,
            UsuarioRepository usuarioRepository,
            PrestadorServicoRepository prestadorRepository) {

        this.jwtService = jwtService;
        this.administradorRepository = administradorRepository;
        this.usuarioRepository = usuarioRepository;
        this.prestadorRepository = prestadorRepository;
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

            try {
                Long id = jwtService.obterUsuarioId(token);
                String email = jwtService.obterEmail(token);
                String tipo = jwtService.obterTipo(token);

                UsernamePasswordAuthenticationToken autenticacao;

                switch (tipo) {

                    case "ADMINISTRADOR":

                        Administrador administrador =
                                administradorRepository.findById(id)
                                        .orElse(null);

                        if (administrador == null
                                || !administrador.isAtivo()
                                || administrador.getEmail() == null
                                || !administrador.getEmail()
                                        .equalsIgnoreCase(email)) {

                            responderNaoAutorizado(response);
                            return;
                        }

                        autenticacao =
                                new UsernamePasswordAuthenticationToken(
                                        administrador.getEmail(),
                                        null,
                                        List.of(new SimpleGrantedAuthority(
                                                "ROLE_ADMINISTRADOR"))
                                );
                        break;

                    case "MORADOR":

                        Usuario usuario =
                                usuarioRepository.findById(id)
                                        .orElse(null);

                        if (usuario == null
                                || usuario.getEmail() == null
                                || !usuario.getEmail()
                                        .equalsIgnoreCase(email)) {

                            responderNaoAutorizado(response);
                            return;
                        }

                        autenticacao =
                                new UsernamePasswordAuthenticationToken(
                                        usuario.getEmail(),
                                        null,
                                        List.of(new SimpleGrantedAuthority(
                                                "ROLE_MORADOR"))
                                );
                        break;

                    case "PRESTADOR":

                        PrestadorServico prestador =
                                prestadorRepository.findById(id)
                                        .orElse(null);

                        if (prestador == null
                                || prestador.getEmail() == null
                                || !prestador.getEmail()
                                        .equalsIgnoreCase(email)) {

                            responderNaoAutorizado(response);
                            return;
                        }

                        autenticacao =
                                new UsernamePasswordAuthenticationToken(
                                        prestador.getEmail(),
                                        null,
                                        List.of(new SimpleGrantedAuthority(
                                                "ROLE_PRESTADOR"))
                                );
                        break;

                    default:
                        responderNaoAutorizado(response);
                        return;
                }

                SecurityContextHolder.getContext()
                        .setAuthentication(autenticacao);

            } catch (RuntimeException e) {
                responderNaoAutorizado(response);
                return;
            }
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
                "{\"mensagem\":\"Token inválido, expirado ou conta não encontrada.\"}"
        );
    }
}
