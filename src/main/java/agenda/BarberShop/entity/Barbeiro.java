package agenda.BarberShop.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
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
//Audith
@EntityListeners(AuditingEntityListener.class)
public class Barbeiro {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private long idBarbeiro;

 @NotBlank(message = "O nome não pode ser vazio.")
 @Pattern(regexp = "^[\\p{L}]+(?:\\s+[\\p{L}]+)+$", message = "O nome deve conter pelo menos duas palavras.")
 private String nome;

 @CPF(message = "O CPF deve seguir o padrão XXX.XXX.XXX-XX")
 private String cpf;

 @NotNull(message = "O status não pode ser nulo.")
 private Boolean status;
 
//Audith changes
 @CreatedDate
 private LocalDateTime createDate;
 @LastModifiedDate
 private LocalDateTime lastModified;
 @CreatedBy
 private String createdBy;
 @LastModifiedBy
 private String modifiedBy;

 // Remova ou comente os campos relacionados a Agendamento
 // @OneToMany(mappedBy = "barbeiro")
 // @JsonIgnoreProperties("barbeiro")
 // private List<Agendamento> agendamentos;
}
