
package br.kaizen.ConectaLar.repository;

import br.kaizen.ConectaLar.model.Peticao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeticaoRepository
        extends JpaRepository<Peticao, Long> {

    List<Peticao> findByComunidadeIdOrderByDataCriacaoDesc(
            Long comunidadeId
    );
}