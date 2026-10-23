
package br.kaizen.ConectaLar.DTO;

public class LoginResponse {

    private Long id;
    private String nome;
    private String email;
    private Long comunidadeId;

    public LoginResponse(
            Long id,
            String nome,
            String email,
            Long comunidadeId) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.comunidadeId = comunidadeId;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Long getComunidadeId() {
        return comunidadeId;
    }
}
