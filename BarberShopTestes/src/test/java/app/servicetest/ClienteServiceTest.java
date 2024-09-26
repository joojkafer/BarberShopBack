package app.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import app.entity.Cliente;
import app.repository.ClienteRepository;
import app.service.ClienteService;

@SpringBootTest
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente clienteMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteMock = new Cliente();
        clienteMock.setIdCliente(1L);
        clienteMock.setNome("Carlos");
        clienteMock.setCpf("12345678900");
        clienteMock.setTelefone("123456789");
    }

    @Test
    public void testSave_Sucesso() {
        // Mockando o salvamento do cliente
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);

        // Chamando o método e verificando o resultado
        String resultado = clienteService.save(clienteMock);
        assertEquals("Cliente salvo com sucesso!", resultado);

        // Verificando se o método save foi chamado
        verify(clienteRepository, times(1)).save(clienteMock);
    }

    @Test
    public void testUpdate_Sucesso() {
        // Mockando a busca do cliente existente
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));

        // Mockando o salvamento após a atualização
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);

        // Chamando o método e verificando o resultado
        String resultado = clienteService.update(clienteMock, 1L);
        assertEquals("Cliente atualizado com sucesso!", resultado);

        // Verificando se o método save foi chamado
        verify(clienteRepository, times(1)).save(clienteMock);
    }

    @Test
    public void testUpdate_Falha_ClienteNaoEncontrado() {
        // Mockando que o cliente não foi encontrado
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Chamando o método e verificando o resultado
        String resultado = clienteService.update(clienteMock, 1L);
        assertEquals("Cliente não encontrado!", resultado);

        // Verificando que o método save não foi chamado
        verify(clienteRepository, times(0)).save(any(Cliente.class));
    }

    @Test
    public void testFindById_Sucesso() {
        // Mockando a busca do cliente por ID
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));

        // Chamando o método e verificando o resultado
        Cliente resultado = clienteService.findById(1L);
        assertEquals(clienteMock, resultado);

        // Verificando se o método findById foi chamado
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_Falha_ClienteNaoEncontrado() {
        // Mockando que o cliente não foi encontrado
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Chamando o método e verificando o resultado
        Cliente resultado = clienteService.findById(1L);
        assertEquals(null, resultado);

        // Verificando se o método findById foi chamado
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAll_Sucesso() {
        // Mockando a busca de todos os clientes
        Cliente cliente2 = new Cliente();
        cliente2.setIdCliente(2L);
        cliente2.setNome("Pedro");
        List<Cliente> clientes = Arrays.asList(clienteMock, cliente2);

        when(clienteRepository.findAll()).thenReturn(clientes);

        // Chamando o método e verificando o resultado
        List<Cliente> resultado = clienteService.findAll();
        assertEquals(clientes, resultado);

        // Verificando se o método findAll foi chamado
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    public void testDelete_Sucesso() {
        // Mockando a existência do cliente
        when(clienteRepository.existsById(1L)).thenReturn(true);

        // Chamando o método e verificando o resultado
        String resultado = clienteService.delete(1L);
        assertEquals("Cliente deletado com sucesso!", resultado);

        // Verificando se o método deleteById foi chamado
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDelete_Falha_ClienteNaoEncontrado() {
        // Mockando que o cliente não foi encontrado
        when(clienteRepository.existsById(1L)).thenReturn(false);

        // Chamando o método e verificando o resultado
        String resultado = clienteService.delete(1L);
        assertEquals("Cliente não encontrado!", resultado);

        // Verificando que o método deleteById não foi chamado
        verify(clienteRepository, times(0)).deleteById(1L);
    }
}
