
package br.kaizen.ConectaLar.repository;

import br.kaizen.ConectaLar.model.CategoriaReclamacao;
import br.kaizen.ConectaLar.model.NivelRelevancia;
import br.kaizen.ConectaLar.model.Reclamacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReclamacaoRepository
        extends JpaRepository<Reclamacao, Long> {

    @Query("""
        SELECT COUNT(r)
        FROM Reclamacao r
        WHERE r.comunidade.id = :comunidadeId
          AND r.categoria = :categoria
          AND r.nivelRelevancia = :nivel
          AND r.dataCriacao >= :inicio
        """)
    long contarReclamacoesRecentes(
            @Param("comunidadeId") Long comunidadeId,
            @Param("categoria") CategoriaReclamacao categoria,
            @Param("nivel") NivelRelevancia nivel,
            @Param("inicio") LocalDateTime inicio
    );

    boolean existsByComunidadeIdAndCategoriaAndDataCriacaoGreaterThanEqualAndAlertaEmitidoTrue(
            Long comunidadeId,
            CategoriaReclamacao categoria,
            LocalDateTime inicio
    );

    List<Reclamacao> findByComunidadeIdOrderByDataCriacaoDesc(
            Long comunidadeId
    );
}
