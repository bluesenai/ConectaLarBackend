
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

    public String gerarToken(
        Long id,
        String email,
        String tipo) {

    Date agora = new Date();
    Date expiracao = new Date(agora.getTime() + validade);

    return Jwts.builder()
            .subject(email)
            .claim("usuarioId", id)
            .claim("tipo", tipo)
            .issuedAt(agora)
            .expiration(expiracao)
            .signWith(chave)
            .compact();
}
    
    public String obterTipo(String token) {
    return obterClaims(token).get("tipo", String.class);
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

        String tipo = claims.get("tipo", String.class);

        return ("ADMINISTRADOR".equals(tipo)
                || "MORADOR".equals(tipo)
                || "PRESTADOR".equals(tipo))
                && claims.getSubject() != null
                && claims.get("usuarioId", Number.class) != null;

    } catch (JwtException | IllegalArgumentException e) {
        return false;
    }
}
    
    public Long obterUsuarioId(String token) {
    Number id = obterClaims(token)
            .get("usuarioId", Number.class);

    return id.longValue();
}

    public String obterEmail(String token) {
        return obterClaims(token).getSubject();
    }
}
