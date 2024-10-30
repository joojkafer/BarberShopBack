package agenda.BarberShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import agenda.BarberShop.entity.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
}