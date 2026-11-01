package br.kaizen.ConectaLar.repository;

import br.kaizen.ConectaLar.model.Comunidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComunidadeRepository extends JpaRepository<Comunidade, Long> {

    Optional<Comunidade> findByCep(String cep);

    List<Comunidade> findByBairroIgnoreCase(String bairro);
}