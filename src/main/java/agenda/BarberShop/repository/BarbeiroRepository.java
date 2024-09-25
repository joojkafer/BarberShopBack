package agenda.BarberShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import agenda.BarberShop.entity.Barbeiro;

public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {

}

