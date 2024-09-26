package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}

