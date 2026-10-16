
package br.kaizen.ConectaLar.DTO;

public class CadastrarCameraRequest {

    private String nome;
    private String endereco;
    private String localizacao;
    private Long comunidadeId;
    private Long responsavelId;

    public CadastrarCameraRequest() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Long getComunidadeId() {
        return comunidadeId;
    }

    public void setComunidadeId(Long comunidadeId) {
        this.comunidadeId = comunidadeId;
    }

    public Long getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(Long responsavelId) {
        this.responsavelId = responsavelId;
    }
}
