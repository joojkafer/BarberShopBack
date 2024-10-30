package agenda.BarberShop.controllertest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import agenda.BarberShop.controller.AgendamentoController;
import agenda.BarberShop.entity.Agendamento;
import agenda.BarberShop.entity.Barbeiro;
import agenda.BarberShop.entity.Cliente;
import agenda.BarberShop.entity.Funcionario;
import agenda.BarberShop.entity.Servico;
import agenda.BarberShop.service.AgendamentoService;
import agenda.BarberShop.entity.Role;

@ExtendWith(MockitoExtension.class)
public class AgendamentoControllerTest {

    @InjectMocks
    private AgendamentoController agendamentoController;

    @Mock
    private AgendamentoService agendamentoService;

    private Agendamento agendamento;
    private Cliente cliente;
    private Barbeiro barbeiro;
    private Funcionario funcionario;
    private Servico servico;

    @BeforeEach
    public void setup() {
        cliente = new Cliente(1L, "Cliente Teste", "123.456.789-10", "(11) 91234-5678", new ArrayList<>());
        funcionario = new Funcionario(1L, Role.ATENDENTE, "Funcionario Teste", "funcionario", "senha123", new ArrayList<>());
        servico = new Servico(1L, "Corte de Cabelo", "Corte padrão", 50.0);

        agendamento = new Agendamento();
        agendamento.setIdAgendamento(1L);
        agendamento.setHorariosAgendamento(LocalDateTime.now().plusDays(2));
        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setFuncionario(funcionario);
        agendamento.setServicos(Arrays.asList(servico));
        agendamento.setValorTotal(50.0);
    }

    // Teste do método save com sucesso
    @Test
    public void test_Save() {
        when(agendamentoService.save(any(Agendamento.class))).thenReturn("Agendamento salvo com sucesso!");
        ResponseEntity<String> response = agendamentoController.save(agendamento);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Agendamento salvo com sucesso!", response.getBody());
    }

    // Teste da Validation do CPF do Cliente
    @Test
    public void test_ValidationCPFCliente() {
        cliente.setCpf("12345678900");
        when(agendamentoService.save(any(Agendamento.class))).thenThrow(new IllegalArgumentException("O CPF deve seguir o padrão XXX.XXX.XXX-XX"));
        ResponseEntity<String> response = agendamentoController.save(agendamento);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: O CPF deve seguir o padrão XXX.XXX.XXX-XX", response.getBody());
    }

    // Teste da Validation do Telefone do Cliente
    @Test
    public void test_ValidationTelefoneCliente() {
        cliente.setTelefone("11912345678"); 
        when(agendamentoService.save(any(Agendamento.class))).thenThrow(new IllegalArgumentException("O telefone deve seguir o padrão: (XX) XXXX-XXXX ou (XX) XXXXX-XXXX."));
        ResponseEntity<String> response = agendamentoController.save(agendamento);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: O telefone deve seguir o padrão: (XX) XXXX-XXXX ou (XX) XXXXX-XXXX.", response.getBody());
    }

    // Teste com Throw no método save
    @Test
    public void test_SaveThrow() {
        when(agendamentoService.save(any(Agendamento.class))).thenThrow(new RuntimeException("Erro inesperado!"));
        ResponseEntity<String> response = agendamentoController.save(agendamento);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Erro inesperado!", response.getBody());
    }

    // Teste do método save com horário inválido (menos de 24 horas)
    @Test
    public void test_SaveHorarioInvalido() {
        agendamento.setHorariosAgendamento(LocalDateTime.now().plusHours(12));
        when(agendamentoService.save(any(Agendamento.class))).thenThrow(new IllegalArgumentException("Agendamentos devem ser feitos com pelo menos 24 horas de antecedência."));
        ResponseEntity<String> response = agendamentoController.save(agendamento);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Agendamentos devem ser feitos com pelo menos 24 horas de antecedência.", response.getBody());
    }

    // Teste do método save com conflito de horário
    @Test
    public void test_SaveConflitoHorario() {
        when(agendamentoService.save(any(Agendamento.class))).thenThrow(new IllegalStateException("Já existe um agendamento para este barbeiro neste horário."));
        ResponseEntity<String> response = agendamentoController.save(agendamento);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Já existe um agendamento para este barbeiro neste horário.", response.getBody());
    }

    // Teste do método save com Servico valor nulo
    @Test
    public void test_SaveServicoValorNulo() {
        Servico servicoInvalido = new Servico(1L, "Corte de Cabelo", "Corte padrão", null);
        agendamento.setServicos(Arrays.asList(servicoInvalido));
        when(agendamentoService.save(any(Agendamento.class))).thenThrow(new IllegalArgumentException("O valor do serviço não pode ser nulo."));
        ResponseEntity<String> response = agendamentoController.save(agendamento);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: O valor do serviço não pode ser nulo.", response.getBody());
    }

