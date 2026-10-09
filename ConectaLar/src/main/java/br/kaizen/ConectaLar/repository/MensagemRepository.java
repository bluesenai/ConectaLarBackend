package br.kaizen.ConectaLar.repository;

import br.kaizen.ConectaLar.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    List<Mensagem> findByComunidadeIdOrderByDataHoraAsc(Long comunidadeId);
}