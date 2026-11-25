package br.kaizen.ConectaLar.DTO;

import br.kaizen.ConectaLar.model.DiaSemana;
import br.kaizen.ConectaLar.model.PeriodoRonda;

import java.time.LocalTime;

public class DisponibilidadeRondaRequest {

    private DiaSemana diaSemana;
    private PeriodoRonda periodo;
    private LocalTime horario;

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