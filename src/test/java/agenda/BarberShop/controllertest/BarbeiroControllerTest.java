package agenda.BarberShop.controllertest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import agenda.BarberShop.controller.BarbeiroController;
import agenda.BarberShop.entity.Barbeiro;
import agenda.BarberShop.service.BarbeiroService;

@ExtendWith(MockitoExtension.class)
public class BarbeiroControllerTest {

    @InjectMocks
    private BarbeiroController barbeiroController;

    @Mock
    private BarbeiroService barbeiroService;

    private Barbeiro barbeiro;

    @BeforeEach
    public void setup() {
        barbeiro = new Barbeiro(1L, "João Silva", "123.456.789-00", true, new ArrayList<>());
    }

    // Teste do método save com sucesso
    @Test
    public void test_Save() {
        when(barbeiroService.save(any(Barbeiro.class))).thenReturn("Barbeiro salvo com sucesso!");
        ResponseEntity<String> response = barbeiroController.save(barbeiro);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Barbeiro salvo com sucesso!", response.getBody());
    }

    // Teste da Validation
    @Test
    public void test_ValidationCPF() {
        barbeiro.setCpf("12345678900");
        when(barbeiroService.save(any(Barbeiro.class))).thenThrow(new RuntimeException("CPF inválido"));
        ResponseEntity<String> response = barbeiroController.save(barbeiro);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: CPF inválido", response.getBody());
        verify(barbeiroService, times(1)).save(barbeiro);
    }

    // Teste com Throw
    @Test
    public void test_SaveThrow() {
        when(barbeiroService.save(any(Barbeiro.class))).thenThrow(new RuntimeException("Erro inesperado!"));
        ResponseEntity<String> response = barbeiroController.save(barbeiro);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Erro inesperado!", response.getBody());
    }

    // Teste do método update com sucesso
    @Test
    public void test_UpdateSucesso() {
        when(barbeiroService.update(any(Barbeiro.class), eq(1L))).thenReturn("Barbeiro atualizado com sucesso!");
        ResponseEntity<String> response = barbeiroController.update(barbeiro, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Barbeiro atualizado com sucesso!", response.getBody());
    }

    // Teste do método update com erro
    @Test
    public void test_UpdateErro() {
        when(barbeiroService.update(any(Barbeiro.class), eq(1L))).thenThrow(new RuntimeException("Barbeiro não encontrado!"));
        ResponseEntity<String> response = barbeiroController.update(barbeiro, 1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Barbeiro não encontrado!", response.getBody());
        verify(barbeiroService, times(1)).update(barbeiro, 1L);
    }

    // Teste do método findById com sucesso
    @Test
    public void test_FindByIdSucesso() {
        when(barbeiroService.findById(1L)).thenReturn(barbeiro);
        ResponseEntity<Barbeiro> response = barbeiroController.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(barbeiro, response.getBody());
    }

    // Teste do método findById com erro
    @Test
    public void test_FindByIdErro() {
        when(barbeiroService.findById(1L)).thenThrow(new RuntimeException("Barbeiro não encontrado!"));
        ResponseEntity<Barbeiro> response = barbeiroController.findById(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(barbeiroService, times(1)).findById(1L);
    }

    // Teste do método findAll
    @Test
    public void test_FindAll() {
        List<Barbeiro> barbeiroList = Arrays.asList(barbeiro, new Barbeiro(2L, "Ana Souza", "987.654.321-11", false, null));
        when(barbeiroService.findAll()).thenReturn(barbeiroList);
        ResponseEntity<List<Barbeiro>> response = barbeiroController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(barbeiro, response.getBody().get(0));
    }

    // Teste do método delete com sucesso
    @Test
    public void test_DeleteSucesso() {
        when(barbeiroService.delete(1L)).thenReturn("Barbeiro deletado com sucesso!");
        ResponseEntity<String> response = barbeiroController.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Barbeiro deletado com sucesso!", response.getBody());
        verify(barbeiroService, times(1)).delete(1L);
    }

    // Teste do método delete com erro
    @Test
    public void test_DeleteErro() {
        when(barbeiroService.delete(1L)).thenThrow(new RuntimeException("Barbeiro não encontrado!"));
        ResponseEntity<String> response = barbeiroController.delete(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Barbeiro não encontrado!", response.getBody());
        verify(barbeiroService, times(1)).delete(1L);
    }
}
