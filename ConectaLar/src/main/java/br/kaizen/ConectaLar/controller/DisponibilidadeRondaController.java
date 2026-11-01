package br.kaizen.ConectaLar.controller;

import br.kaizen.ConectaLar.DTO.DisponibilidadeRondaRequest;
import br.kaizen.ConectaLar.DTO.DisponibilidadeRondaResponse;
import br.kaizen.ConectaLar.model.DisponibilidadeRonda;
import br.kaizen.ConectaLar.service.DisponibilidadeRondaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prestador")
public class DisponibilidadeRondaController {

    private final DisponibilidadeRondaService disponibilidadeService;

    public DisponibilidadeRondaController(
            DisponibilidadeRondaService disponibilidadeService) {
        this.disponibilidadeService = disponibilidadeService;
    }

    @PostMapping("/{prestadorId}/disponibilidade")
    public ResponseEntity<?> cadastrar(
            @PathVariable Long prestadorId,
            @RequestBody List<DisponibilidadeRondaRequest> requests) {

        try {
            List<DisponibilidadeRonda> rondas = requests.stream().map(request -> {
                DisponibilidadeRonda ronda = new DisponibilidadeRonda();

                ronda.setDiaSemana(request.getDiaSemana());
                ronda.setPeriodo(request.getPeriodo());
                ronda.setHorario(request.getHorario());

                return ronda;
            }).toList();

            List<DisponibilidadeRonda> salvas =
                    disponibilidadeService.cadastrar(prestadorId, rondas);

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

    @GetMapping("/{prestadorId}/disponibilidade")
    public ResponseEntity<?> listar(@PathVariable Long prestadorId) {

        try {
            List<DisponibilidadeRondaResponse> rondas =
                    disponibilidadeService.listar(prestadorId)
                            .stream()
                            .map(ronda -> new DisponibilidadeRondaResponse(
                                    ronda.getId(),
                                    ronda.getDiaSemana(),
                                    ronda.getPeriodo(),
                                    ronda.getHorario()
                            ))
                            .collect(Collectors.toList());

            return ResponseEntity.ok(rondas);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro", e.getMessage())
            );
        }
    }
}