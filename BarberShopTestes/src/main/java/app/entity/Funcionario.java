package app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
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
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuario;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotBlank(message = "O nome não pode ser vazio.")
    @Pattern(regexp = "^[\\p{L}]+(?:\\s+[\\p{L}]+)+$", message = "O nome deve conter pelo menos duas palavras.")
    private String nome;

    @NotBlank(message = "O login não pode ser vazio.")
    private String login;

    @JsonIgnore
    @NotBlank(message = "A senha não pode ser vazia.")
    private String senha;

    @OneToMany(mappedBy = "funcionario")
    @JsonIgnoreProperties("funcionario")
    private List<Agendamento> agendamentos;

    public void setIdFuncionario(long id) {
        this.idUsuario = id;
    }
}