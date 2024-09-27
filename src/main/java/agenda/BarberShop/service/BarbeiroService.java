package agenda.BarberShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agenda.BarberShop.entity.Barbeiro;
import agenda.BarberShop.repository.BarbeiroRepository;

@Service
public class BarbeiroService {

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    public String save(Barbeiro barbeiro) {
        barbeiroRepository.save(barbeiro);
        return "Barbeiro salvo com sucesso!";
    }

    public String update(Barbeiro barbeiro, long id) {
        Optional<Barbeiro> optionalBarbeiro = barbeiroRepository.findById(id);
        if (optionalBarbeiro.isPresent()) {
            Barbeiro existingBarbeiro = optionalBarbeiro.get();
            existingBarbeiro.setNome(barbeiro.getNome());
            existingBarbeiro.setCpf(barbeiro.getCpf());
            existingBarbeiro.setStatus(barbeiro.getStatus());
            existingBarbeiro.setAgendamentos(barbeiro.getAgendamentos());

            barbeiroRepository.save(existingBarbeiro);
            return "Barbeiro atualizado com sucesso!";
        }
        throw new RuntimeException("Barbeiro não encontrado!");
    }

    public Barbeiro findById(long id) {
        return barbeiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado!"));
    }

    public List<Barbeiro> findAll() {
        return barbeiroRepository.findAll();
    }

    public String delete(Long id) {
        if (!barbeiroRepository.existsById(id)) {
            throw new RuntimeException("Barbeiro não encontrado!");
        }
        barbeiroRepository.deleteById(id);
        return "Barbeiro deletado com sucesso!";
    }
}
