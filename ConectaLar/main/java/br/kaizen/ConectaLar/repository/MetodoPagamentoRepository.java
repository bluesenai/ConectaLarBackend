package br.kaizen.ConectaLar.repository;

import br.kaizen.ConectaLar.model.MetodoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MetodoPagamentoRepository
        extends JpaRepository<MetodoPagamento, Long> {

    Optional<MetodoPagamento> findByChavePix(String chavePix);

    boolean existsByChavePix(String chavePix);

    Optional<MetodoPagamento> findByPrestadorId(Long prestadorId);

    boolean existsByPrestadorId(Long prestadorId);
}