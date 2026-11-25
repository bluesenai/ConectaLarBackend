
package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.DTO.PrestadorCadastroRequest;
import br.kaizen.ConectaLar.model.Comunidade;
import br.kaizen.ConectaLar.model.PrestadorComunidade;
import br.kaizen.ConectaLar.model.PrestadorServico;
import br.kaizen.ConectaLar.repository.ComunidadeRepository;
import br.kaizen.ConectaLar.repository.PrestadorComunidadeRepository;
import br.kaizen.ConectaLar.repository.PrestadorServicoRepository;
import br.kaizen.ConectaLar.model.PrestadorComunidade;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestadorServicoService {

    private final PrestadorServicoRepository prestadorRepository;
    private final ComunidadeRepository comunidadeRepository;
    private final PrestadorComunidadeRepository prestadorComunidadeRepository;
    private final PasswordEncoder passwordEncoder;

    public PrestadorServicoService(
            PrestadorServicoRepository prestadorRepository,
            ComunidadeRepository comunidadeRepository,
            PrestadorComunidadeRepository prestadorComunidadeRepository,
            PasswordEncoder passwordEncoder) {

        this.prestadorRepository = prestadorRepository;
        this.comunidadeRepository = comunidadeRepository;
        this.prestadorComunidadeRepository = prestadorComunidadeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public PrestadorServico cadastrar(PrestadorCadastroRequest request) {

        if (prestadorRepository.existsByCpf(request.getCpf())) {
            throw new RuntimeException("Este CPF já está cadastrado");
        }

        if (prestadorRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Este e-mail já está cadastrado");
        }

        if (request.getComunidadeId() == null) {
            throw new RuntimeException(
                    "Selecione uma comunidade para realizar as rondas");
        }

        Comunidade comunidade = comunidadeRepository
                .findById(request.getComunidadeId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Comunidade selecionada não encontrada"));

        PrestadorServico prestador = new PrestadorServico();

        prestador.setNome(request.getNome());
        prestador.setDataNascimento(request.getDataNascimento());
        prestador.setCpf(request.getCpf());
        prestador.setTelefone(request.getTelefone());
        prestador.setEmail(request.getEmail());
        prestador.setSenha(
                passwordEncoder.encode(request.getSenha()));

        prestador = prestadorRepository.save(prestador);

        PrestadorComunidade vinculo = new PrestadorComunidade();
        vinculo.setPrestador(prestador);
        vinculo.setComunidade(comunidade);

        prestadorComunidadeRepository.save(vinculo);

        return prestador;
    }
    
    
@Transactional
public PrestadorComunidade adicionarComunidade(
        String emailPrestador,
        Long comunidadeId) {

    PrestadorServico prestador = prestadorRepository
            .findByEmail(emailPrestador)
            .orElseThrow(() ->
                    new RuntimeException("Prestador não encontrado"));

    Comunidade comunidade = comunidadeRepository
            .findById(comunidadeId)
            .orElseThrow(() ->
                    new RuntimeException("Comunidade não encontrada"));

    if (prestadorComunidadeRepository
            .existsByPrestadorIdAndComunidadeId(
                    prestador.getId(), comunidade.getId())) {

        throw new RuntimeException(
                "Este prestador já está associado a esta comunidade");
    }

    PrestadorComunidade vinculo = new PrestadorComunidade();
    vinculo.setPrestador(prestador);
    vinculo.setComunidade(comunidade);

    return prestadorComunidadeRepository.save(vinculo);
}

public List<Comunidade> listarComunidades(String emailPrestador) {

    PrestadorServico prestador = prestadorRepository
            .findByEmail(emailPrestador)
            .orElseThrow(() ->
                    new RuntimeException("Prestador não encontrado"));

    return prestadorComunidadeRepository
            .findByPrestadorId(prestador.getId())
            .stream()
            .map(PrestadorComunidade::getComunidade)
            .toList();
}
}
