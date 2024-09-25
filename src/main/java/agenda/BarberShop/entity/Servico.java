package agenda.BarberShop.entity;

import jakarta.persistence.Entity;
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

    public Double getValor() {
        return valor;
    }
}
