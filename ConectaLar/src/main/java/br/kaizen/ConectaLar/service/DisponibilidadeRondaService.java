package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.model.*;
import br.kaizen.ConectaLar.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Service
public class DisponibilidadeRondaService {

    private final DisponibilidadeRondaRepository disponibilidadeRepository;
    private final PrestadorServicoRepository prestadorRepository;

    public DisponibilidadeRondaService(
            DisponibilidadeRondaRepository disponibilidadeRepository,
            PrestadorServicoRepository prestadorRepository) {

        this.disponibilidadeRepository = disponibilidadeRepository;
        this.prestadorRepository = prestadorRepository;
    }

    @Transactional
    public List<DisponibilidadeRonda> cadastrar(
            Long prestadorId,
            List<DisponibilidadeRonda> rondas) {

        PrestadorServico prestador = prestadorRepository
                .findById(prestadorId)
                .orElseThrow(() ->
                        new RuntimeException("Prestador não encontrado"));

        if (rondas == null || rondas.size() != 9) {
            throw new RuntimeException(
                    "Devem ser cadastradas exatamente 9 rondas por semana");
        }

        Set<DiaSemana> dias = new HashSet<>();

        Set<String> combinacoes = new HashSet<>();

        for (DisponibilidadeRonda ronda : rondas) {

            if (ronda.getDiaSemana() == null
                    || ronda.getPeriodo() == null
                    || ronda.getHorario() == null) {

                throw new RuntimeException(
                        "Todos os dias, períodos e horários devem ser preenchidos");
            }

            dias.add(ronda.getDiaSemana());

            String combinacao =
                    ronda.getDiaSemana() + "_" + ronda.getPeriodo();

            if (!combinacoes.add(combinacao)) {
                throw new RuntimeException(
                        "Não pode haver duas rondas no mesmo período e dia");
            }

            ronda.setPrestador(prestador);
        }

        if (dias.size() != 3) {
            throw new RuntimeException(
                    "Escolha exatamente 3 dias diferentes da semana");
        }

        for (DiaSemana dia : dias) {

            for (PeriodoRonda periodo : PeriodoRonda.values()) {

                String combinacao = dia + "_" + periodo;

                if (!combinacoes.contains(combinacao)) {
                    throw new RuntimeException(
                            "Cada dia deve possuir rondas de manhã, tarde e noite");
                }
            }
        }

        return disponibilidadeRepository.saveAll(rondas);
    }

    public List<DisponibilidadeRonda> listar(Long prestadorId) {

        if (!prestadorRepository.existsById(prestadorId)) {
            throw new RuntimeException("Prestador não encontrado");
        }

        return disponibilidadeRepository.findByPrestadorId(prestadorId);
    }
}