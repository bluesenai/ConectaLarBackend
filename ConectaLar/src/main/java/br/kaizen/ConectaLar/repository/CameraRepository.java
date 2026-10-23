
package br.kaizen.ConectaLar.repository;

import br.kaizen.ConectaLar.model.Camera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CameraRepository
        extends JpaRepository<Camera, Long> {

    List<Camera> findByComunidadeId(Long comunidadeId);

    List<Camera> findByComunidadeIdAndAtivaTrue(
            Long comunidadeId);
}
