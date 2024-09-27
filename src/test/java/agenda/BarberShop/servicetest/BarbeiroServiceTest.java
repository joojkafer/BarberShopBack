package agenda.BarberShop.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import agenda.BarberShop.entity.Barbeiro;
import agenda.BarberShop.repository.BarbeiroRepository;
import agenda.BarberShop.service.BarbeiroService;

public class BarbeiroServiceTest {

    @Mock
    private BarbeiroRepository barbeiroRepository;

    @InjectMocks
    private BarbeiroService barbeiroService;

    private Barbeiro barbeiroMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        barbeiroMock = new Barbeiro();
        barbeiroMock.setIdBarbeiro(1L);
        barbeiroMock.setNome("João");
        barbeiroMock.setCpf("12345678900");
        barbeiroMock.setStatus(true);
        barbeiroMock.setAgendamentos(null);
    }

    @Test
    public void testSave_Sucesso() {
        // Mockando o salvamento do barbeiro
        when(barbeiroRepository.save(any(Barbeiro.class))).thenReturn(barbeiroMock);

        // Chamando o método e verificando o resultado
        String resultado = barbeiroService.save(barbeiroMock);
        assertEquals("Barbeiro salvo com sucesso!", resultado);

        // Verificando se o método save foi chamado
        verify(barbeiroRepository, times(1)).save(barbeiroMock);
    }

    @Test
    public void testUpdate_Sucesso() {
        // Mockando a busca do barbeiro existente
        when(barbeiroRepository.findById(1L)).thenReturn(Optional.of(barbeiroMock));

        // Mockando o salvamento após a atualização
        when(barbeiroRepository.save(any(Barbeiro.class))).thenReturn(barbeiroMock);

        // Chamando o método e verificando o resultado
        String resultado = barbeiroService.update(barbeiroMock, 1L);
        assertEquals("Barbeiro atualizado com sucesso!", resultado);

        // Verificando se o método save foi chamado
        verify(barbeiroRepository, times(1)).save(barbeiroMock);
    }

    @Test
    public void testUpdate_Falha_BarbeiroNaoEncontrado() {
        // Mockando que o barbeiro não foi encontrado
        when(barbeiroRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificando se a exceção é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            barbeiroService.update(barbeiroMock, 1L);
        });
        assertEquals("Barbeiro não encontrado!", exception.getMessage());

        // Verificando que o método save não foi chamado
        verify(barbeiroRepository, times(0)).save(any(Barbeiro.class));
    }

    @Test
    public void testFindById_Sucesso() {
        // Mockando a busca do barbeiro por ID
        when(barbeiroRepository.findById(1L)).thenReturn(Optional.of(barbeiroMock));

        // Chamando o método e verificando o resultado
        Barbeiro resultado = barbeiroService.findById(1L);
        assertEquals(barbeiroMock, resultado);

        // Verificando se o método findById foi chamado
        verify(barbeiroRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_Falha_BarbeiroNaoEncontrado() {
        // Mockando que o barbeiro não foi encontrado
        when(barbeiroRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificando se a exceção é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            barbeiroService.findById(1L);
        });
        assertEquals("Barbeiro não encontrado!", exception.getMessage());

        // Verificando se o método findById foi chamado
        verify(barbeiroRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAll_Sucesso() {
        // Mockando a busca de todos os barbeiros
        Barbeiro barbeiro2 = new Barbeiro();
        barbeiro2.setIdBarbeiro(2L);
        barbeiro2.setNome("Pedro");
        List<Barbeiro> barbeiros = Arrays.asList(barbeiroMock, barbeiro2);

        when(barbeiroRepository.findAll()).thenReturn(barbeiros);

        // Chamando o método e verificando o resultado
        List<Barbeiro> resultado = barbeiroService.findAll();
        assertEquals(barbeiros, resultado);

        // Verificando se o método findAll foi chamado
        verify(barbeiroRepository, times(1)).findAll();
    }

    @Test
    public void testDelete_Sucesso() {
        // Mockando a existência do barbeiro
        when(barbeiroRepository.existsById(1L)).thenReturn(true);

        // Chamando o método e verificando o resultado
        String resultado = barbeiroService.delete(1L);
        assertEquals("Barbeiro deletado com sucesso!", resultado);

        // Verificando se o método deleteById foi chamado
        verify(barbeiroRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDelete_Falha_BarbeiroNaoEncontrado() {
        // Mockando que o barbeiro não foi encontrado
        when(barbeiroRepository.existsById(1L)).thenReturn(false);

        // Verificando se a exceção é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            barbeiroService.delete(1L);
        });
        assertEquals("Barbeiro não encontrado!", exception.getMessage());

        // Verificando que o método deleteById não foi chamado
        verify(barbeiroRepository, times(0)).deleteById(1L);
    }
}
