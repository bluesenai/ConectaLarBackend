
package br.kaizen.ConectaLar.DTO;

public class LoginAdministradorRequest {

    private String email;
    private String senha;

    public LoginAdministradorRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
