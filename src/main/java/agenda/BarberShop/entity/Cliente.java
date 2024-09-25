package agenda.BarberShop.entity;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCliente;

    @NotBlank(message = "O nome não pode ser vazio.")
    @Pattern(regexp = "^[\\p{L}]+(?:\\s+[\\p{L}]+)+$", message = "O nome deve conter pelo menos duas palavras.")
	private String nome;

    @CPF (message = "O CPF deve seguir o padrão XXX.XXX.XXX-XX")
	private String cpf;

    @NotBlank(message = "O nome não pode ser vazio.")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "O telefone deve seguir o padrão: (XX) XXXX-XXXX ou (XX) XXXXX-XXXX.")
	private String telefone;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnoreProperties("cliente")
    private List<Agendamento> agendamentos;

}