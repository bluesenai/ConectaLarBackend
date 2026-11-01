package br.kaizen.ConectaLar.service;

import br.kaizen.ConectaLar.DTO.PrestadorCadastroRequest;
import br.kaizen.ConectaLar.model.Comunidade;
import br.kaizen.ConectaLar.model.PrestadorServico;
import br.kaizen.ConectaLar.repository.ComunidadeRepository;
import br.kaizen.ConectaLar.repository.PrestadorServicoRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PrestadorServicoService {

    private final PrestadorServicoRepository prestadorRepository;
    private final ComunidadeRepository comunidadeRepository;
    private final PasswordEncoder passwordEncoder;

    public PrestadorServicoService(
            PrestadorServicoRepository prestadorRepository,
            ComunidadeRepository comunidadeRepository,
            PasswordEncoder passwordEncoder) {

        this.prestadorRepository = prestadorRepository;
        this.comunidadeRepository = comunidadeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PrestadorServico cadastrar(PrestadorCadastroRequest request) {

        if (prestadorRepository.existsByCpf(request.getCpf())) {
            throw new RuntimeException("Este CPF já está cadastrado");
        }

        if (prestadorRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Este e-mail já está cadastrado");
        }

        if (request.getComunidadeId() == null) {
            throw new RuntimeException(
                    "Selecione uma rua para realizar as rondas");
        }

        Comunidade comunidade = comunidadeRepository
                .findById(request.getComunidadeId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Rua selecionada não encontrada"));

        PrestadorServico prestador = new PrestadorServico();

        prestador.setNome(request.getNome());
        prestador.setDataNascimento(request.getDataNascimento());
        prestador.setCpf(request.getCpf());
        prestador.setTelefone(request.getTelefone());
        prestador.setEmail(request.getEmail());

        // Criptografa a senha antes de salvar
        prestador.setSenha(
                passwordEncoder.encode(request.getSenha()));

        prestador.setComunidade(comunidade);

        return prestadorRepository.save(prestador);
    }
}