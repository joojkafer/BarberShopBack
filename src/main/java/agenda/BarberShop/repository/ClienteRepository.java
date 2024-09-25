package agenda.BarberShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import agenda.BarberShop.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}

