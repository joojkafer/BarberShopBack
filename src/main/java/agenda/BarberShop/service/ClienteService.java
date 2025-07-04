package agenda.BarberShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agenda.BarberShop.entity.Cliente;
import agenda.BarberShop.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public String save(Cliente cliente) {
        clienteRepository.save(cliente);
        return "Cliente salvo com sucesso!";
    }

    public String update(Cliente cliente, long id) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        if (optionalCliente.isPresent()) {
            cliente.setIdCliente(id);
            clienteRepository.save(cliente);
            return "Cliente atualizado com sucesso!";
        } else {
            throw new RuntimeException("Cliente não encontrado!");
        }
    }

    public Cliente findById(long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public String delete(long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return "Cliente deletado com sucesso!";
        } else {
            throw new RuntimeException("Cliente não encontrado!");
        }
    }
}
