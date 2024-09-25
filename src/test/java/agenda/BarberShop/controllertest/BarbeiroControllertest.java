package agenda.BarberShop.controllertest;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

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

import agenda.BarberShop.controller.BarbeiroController;
import agenda.BarberShop.entity.Barbeiro;
import agenda.BarberShop.service.BarbeiroService;

public class BarbeiroControllertest {

    @InjectMocks
    private BarbeiroController barbeiroController;

    @Mock
    private BarbeiroService barbeiroService;

    private Barbeiro barbeiro;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        barbeiro = new Barbeiro(1L, "João Silva", "123.456.789-00", true, new ArrayList<>());
    }
    
    @Test
    void testSomething() {
        // Seu código de teste aqui
        assertTrue(true);
    }
    
    //Teste do método save com sucesso
    @Test
    public void test_Save() {
        when(barbeiroService.save(any(Barbeiro.class))).thenReturn("Barbeiro salvo com sucesso!");
        ResponseEntity<String> response = barbeiroController.save(barbeiro);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Barbeiro salvo com sucesso!", response.getBody());
    }
    
    //Teste da Validation
    @Test
    public void test_ValidationCPF() {
        barbeiro.setCpf("12345678900");
        ResponseEntity<String> response = barbeiroController.save(barbeiro);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    
    //Teste com Throw
    @Test
    public void test_SaveThrow() {
        when(barbeiroService.save(any(Barbeiro.class))).thenThrow(new RuntimeException("Erro inesperado!"));
        
        ResponseEntity<String> response = barbeiroController.save(barbeiro);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Erro inesperado!", response.getBody());
    }
    
    //Teste do método update com sucesso
    @Test
    public void test_UpdateSuccesso() {
        when(barbeiroService.update(any(Barbeiro.class), anyLong())).thenReturn("Barbeiro atualizado com sucesso!");
        ResponseEntity<String> response = barbeiroController.update(barbeiro, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Barbeiro atualizado com sucesso!", response.getBody());
    }
    
    //Teste do método update com erro
    @Test
    public void test_UpdateErro() {
        when(barbeiroService.update(any(Barbeiro.class), anyLong())).thenReturn("Barbeiro não encontrado!");
        ResponseEntity<String> response = barbeiroController.update(barbeiro, 1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Barbeiro não encontrado!", response.getBody());
    }
    
    //Teste do método update com sucesso
    @Test
    public void test_FindByIdSuccesso() {
        when(barbeiroService.findById(1L)).thenReturn(barbeiro);
        ResponseEntity<Barbeiro> response = barbeiroController.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(barbeiro, response.getBody());
    }
    
    //Teste do método update com erro
    @Test
    public void test_FindByIdErro() {
        when(barbeiroService.findById(1L)).thenReturn(null);
        ResponseEntity<Barbeiro> response = barbeiroController.findById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    //Teste do método findall
    @Test
    public void test_FindAll() {
        List<Barbeiro> barbeiroList = new ArrayList<>();
        barbeiroList.add(barbeiro);
        when(barbeiroService.findAll()).thenReturn(barbeiroList);
        ResponseEntity<List<Barbeiro>> response = barbeiroController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(barbeiro, response.getBody().get(0));
    }
    
    //Teste do método delete com sucesso
    @Test
    public void test_DeleteSuccesso() {
        when(barbeiroService.delete(1L)).thenReturn("Barbeiro deletado com sucesso!");
        ResponseEntity<String> response = barbeiroController.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Barbeiro deletado com sucesso!", response.getBody());
        verify(barbeiroService, times(1)).delete(1L);
    }
    
    //Teste do método delete com erro
    @Test
    public void test_DeleteErro() {
        when(barbeiroService.delete(1L)).thenReturn("Barbeiro não encontrado!");
        ResponseEntity<String> response = barbeiroController.delete(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Barbeiro não encontrado!", response.getBody());
    }
}
