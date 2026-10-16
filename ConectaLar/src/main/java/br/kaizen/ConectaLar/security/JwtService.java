
package br.kaizen.ConectaLar.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final SecretKey chave;
    private final long validade;

    public JwtService(
            @Value("${jwt.secret}") String segredo,
            @Value("${jwt.expiration}") long validade) {

        byte[] bytes = segredo.getBytes(StandardCharsets.UTF_8);

        if (bytes.length < 32) {
            throw new IllegalArgumentException(
                "A chave JWT deve ter pelo menos 32 bytes."
            );
        }

        this.chave = Keys.hmacShaKeyFor(bytes);
        this.validade = validade;
    }

    public String gerarToken(Long administradorId, String email) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + validade);

        return Jwts.builder()
                .subject(email)
                .claim("administradorId", administradorId)
                .claim("tipo", "ADMINISTRADOR")
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(chave)
                .compact();
    }

    private Claims obterClaims(String token) {
        return Jwts.parser()
                .verifyWith(chave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validarToken(String token) {
        try {
            Claims claims = obterClaims(token);

            return "ADMINISTRADOR".equals(
                    claims.get("tipo", String.class)
                )
                && claims.getSubject() != null
                && claims.get("administradorId", Number.class) != null;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String obterEmail(String token) {
        return obterClaims(token).getSubject();
    }

    public Long obterAdministradorId(String token) {
        Number id = obterClaims(token)
                .get("administradorId", Number.class);

        return id.longValue();
    }
}
