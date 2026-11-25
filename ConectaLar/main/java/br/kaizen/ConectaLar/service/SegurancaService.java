package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.DTO.SegurancaResponse;
import br.kaizen.ConectaLar.DTO.DisponibilidadeRondaResponse;
import br.kaizen.ConectaLar.model.Usuario;
import br.kaizen.ConectaLar.model.PrestadorServico;
import br.kaizen.ConectaLar.repository.UsuarioRepository;
import br.kaizen.ConectaLar.repository.PrestadorServicoRepository;
import br.kaizen.ConectaLar.repository.DisponibilidadeRondaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SegurancaService {

    private final UsuarioRepository usuarioRepository;
    private final PrestadorServicoRepository prestadorRepository;
    private final DisponibilidadeRondaRepository disponibilidadeRepository;

    public SegurancaService(
            UsuarioRepository usuarioRepository,
            PrestadorServicoRepository prestadorRepository,
            DisponibilidadeRondaRepository disponibilidadeRepository) {

        this.usuarioRepository = usuarioRepository;
        this.prestadorRepository = prestadorRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
    }

    public SegurancaResponse consultar(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException(
                        "Morador não encontrado"));

        if (usuario.getComunidade() == null) {
            throw new RuntimeException(
                    "O morador ainda não está vinculado a uma comunidade");
        }

        Long comunidadeId = usuario.getComunidade().getId();

        PrestadorServico prestador = prestadorRepository
                .findByComunidadeId(comunidadeId)
                .orElseThrow(() -> new RuntimeException(
                        "Esta comunidade ainda não possui prestador responsável"));

       List<DisponibilidadeRondaResponse> horarios =
        disponibilidadeRepository
                .findByPrestadorId(prestador.getId())
                .stream()
                .sorted(
                    java.util.Comparator
                        .comparing((br.kaizen.ConectaLar.model.DisponibilidadeRonda r)
                                -> r.getDiaSemana().ordinal())
                        .thenComparing(r -> r.getPeriodo().ordinal())
                )
                        .map(ronda -> new DisponibilidadeRondaResponse(
                                ronda.getId(),
                                ronda.getDiaSemana(),
                                ronda.getPeriodo(),
                                ronda.getHorario()
                        ))
                        .toList();

        return new SegurancaResponse(
                prestador.getNome(),
                horarios
        );
    }
}