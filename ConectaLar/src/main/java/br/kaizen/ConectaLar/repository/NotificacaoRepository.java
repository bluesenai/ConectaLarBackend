
package br.kaizen.ConectaLar.repository;

import br.kaizen.ConectaLar.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface NotificacaoRepository
        extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findByDestinatarioIdOrderByDataCriacaoDesc(
            Long usuarioId
    );

    long countByDestinatarioIdAndLidaFalse(Long usuarioId);
    
    @Modifying
@Transactional
@Query("""
    UPDATE Notificacao n
    SET n.lida = true
    WHERE n.destinatario.id = :usuarioId
      AND n.lida = false
    """)
int marcarTodasComoLidas(
        @Param("usuarioId") Long usuarioId
);
}
