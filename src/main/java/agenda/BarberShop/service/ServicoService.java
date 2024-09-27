package agenda.BarberShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agenda.BarberShop.entity.Servico;
import agenda.BarberShop.repository.ServicoRepository;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public String save(Servico servico) throws Exception {
        if (servico.getNome() == null || servico.getNome().isEmpty()) {
            throw new Exception("O nome do serviço não pode ser nulo.");
        }
        servicoRepository.save(servico);
        return "Serviço salvo com sucesso!";
    }

    public String update(Servico servico, long id) throws Exception {
        Optional<Servico> optionalServico = servicoRepository.findById(id);
        if (optionalServico.isPresent()) {
            servico.setIdServico(id);
            servicoRepository.save(servico);
            return "Serviço atualizado com sucesso!";
        } else {
            throw new Exception("Serviço não encontrado!");
        }
    }

    public Servico findById(long id) throws Exception {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new Exception("Serviço não encontrado!"));
    }

    public List<Servico> findAll() throws Exception {
        return servicoRepository.findAll();
    }

    public String delete(long id) throws Exception {
        if (servicoRepository.existsById(id)) {
            servicoRepository.deleteById(id);
            return "Serviço deletado com sucesso!";
        } else {
            throw new Exception("Serviço não encontrado!");
        }
    }
}
