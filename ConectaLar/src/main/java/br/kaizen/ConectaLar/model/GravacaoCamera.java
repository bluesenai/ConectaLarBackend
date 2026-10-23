
package br.kaizen.ConectaLar.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gravacao_camera")
public class GravacaoCamera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime inicio;

    private LocalDateTime fim;

    @Column(length = 1000)
    private String urlArquivoNuvem;

    @Column(nullable = false, length = 30)
    private String status = "PROCESSANDO";

    @ManyToOne(optional = false)
    @JoinColumn(name = "camera_id", nullable = false)
    private Camera camera;

    public GravacaoCamera() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

    public String getUrlArquivoNuvem() {
        return urlArquivoNuvem;
    }

    public void setUrlArquivoNuvem(String urlArquivoNuvem) {
        this.urlArquivoNuvem = urlArquivoNuvem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
