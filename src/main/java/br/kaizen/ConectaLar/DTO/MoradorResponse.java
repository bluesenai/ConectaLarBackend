
package br.kaizen.ConectaLar.DTO;

public class MoradorResponse {

    private Long id;
    private String nome;

    public MoradorResponse() {
    }

    public MoradorResponse(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}