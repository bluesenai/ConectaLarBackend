
package br.kaizen.ConectaLar.repository;

import br.kaizen.ConectaLar.model.AssinaturaPeticao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssinaturaPeticaoRepository
        extends JpaRepository<AssinaturaPeticao, Long> {

    boolean existsByPeticaoIdAndUsuarioId(
            Long peticaoId,
            Long usuarioId
    );

    long countByPeticaoId(Long peticaoId);
}