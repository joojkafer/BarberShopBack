package agenda.BarberShop.servicetest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import agenda.BarberShop.entity.Agendamento;
import agenda.BarberShop.entity.Barbeiro;
import agenda.BarberShop.entity.Cliente;
import agenda.BarberShop.entity.Funcionario;
import agenda.BarberShop.entity.Servico;
import agenda.BarberShop.repository.*;
import agenda.BarberShop.service.AgendamentoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoServiceTest {

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Mock
    private AgendamentoRepository agendamentoRepository;
    
    @Mock
    private ClienteRepository clienteRepository;
    
    @Mock
    private BarbeiroRepository barbeiroRepository;
    
    @Mock
    private FuncionarioRepository funcionarioRepository;
    
    @Mock
    private ServicoRepository servicoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave_Sucesso() {
        Agendamento agendamento = criarAgendamentoMock();
        
        // Mockando as verificações de existência
        when(clienteRepository.existsById(agendamento.getCliente().getIdCliente())).thenReturn(true);
        when(barbeiroRepository.existsById(agendamento.getBarbeiro().getIdBarbeiro())).thenReturn(true);
        when(funcionarioRepository.existsById(agendamento.getFuncionario().getIdUsuario())).thenReturn(true);
        
        // Mockando os serviços
        when(servicoRepository.existsById(anyLong())).thenReturn(true);
        when(servicoRepository.findById(anyLong())).thenReturn(Optional.of(new Servico(1L, "Corte", null, 30.0)));
        
        // Mockando a verificação de conflito de agendamento
        when(agendamentoRepository.existsByBarbeiroAndHorariosAgendamento(any(), any())).thenReturn(false);
        
        // Mockando o salvamento
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);

        // Chamando o método e verificando o resultado
        String resultado = agendamentoService.save(agendamento);
        assertEquals("Agendamento salvo com sucesso!", resultado);
    }

    @Test
    public void testSave_ClienteNaoEncontrado() {
        Agendamento agendamento = criarAgendamentoMock();
        when(clienteRepository.existsById(agendamento.getCliente().getIdCliente())).thenReturn(false);

        // Verificando se a exceção é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            agendamentoService.save(agendamento);
        });
        assertEquals("Erro: Cliente não encontrado!", exception.getMessage());

        // Verificando que o método save não foi chamado
        verify(agendamentoRepository, times(0)).save(any(Agendamento.class));
    }

    @Test
    public void testSave_AgendamentoMenos24Horas() {
        Agendamento agendamento = criarAgendamentoMock();
        agendamento.setHorariosAgendamento(LocalDateTime.now().plusHours(23));
        
        // Garantindo que o cliente, barbeiro, funcionário e serviços existam
        when(clienteRepository.existsById(agendamento.getCliente().getIdCliente())).thenReturn(true);
        when(barbeiroRepository.existsById(agendamento.getBarbeiro().getIdBarbeiro())).thenReturn(true);
        when(funcionarioRepository.existsById(agendamento.getFuncionario().getIdUsuario())).thenReturn(true);
        when(servicoRepository.existsById(anyLong())).thenReturn(true);
        when(servicoRepository.findById(anyLong())).thenReturn(Optional.of(new Servico(1L, "Corte", null, 30.0)));
        
        // Mockando que não há conflitos de agendamento
        when(agendamentoRepository.existsByBarbeiroAndHorariosAgendamento(any(), any())).thenReturn(false);

        // Verificando se a exceção é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            agendamentoService.save(agendamento);
        });
        assertEquals("Erro: Agendamentos devem ser feitos com pelo menos 24 horas de antecedência.", exception.getMessage());
    }

    @Test
    public void testUpdate_AgendamentoNaoEncontrado() {
        Agendamento agendamento = criarAgendamentoMock();
        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Verificando se a exceção é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            agendamentoService.update(agendamento, 1L);
        });
        assertEquals("Agendamento não encontrado!", exception.getMessage());
    }

    @Test
    public void testUpdate_Sucesso() {
        Agendamento agendamento = criarAgendamentoMock();
        
        // Mockando a busca pelo agendamento existente
        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.of(agendamento));
        
        // Mockando os serviços
        when(servicoRepository.findById(anyLong())).thenReturn(Optional.of(new Servico(1L, "Corte", null, 30.0)));
        
        // Mockando o salvamento
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);

        // Chamando o método e verificando o resultado
        String resultado = agendamentoService.update(agendamento, 1L);
        assertEquals("Agendamento atualizado com sucesso!", resultado);
    }

    @Test
    public void testDelete_AgendamentoNaoEncontrado() {
        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Verificando se a exceção é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            agendamentoService.delete(1L);
        });
        assertEquals("Agendamento não encontrado!", exception.getMessage());
    }

    @Test
    public void testDelete_Sucesso() {
        Agendamento agendamento = criarAgendamentoMock();
        agendamento.setHorariosAgendamento(LocalDateTime.now().plusHours(13));
        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.of(agendamento));

        // Chamando o método e verificando o resultado
        String resultado = agendamentoService.delete(1L);
        assertEquals("Agendamento deletado com sucesso!", resultado);
        verify(agendamentoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDelete_HorarioMenos12Horas() {
        Agendamento agendamento = criarAgendamentoMock();
        agendamento.setHorariosAgendamento(LocalDateTime.now().plusHours(11));
        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.of(agendamento));

        // Verificando se a exceção é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            agendamentoService.delete(1L);
        });
        assertEquals("Erro: Cancelamentos de agendamentos devem ser tratados diretamente com a recepção.", exception.getMessage());
    }

    private Agendamento criarAgendamentoMock() {
        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(new Cliente(1L, "Cliente", "123.456.789-10", "123456789", new ArrayList<>()));
        agendamento.setFuncionario(new Funcionario(1L, null, "Funcionario", "login", "senha", null));
        agendamento.setHorariosAgendamento(LocalDateTime.now().plusHours(25));
        agendamento.setServicos(List.of(new Servico(1L, "Corte", null, 30.0)));
        return agendamento;
    }
}
