package agenda.BarberShop.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@EntityListeners(AuditingEntityListener.class)
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idServico;

    @NotNull(message = "O nome do serviço não pode ser nulo.")
    private String nome;

    @NotNull(message = "A descrição do serviço não pode ser nula.")
    private String descricao;

    @NotNull(message = "O valor do serviço não pode ser nulo.")
    private Double valor;
    
    //Audith changes
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime lastModified;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String modifiedBy;
    
 // Getters
    public long getIdServico() {
        return idServico;
    }
    
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    // Setters (se necessário)
    public void setIdServico(long idServico) {
        this.idServico = idServico;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}