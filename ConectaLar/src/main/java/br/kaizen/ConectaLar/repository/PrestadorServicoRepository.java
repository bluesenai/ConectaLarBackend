package br.kaizen.ConectaLar.repository;

import br.kaizen.ConectaLar.model.PrestadorServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrestadorServicoRepository
        extends JpaRepository<PrestadorServico, Long> {

    Optional<PrestadorServico> findByCpf(String cpf);

    Optional<PrestadorServico> findByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}