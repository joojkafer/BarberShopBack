package agenda.BarberShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import agenda.BarberShop.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}

