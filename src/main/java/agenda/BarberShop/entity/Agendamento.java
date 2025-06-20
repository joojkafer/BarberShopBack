package agenda.BarberShop.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//Audith changes
@EntityListeners(AuditingEntityListener.class)
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAgendamento;

    @NotNull(message = "O horário do agendamento não pode ser nulo.")
    private LocalDateTime horariosAgendamento;

    private double valorTotal;

    @ManyToOne
    @JsonIgnoreProperties("agendamentos")
    @NotNull(message = "O agendamento deve estar relacionado a um cliente.")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "barbeiro_idBarbeiro", nullable = false)
    @JsonIgnoreProperties("agendamentos")
    private Barbeiro barbeiro;

    @ManyToOne
    @JsonIgnoreProperties("agendamentos")
    @NotNull(message = "O agendamento deve estar relacionado a um funcionário.")
    private Funcionario funcionario;

    @NotEmpty(message = "O agendamento deve ter pelo menos um serviço relacionado.")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "agendamento_servico",
        joinColumns = @JoinColumn(name = "agendamento_id_agendamento"),
        inverseJoinColumns = @JoinColumn(name = "servicos_id_servico")
    )
    @JsonIgnoreProperties("agendamentos")
    private List<Servico> servicos;
    
	//Audith changes
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime lastModified;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String modifiedBy;
    
  public long getIdAgendamento() {
		return idAgendamento;
	}
	public void setIdAgendamento(long idAgendamento) {
		this.idAgendamento = idAgendamento;
	}
	public LocalDateTime getHorariosAgendamento() {
		return horariosAgendamento;
	}
	public void setHorariosAgendamento(LocalDateTime horariosAgendamento) {
		this.horariosAgendamento = horariosAgendamento;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Barbeiro getBarbeiro() {
		return barbeiro;
	}
	public void setBarbeiro(Barbeiro barbeiro) {
		this.barbeiro = barbeiro;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public List<Servico> getServicos() {
		return servicos;
	}
	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	public LocalDateTime getLastModified() {
		return lastModified;
	}
	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
