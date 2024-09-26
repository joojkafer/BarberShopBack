package app.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import app.controller.ClienteController;
import app.entity.Cliente;
import app.service.ClienteService;

@SpringBootTest
public class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente(1L, "João Silva", "123.456.789-00", "(11) 99999-9999", new ArrayList<>());
    }

    // Teste do método save com sucesso
    @Test
    public void test_Save() {
        when(clienteService.save(any(Cliente.class))).thenReturn("Cliente salvo com sucesso!");
        ResponseEntity<String> response = clienteController.save(cliente);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente salvo com sucesso!", response.getBody());
    }

    // Teste da Validation
    @Test
    public void testValidation_CPFInvalido() {
        cliente.setCpf("12345678900");
        ResponseEntity<String> response = clienteController.save(cliente);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Teste com Throw
    @Test
    public void test_SaveThrow() {
        when(clienteService.save(any(Cliente.class))).thenThrow(new RuntimeException("Erro inesperado!"));
        ResponseEntity<String> response = clienteController.save(cliente);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Erro inesperado!", response.getBody());
    }

    // Teste do método update com sucesso
    @Test
    public void test_UpdateSuccesso() {
        when(clienteService.update(any(Cliente.class), anyLong())).thenReturn("Cliente atualizado com sucesso!");
        ResponseEntity<String> response = clienteController.update(cliente, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente atualizado com sucesso!", response.getBody());
    }

    // Teste do método update com erro
    @Test
    public void test_UpdateErro() {
        when(clienteService.update(any(Cliente.class), anyLong())).thenReturn("Cliente não encontrado!");
        ResponseEntity<String> response = clienteController.update(cliente, 1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Cliente não encontrado!", response.getBody());
    }

    // Teste do método findById com sucesso
    @Test
    public void test_FindByIdSuccesso() {
        when(clienteService.findById(1L)).thenReturn(cliente);
        ResponseEntity<Cliente> response = clienteController.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    // Teste do método findById com erro
    @Test
    public void test_FindByIdErro() {
        when(clienteService.findById(1L)).thenReturn(null);
        ResponseEntity<Cliente> response = clienteController.findById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Teste do método findAll
    @Test
    public void test_FindAll() {
        List<Cliente> clienteList = new ArrayList<>();
        clienteList.add(cliente);
        when(clienteService.findAll()).thenReturn(clienteList);
        ResponseEntity<List<Cliente>> response = clienteController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(cliente, response.getBody().get(0));
    }

    // Teste do método delete com sucesso
    @Test
    public void test_DeleteSuccesso() {
        when(clienteService.delete(1L)).thenReturn("Cliente deletado com sucesso!");
        ResponseEntity<String> response = clienteController.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente deletado com sucesso!", response.getBody());
        verify(clienteService, times(1)).delete(1L);
    }

    // Teste do método delete com erro
    @Test
    public void test_DeleteErro() {
        when(clienteService.delete(1L)).thenReturn("Cliente não encontrado!");
        ResponseEntity<String> response = clienteController.delete(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Cliente não encontrado!", response.getBody());
    }
}
