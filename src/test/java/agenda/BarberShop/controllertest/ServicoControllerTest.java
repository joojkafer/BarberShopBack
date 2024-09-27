package agenda.BarberShop.controllertest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import agenda.BarberShop.controller.ServicoController;
import agenda.BarberShop.entity.Servico;
import agenda.BarberShop.service.ServicoService;

public class ServicoControllerTest {

    @InjectMocks
    private ServicoController servicoController;

    @Mock
    private ServicoService servicoService;

    private Servico servico;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servico = new Servico(1L, "Corte de Cabelo", "Corte de cabelo padrão", 50.0);
    }

    // Teste do método save com sucesso
    @Test
    void test_SaveSucesso() throws Exception {
        when(servicoService.save(any(Servico.class))).thenReturn("Serviço salvo com sucesso!");
        ResponseEntity<String> response = servicoController.save(servico);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Serviço salvo com sucesso!", response.getBody());
        verify(servicoService, times(1)).save(any(Servico.class));
    }

    // Teste do método save com erro de validação
    @Test
    void test_SaveErroValidacao() throws Exception {
        Servico servicoInvalido = new Servico(1L, null, "Descrição", 30.0);
        when(servicoService.save(servicoInvalido)).thenThrow(new RuntimeException("O nome do serviço não pode ser nulo."));
        
        ResponseEntity<String> response = servicoController.save(servicoInvalido);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: O nome do serviço não pode ser nulo.", response.getBody());
        verify(servicoService, times(1)).save(servicoInvalido);
    }

    // Teste do método update com sucesso
    @Test
    void test_UpdateSucesso() throws Exception {
        when(servicoService.update(any(Servico.class), eq(1L))).thenReturn("Serviço atualizado com sucesso!");
        ResponseEntity<String> response = servicoController.update(servico, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Serviço atualizado com sucesso!", response.getBody());
        verify(servicoService, times(1)).update(any(Servico.class), eq(1L));
    }

    // Teste do método update com erro
    @Test
    void test_UpdateErro() throws Exception {
        when(servicoService.update(any(Servico.class), eq(1L)))
            .thenThrow(new RuntimeException("Serviço não encontrado!"));

        ResponseEntity<String> response = servicoController.update(servico, 1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Serviço não encontrado!", response.getBody());
        verify(servicoService, times(1)).update(any(Servico.class), eq(1L));
    }

    // Teste do método findById com sucesso
    @Test
    void test_FindByIdSucesso() throws Exception {
        when(servicoService.findById(1L)).thenReturn(servico);
        ResponseEntity<Servico> response = servicoController.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Corte de Cabelo", response.getBody().getNome());
        verify(servicoService, times(1)).findById(1L);
    }

    // Teste do método findById com erro
    @Test
    void test_FindByIdErro() throws Exception {
        when(servicoService.findById(1L)).thenThrow(new RuntimeException("Serviço não encontrado!"));
        ResponseEntity<Servico> response = servicoController.findById(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(servicoService, times(1)).findById(1L);
    }

    // Teste do método findAll
    @Test
    void test_FindAll() throws Exception {
        List<Servico> lista = Arrays.asList(servico, new Servico(2L, "Barba", "Serviço de barbearia", 30.0));
        when(servicoService.findAll()).thenReturn(lista);
        ResponseEntity<List<Servico>> response = servicoController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(servicoService, times(1)).findAll();
    }

    // Teste do método delete com sucesso
    @Test
    void test_DeleteSucesso() throws Exception {
        when(servicoService.delete(1L)).thenReturn("Serviço deletado com sucesso!");
        ResponseEntity<String> response = servicoController.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Serviço deletado com sucesso!", response.getBody());
        verify(servicoService, times(1)).delete(1L);
    }

    // Teste do método delete com erro
    @Test
    void test_DeleteErro() throws Exception {
        when(servicoService.delete(1L)).thenThrow(new RuntimeException("Serviço não encontrado!"));
        ResponseEntity<String> response = servicoController.delete(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(servicoService, times(1)).delete(1L);
    }
}
