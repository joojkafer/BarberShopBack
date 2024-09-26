package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Barbeiro;

public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {

}

