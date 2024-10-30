// FuncionarioService.java
package agenda.BarberShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agenda.BarberShop.entity.Funcionario;
import agenda.BarberShop.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public String save(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
        return "Funcionário salvo com sucesso!";
    }

    public String update(Funcionario funcionario, long id) {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if (optionalFuncionario.isPresent()) {
            Funcionario existingFuncionario = optionalFuncionario.get();
            existingFuncionario.setNome(funcionario.getNome());
            existingFuncionario.setLogin(funcionario.getLogin());
            existingFuncionario.setRole(funcionario.getRole());
            if (funcionario.getSenha() != null && !funcionario.getSenha().isEmpty()) {
                existingFuncionario.setSenha(funcionario.getSenha());
            }
            funcionarioRepository.save(existingFuncionario);
            return "Funcionário atualizado com sucesso!";
        } else {
            throw new RuntimeException("Funcionário não encontrado!");
        }
    }

    public Funcionario findById(long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado!"));
    }

    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    public String delete(long id) {
        if (funcionarioRepository.existsById(id)) {
            funcionarioRepository.deleteById(id);
            return "Funcionário deletado com sucesso!";
        } else {
            throw new RuntimeException("Funcionário não encontrado!");
        }
    }
}
