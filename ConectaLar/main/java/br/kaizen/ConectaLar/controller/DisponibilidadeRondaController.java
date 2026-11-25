
package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.DTO.DisponibilidadeRondaRequest;
import br.kaizen.ConectaLar.DTO.DisponibilidadeRondaResponse;
import br.kaizen.ConectaLar.model.DisponibilidadeRonda;
import br.kaizen.ConectaLar.service.DisponibilidadeRondaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prestador")
public class DisponibilidadeRondaController {

    private final DisponibilidadeRondaService disponibilidadeService;

    public DisponibilidadeRondaController(
            DisponibilidadeRondaService disponibilidadeService) {
        this.disponibilidadeService = disponibilidadeService;
    }

    @PostMapping("/{prestadorId}/comunidade/{comunidadeId}/disponibilidade")
    public ResponseEntity<?> cadastrar(
            @PathVariable Long prestadorId,
            @PathVariable Long comunidadeId,
            @RequestBody List<DisponibilidadeRondaRequest> requests) {

        try {
            List<DisponibilidadeRonda> rondas = converterRequests(requests);

            List<DisponibilidadeRonda> salvas =
                    disponibilidadeService.cadastrar(
                            prestadorId, comunidadeId, rondas);

            return ResponseEntity.ok(Map.of(
                    "mensagem", "Disponibilidade cadastrada com sucesso",
                    "quantidade", salvas.size()
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro", e.getMessage())
            );
        }
    }

    @GetMapping("/{prestadorId}/comunidade/{comunidadeId}/disponibilidade")
    public ResponseEntity<?> listar(
            @PathVariable Long prestadorId,
            @PathVariable Long comunidadeId) {

        try {
            List<DisponibilidadeRondaResponse> rondas =
                    disponibilidadeService.listar(
                            prestadorId, comunidadeId)
                            .stream()
                            .map(ronda -> new DisponibilidadeRondaResponse(
                                    ronda.getId(),
                                    ronda.getDiaSemana(),
                                    ronda.getPeriodo(),
                                    ronda.getHorario()
                            ))
                            .toList();

            return ResponseEntity.ok(rondas);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro", e.getMessage())
            );
        }
    }

    @PutMapping("/{prestadorId}/comunidade/{comunidadeId}/disponibilidade")
    public ResponseEntity<?> atualizar(
            @PathVariable Long prestadorId,
            @PathVariable Long comunidadeId,
            @RequestBody List<DisponibilidadeRondaRequest> requests) {

        try {
            List<DisponibilidadeRonda> rondas = converterRequests(requests);

            List<DisponibilidadeRonda> atualizadas =
                    disponibilidadeService.atualizar(
                            prestadorId, comunidadeId, rondas);

            return ResponseEntity.ok(Map.of(
                    "mensagem", "Escala atualizada com sucesso",
                    "quantidade", atualizadas.size()
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro", e.getMessage())
            );
        }
    }

    private List<DisponibilidadeRonda> converterRequests(
            List<DisponibilidadeRondaRequest> requests) {

        if (requests == null) {
            throw new RuntimeException(
                    "A lista de rondas não pode ser nula.");
        }

        return requests.stream()
                .map(request -> {
                    DisponibilidadeRonda ronda =
                            new DisponibilidadeRonda();

                    ronda.setDiaSemana(request.getDiaSemana());
                    ronda.setPeriodo(request.getPeriodo());
                    ronda.setHorario(request.getHorario());

                    return ronda;
                })
                .toList();
    }
}
