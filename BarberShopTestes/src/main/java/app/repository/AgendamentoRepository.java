package app.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.entity.Agendamento;
import app.entity.Barbeiro;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByBarbeiroAndHorariosAgendamento(Barbeiro barbeiro, LocalDateTime horariosAgendamento);

    @Query("SELECT a FROM Agendamento a WHERE "
         + "(:dataInicio IS NULL OR a.horariosAgendamento >= :dataInicio) AND "
         + "(:dataFim IS NULL OR a.horariosAgendamento <= :dataFim) AND "
         + "(:idBarbeiro IS NULL OR a.barbeiro.idBarbeiro = :idBarbeiro) AND "
         + "(:idFuncionario IS NULL OR a.funcionario.idUsuario = :idFuncionario)")
    List<Agendamento> findAllByFilters(@Param("dataInicio") LocalDateTime dataInicio,
                                       @Param("dataFim") LocalDateTime dataFim,
                                       @Param("idBarbeiro") Long idBarbeiro,
                                       @Param("idFuncionario") Long idFuncionario);
}