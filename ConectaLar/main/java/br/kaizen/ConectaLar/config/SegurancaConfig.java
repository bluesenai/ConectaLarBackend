
package br.kaizen.ConectaLar.config;

import br.kaizen.ConectaLar.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SegurancaConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SegurancaConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
@Bean
public SecurityFilterChain filtroSeguranca(HttpSecurity http)
        throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        .headers(headers ->
            headers.frameOptions(frame -> frame.disable())
        )
        .sessionManagement(session ->
            session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
            )
        )
        .authorizeHttpRequests(auth -> auth

            // Console H2
            .requestMatchers("/h2-console/**").permitAll()

            // Logins públicos
            .requestMatchers(
                "/usuario/login",
                "/administrador/login"
            ).permitAll()

            // Cadastros públicos
            .requestMatchers(
                "/usuario/cadastro",
                "/prestador/cadastro",
                "/administrador/cadastrar",
                "/comunidade/cadastrar",
                "/comunidade/entrar"
            ).permitAll()

            // Funcionalidades exclusivas do administrador
            .requestMatchers("/administrador/**")
                .hasAuthority("ROLE_ADMINISTRADOR")

            // Funcionalidades do prestador
            .requestMatchers("/prestador/**")
                .hasAuthority("ROLE_PRESTADOR")

            // Funcionalidades do morador
            .requestMatchers("/seguranca/morador/**")
                .hasAuthority("ROLE_MORADOR")

            // Gravações exigem autenticação
            .requestMatchers("/gravacao/**").authenticated()

            // Todas as demais rotas exigem autenticação
            .anyRequest().authenticated()
        )
        .addFilterBefore(
            jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class
        )
        .formLogin(form -> form.disable())
        .httpBasic(basic -> basic.disable());

    return http.build();
 }
}

