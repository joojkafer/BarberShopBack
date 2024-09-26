package app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Agendamento;
import app.entity.Servico;
import app.repository.AgendamentoRepository;
import app.repository.BarbeiroRepository;
import app.repository.ClienteRepository;
import app.repository.FuncionarioRepository;
import app.repository.ServicoRepository;
import jakarta.transaction.Transactional;

@Service
public class AgendamentoService {

    private static final Logger logger = Logger.getLogger(AgendamentoService.class.getName());

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Transactional
    public String save(Agendamento agendamento) {
        if (!clienteRepository.existsById(agendamento.getCliente().getIdCliente())) {
            return "Erro: Cliente não encontrado!";
        }
        if (!barbeiroRepository.existsById(agendamento.getBarbeiro().getIdBarbeiro())) {
            return "Erro: Barbeiro não encontrado!";
        }
        if (!funcionarioRepository.existsById(agendamento.getFuncionario().getIdUsuario())) {
            return "Erro: Funcionário não encontrado!";
        }

        for (Servico servico : agendamento.getServicos()) {
            if (!servicoRepository.existsById(servico.getIdServico())) {
                return "Erro: Serviço não encontrado!";
            }
        }

        if (agendamento.getHorariosAgendamento().isBefore(LocalDateTime.now().plusHours(24))) {
            return "Erro: Agendamentos devem ser feitos com pelo menos 24 horas de antecedência.";
        }

  
        boolean existeConflito = agendamentoRepository.existsByBarbeiroAndHorariosAgendamento(
            agendamento.getBarbeiro(),
            agendamento.getHorariosAgendamento()
        );
        if (existeConflito) {
            return "Erro: Já existe um agendamento para este barbeiro neste horário.";
        }


        calcularValorTotal(agendamento);

        Agendamento savedAgendamento = agendamentoRepository.save(agendamento);

        return "Agendamento salvo com sucesso!";
    }

    public String update(Agendamento agendamento, long id) {
        Optional<Agendamento> optionalAgendamento = agendamentoRepository.findById(id);
        if (optionalAgendamento.isPresent()) {
            Agendamento existingAgendamento = optionalAgendamento.get();
            existingAgendamento.setHorariosAgendamento(agendamento.getHorariosAgendamento());
            existingAgendamento.setCliente(agendamento.getCliente());
            existingAgendamento.setBarbeiro(agendamento.getBarbeiro());
            existingAgendamento.setFuncionario(agendamento.getFuncionario());
            existingAgendamento.setServicos(agendamento.getServicos());

            calcularValorTotal(existingAgendamento);

            agendamentoRepository.save(existingAgendamento);
            return "Agendamento atualizado com sucesso!";
        } else {
            return "Agendamento não encontrado!";
        }
    }

    public Agendamento findById(long id) {
        return agendamentoRepository.findById(id).orElse(null);
    }

    public List<Agendamento> findAll(LocalDateTime dataInicio, LocalDateTime dataFim, Long idBarbeiro, Long idFuncionario) {
        return agendamentoRepository.findAllByFilters(dataInicio, dataFim, idBarbeiro, idFuncionario);
    }

    public String delete(long id) {
        Optional<Agendamento> agendamentoOptional = agendamentoRepository.findById(id);
        if (agendamentoOptional.isPresent()) {
            Agendamento agendamento = agendamentoOptional.get();

           
            if (agendamento.getHorariosAgendamento().isBefore(LocalDateTime.now().plusHours(12))) {
                return "Erro: Cancelamentos de agendamentos devem ser tratados diretamente com a recepção.";
            }

            agendamentoRepository.deleteById(id);
            return "Agendamento deletado com sucesso!";
        } else {
            return "Agendamento não encontrado!";
        }
    }

    private void calcularValorTotal(Agendamento agendamento) {
        List<Servico> servicosCompletos = agendamento.getServicos().stream()
            .map(servico -> servicoRepository.findById(servico.getIdServico())
                                             .orElseThrow(() -> new IllegalArgumentException("Serviço com ID " + servico.getIdServico() + " não encontrado")))
            .collect(Collectors.toList());

        servicosCompletos.forEach(servico -> {
            logger.info("Serviço ID: " + servico.getIdServico() + ", Nome: " + servico.getNome() + ", Valor: " + servico.getValor());
            if (servico.getValor() == null) {
                throw new IllegalArgumentException("O valor do serviço não pode ser nulo.");
            }
        });

        double valorTotal = servicosCompletos.stream()
            .mapToDouble(Servico::getValor)
            .sum();

        agendamento.setValorTotal(valorTotal);
    }
}