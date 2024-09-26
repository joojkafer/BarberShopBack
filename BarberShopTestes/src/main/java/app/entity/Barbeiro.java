package app.entity;

import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Barbeiro {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idBarbeiro;

	@NotBlank(message = "O nome não pode ser vazio.")
    @Pattern(regexp = "^[\\p{L}]+(?:\\s+[\\p{L}]+)+$", message = "O nome deve conter pelo menos duas palavras.")
	private String nome;

	@CPF (message = "O CPF deve seguir o padrão XXX.XXX.XXX-XX")
	private String cpf;

	@NotNull (message = "O status não pode ser nulo.")
	private Boolean status;

	@OneToMany(mappedBy = "barbeiro")
	@JsonIgnoreProperties("barbeiro")
	private List<Agendamento> agendamentos;
}