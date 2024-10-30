package agenda.BarberShop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agenda.BarberShop.entity.Barbeiro;
import agenda.BarberShop.repository.BarbeiroRepository;

@Service
public class BarbeiroService {

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    public List<Barbeiro> findAll() {
        return barbeiroRepository.findAll();
    }

    public Barbeiro findById(long id) {
        return barbeiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado!"));
    }

    public String save(Barbeiro barbeiro) {
        barbeiroRepository.save(barbeiro);
        return "Barbeiro cadastrado com sucesso!";
    }

    public String update(Barbeiro barbeiro, long id) {
        Barbeiro existingBarbeiro = findById(id);
        existingBarbeiro.setNome(barbeiro.getNome());
        existingBarbeiro.setCpf(barbeiro.getCpf());
        existingBarbeiro.setStatus(barbeiro.getStatus());
        barbeiroRepository.save(existingBarbeiro);
        return "Barbeiro atualizado com sucesso!";
    }

    public String delete(long id) {
        Barbeiro barbeiro = findById(id);
        barbeiroRepository.delete(barbeiro);
        return "Barbeiro deletado com sucesso!";
    }
}
