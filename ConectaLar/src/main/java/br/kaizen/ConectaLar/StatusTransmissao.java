
package br.kaizen.ConectaLar;

public class StatusTransmissao {

    private Long cameraId;
    private boolean conectada;
    private String status;
    private String mensagem;

    public StatusTransmissao() {
    }

    public StatusTransmissao(
            Long cameraId,
            boolean conectada,
            String status,
            String mensagem) {
        this.cameraId = cameraId;
        this.conectada = conectada;
        this.status = status;
        this.mensagem = mensagem;
    }

    public Long getCameraId() {
        return cameraId;
    }

    public void setCameraId(Long cameraId) {
        this.cameraId = cameraId;
    }

    public boolean isConectada() {
        return conectada;
    }

    public void setConectada(boolean conectada) {
        this.conectada = conectada;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