    // Teste do método update com sucesso
    @Test
    public void test_UpdateSucesso() {
        when(agendamentoService.update(any(Agendamento.class), anyLong())).thenReturn("Agendamento atualizado com sucesso!");
        ResponseEntity<String> response = agendamentoController.update(agendamento, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Agendamento atualizado com sucesso!", response.getBody());
    }

    // Teste do método update com erro (agendamento não encontrado)
    @Test
    public void test_UpdateErro() {
        when(agendamentoService.update(any(Agendamento.class), anyLong())).thenThrow(new IllegalArgumentException("Agendamento não encontrado!"));
        ResponseEntity<String> response = agendamentoController.update(agendamento, 1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Agendamento não encontrado!", response.getBody());
    }

    // Teste do método update com Throw
    @Test
    public void test_UpdateThrow() {
        when(agendamentoService.update(any(Agendamento.class), anyLong())).thenThrow(new RuntimeException("Erro inesperado na atualização!"));
        ResponseEntity<String> response = agendamentoController.update(agendamento, 1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Erro inesperado na atualização!", response.getBody());
    }

    // Teste do método update com validação inválida (nome do Cliente)
    @Test
    public void test_UpdateValidationNomeClienteInvalido() {
        cliente.setNome("Cliente");
        when(agendamentoService.update(any(Agendamento.class), anyLong())).thenThrow(new IllegalArgumentException("O nome deve conter pelo menos duas palavras."));
        ResponseEntity<String> response = agendamentoController.update(agendamento, 1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: O nome deve conter pelo menos duas palavras.", response.getBody());
    }

    // Teste do método findById com sucesso
    @Test
    public void test_FindByIdSucesso() {
        when(agendamentoService.findById(1L)).thenReturn(agendamento);
        ResponseEntity<?> response = agendamentoController.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agendamento, response.getBody());
    }

    // Teste do método findById com erro (agendamento não encontrado)
    @Test
    public void test_FindByIdErro() {
        when(agendamentoService.findById(1L)).thenThrow(new RuntimeException("Agendamento não encontrado!"));
        ResponseEntity<?> response = agendamentoController.findById(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Agendamento não encontrado!", response.getBody());
    }

    // Teste do método findById com Throw
    @Test
    public void test_FindByIdThrow() {
        when(agendamentoService.findById(1L)).thenThrow(new RuntimeException("Erro inesperado no findById!"));
        ResponseEntity<?> response = agendamentoController.findById(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Erro inesperado no findById!", response.getBody());
    }

    // Teste do método findAll com resultados
    @Test
    public void test_FindAllSucesso() {
        List<Agendamento> agendamentoList = Arrays.asList(agendamento);
        when(agendamentoService.findAll(any(LocalDateTime.class), any(LocalDateTime.class), anyLong(), anyLong())).thenReturn(agendamentoList);
        ResponseEntity<?> response = agendamentoController.findAll(LocalDateTime.now(), LocalDateTime.now().plusDays(3), 1L, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, ((List<?>) response.getBody()).size());
        assertEquals(agendamento, ((List<Agendamento>) response.getBody()).get(0));
    }

    // Teste do método findAll com lista vazia
    @Test
    public void test_FindAllVazia() {
        when(agendamentoService.findAll(any(LocalDateTime.class), any(LocalDateTime.class), anyLong(), anyLong())).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = agendamentoController.findAll(LocalDateTime.now(), LocalDateTime.now().plusDays(3), 1L, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((List<?>) response.getBody()).isEmpty());
    }

    // Teste do método findAll com Throw
    @Test
    public void test_FindAllThrow() {
        when(agendamentoService.findAll(any(LocalDateTime.class), any(LocalDateTime.class), anyLong(), anyLong())).thenThrow(new RuntimeException("Erro inesperado no findAll!"));
        ResponseEntity<?> response = agendamentoController.findAll(LocalDateTime.now(), LocalDateTime.now().plusDays(3), 1L, 1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Erro inesperado no findAll!", response.getBody());
    }

    // Teste do método delete com sucesso
    @Test
    public void test_DeleteSucesso() {
        when(agendamentoService.delete(1L)).thenReturn("Agendamento deletado com sucesso!");
        ResponseEntity<String> response = agendamentoController.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Agendamento deletado com sucesso!", response.getBody());
        verify(agendamentoService, times(1)).delete(1L);
    }

    // Teste do método delete com erro (agendamento não encontrado)
    @Test
    public void test_DeleteErro() {
        when(agendamentoService.delete(1L)).thenThrow(new IllegalArgumentException("Agendamento não encontrado!"));
        ResponseEntity<String> response = agendamentoController.delete(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Agendamento não encontrado!", response.getBody());
    }

    // Teste do método delete com erro de cancelamento próximo ao horário
    @Test
    public void test_DeleteCancelamentoProximoHorario() {
        when(agendamentoService.delete(1L)).thenThrow(new IllegalStateException("Cancelamentos de agendamentos devem ser tratados diretamente com a recepção."));
        ResponseEntity<String> response = agendamentoController.delete(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Cancelamentos de agendamentos devem ser tratados diretamente com a recepção.", response.getBody());
    }

    // Teste do método delete com Throw genérico
    @Test
    public void test_DeleteThrow() {
        when(agendamentoService.delete(1L)).thenThrow(new RuntimeException("Erro inesperado na deleção!"));
        ResponseEntity<String> response = agendamentoController.delete(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Erro inesperado na deleção!", response.getBody());
    }
}
