package br.kaizen.ConectaLar.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "disponibilidade_ronda")
public class DisponibilidadeRonda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prestador_id", nullable = false)
    private PrestadorServico prestador;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiaSemana diaSemana;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PeriodoRonda periodo;

    @Column(nullable = false)
    private LocalTime horario;

    public DisponibilidadeRonda() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PrestadorServico getPrestador() {
        return prestador;
    }

    public void setPrestador(PrestadorServico prestador) {
        this.prestador = prestador;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public PeriodoRonda getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoRonda periodo) {
        this.periodo = periodo;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }
}
