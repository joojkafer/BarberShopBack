package app.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import app.controller.FuncionarioController;
import app.entity.Funcionario;
import app.entity.Role;
import app.service.FuncionarioService;

@SpringBootTest
public class FuncionarioControllerTest {

    @InjectMocks
    private FuncionarioController funcionarioController;

    @Mock
    private FuncionarioService funcionarioService;

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        funcionario = new Funcionario(1L, Role.ATENDENTE, "Pedro Souza", "pedro", "senha123", null);
    }
    
    // Teste do método save com sucesso (Cenário OK)
    @Test
    void test_SaveSucesso() {
        when(funcionarioService.save(any(Funcionario.class))).thenReturn("Funcionário salvo com sucesso!");
        ResponseEntity<String> response = funcionarioController.save(funcionario);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário salvo com sucesso!", response.getBody());
        verify(funcionarioService, times(1)).save(any(Funcionario.class));
    }

    // Teste do método save com erro
    @Test
    void test_SaveThrow() {
        when(funcionarioService.save(any(Funcionario.class))).thenThrow(new RuntimeException("Erro inesperado!"));
        ResponseEntity<String> response = funcionarioController.save(funcionario);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Erro inesperado!", response.getBody());
        verify(funcionarioService, times(1)).save(any(Funcionario.class));
    }

    // Cenário de validação
    @Test
    void testValidationErro() {
        funcionario.setNome("");
        ResponseEntity<String> response = funcionarioController.save(funcionario);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    // Teste do método update com sucesso
    @Test
    void test_UpdateSucesso() {
        when(funcionarioService.update(any(Funcionario.class), eq(1L))).thenReturn("Funcionário atualizado com sucesso!");
        ResponseEntity<String> response = funcionarioController.update(funcionario, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário atualizado com sucesso!", response.getBody());
        verify(funcionarioService, times(1)).update(any(Funcionario.class), eq(1L));
    }

    // Teste do método update com erro
    void test_UpdateErro() {
        when(funcionarioService.update(any(Funcionario.class), eq(1L))).thenThrow(new RuntimeException("Funcionário não encontrado!"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioController.update(funcionario, 1L);
        });

        assertEquals("Funcionário não encontrado!", exception.getMessage());
        verify(funcionarioService, times(1)).update(any(Funcionario.class), eq(1L));
    }

    // Teste do método findById com sucesso
    @Test
    void test_FindByIdSucesso() {
        when(funcionarioService.findById(1L)).thenReturn(funcionario);
        ResponseEntity<Funcionario> response = funcionarioController.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Pedro Souza", response.getBody().getNome());
        verify(funcionarioService, times(1)).findById(1L);
    }

    // Teste do método findById com erro
    @Test
    void test_FindByIdErro() {
        when(funcionarioService.findById(1L)).thenThrow(new RuntimeException("Funcionário não encontrado!"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioController.findById(1L);
        });

        assertEquals("Funcionário não encontrado!", exception.getMessage());
        verify(funcionarioService, times(1)).findById(1L);
    }

    // Teste do método findAll
    @Test
    void test_FindAll() {
        List<Funcionario> lista = Arrays.asList(funcionario, new Funcionario(2L, Role.ADM, "Ana Souza", "ana", "senha456", null));
        when(funcionarioService.findAll()).thenReturn(lista);

        ResponseEntity<List<Funcionario>> response = funcionarioController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(funcionarioService, times(1)).findAll();
    }

    // Teste do método delete com sucesso
    @Test
    void test_DeleteSucesso() {
        when(funcionarioService.delete(1L)).thenReturn("Funcionário deletado com sucesso!");
        ResponseEntity<String> response = funcionarioController.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário deletado com sucesso!", response.getBody());
        verify(funcionarioService, times(1)).delete(1L);
    }

    // Teste do método delete com erro
    @Test
    void test_DeleteErro() {
        when(funcionarioService.delete(1L)).thenThrow(new RuntimeException("Funcionário não encontrado!"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioController.delete(1L);
        });

        assertEquals("Funcionário não encontrado!", exception.getMessage());
        verify(funcionarioService, times(1)).delete(1L);
    }
}
