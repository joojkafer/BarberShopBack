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

    public String save(Servico servico) {
        servicoRepository.save(servico);
        return "Serviço salvo com sucesso!";
    }

    public String update(Servico servico, long id) {
        Optional<Servico> optionalServico = servicoRepository.findById(id);
        if (optionalServico.isPresent()) {
            servico.setIdServico(id);
            servicoRepository.save(servico);
            return "Serviço atualizado com sucesso!";
        } else {
            return "Serviço não encontrado!";
        }
    }

    public Servico findById(long id) {
        return servicoRepository.findById(id).orElse(null);
    }

    public List<Servico> findAll() {
        return servicoRepository.findAll();
    }

    public String delete(long id) {
        if (servicoRepository.existsById(id)) {
            servicoRepository.deleteById(id);
            return "Serviço deletado com sucesso!";
        } else {
            return "Serviço não encontrado!";
        }
    }
}