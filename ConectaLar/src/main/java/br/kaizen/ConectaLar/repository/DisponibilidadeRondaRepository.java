package br.kaizen.ConectaLar.repository;

import br.kaizen.ConectaLar.model.DisponibilidadeRonda;
import br.kaizen.ConectaLar.model.PrestadorServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisponibilidadeRondaRepository
        extends JpaRepository<DisponibilidadeRonda, Long> {

    List<DisponibilidadeRonda> findByPrestadorId(Long prestadorId);

    long countByPrestadorId(Long prestadorId);

    boolean existsByPrestadorAndDiaSemanaAndPeriodo(
            PrestadorServico prestador,
            br.kaizen.ConectaLar.model.DiaSemana diaSemana,
            br.kaizen.ConectaLar.model.PeriodoRonda periodo
    );
}