
package br.kaizen.ConectaLar.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "assinatura_peticao",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"peticao_id", "usuario_id"}
        )
    }
)
public class AssinaturaPeticao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataAssinatura;

    @ManyToOne(optional = false)
    @JoinColumn(name = "peticao_id", nullable = false)
    private Peticao peticao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public AssinaturaPeticao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(LocalDateTime dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    public Peticao getPeticao() {
        return peticao;
    }

    public void setPeticao(Peticao peticao) {
        this.peticao = peticao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}