package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

}

