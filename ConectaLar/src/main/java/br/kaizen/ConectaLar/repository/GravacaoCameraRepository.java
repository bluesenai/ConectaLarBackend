
package br.kaizen.ConectaLar.repository;

import br.kaizen.ConectaLar.model.GravacaoCamera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GravacaoCameraRepository
        extends JpaRepository<GravacaoCamera, Long> {

    List<GravacaoCamera> findByCameraId(Long cameraId);

    List<GravacaoCamera> findByCameraComunidadeId(Long comunidadeId);
}
