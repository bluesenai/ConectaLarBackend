package br.kaizen.ConectaLar.DTO;

import br.kaizen.ConectaLar.model.DiaSemana;
import br.kaizen.ConectaLar.model.PeriodoRonda;
import java.time.LocalTime;

public class DisponibilidadeRondaResponse {

    private Long id;
    private DiaSemana diaSemana;
    private PeriodoRonda periodo;
    private LocalTime horario;

    public DisponibilidadeRondaResponse() {
    }

    public DisponibilidadeRondaResponse(
            Long id,
            DiaSemana diaSemana,
            PeriodoRonda periodo,
            LocalTime horario) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.periodo = periodo;
        this.horario = horario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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