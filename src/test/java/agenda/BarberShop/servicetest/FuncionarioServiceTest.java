package agenda.BarberShop.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import agenda.BarberShop.entity.Funcionario;
import agenda.BarberShop.repository.FuncionarioRepository;
import agenda.BarberShop.service.FuncionarioService;

public class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;

    private Funcionario funcionarioMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        funcionarioMock = new Funcionario();
        funcionarioMock.setIdUsuario(1L);
        funcionarioMock.setNome("João");
        funcionarioMock.setLogin("joao123");
    }

    @Test
    public void testSave_Sucesso() {
        // Mockando o salvamento do funcionário
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionarioMock);

        // Chamando o método e verificando o resultado
        String resultado = funcionarioService.save(funcionarioMock);
        assertEquals("Funcionário salvo com sucesso!", resultado);

        // Verificando se o método save foi chamado
        verify(funcionarioRepository, times(1)).save(funcionarioMock);
    }

    @Test
    public void testUpdate_Sucesso() {
        // Mockando a busca do funcionário existente
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionarioMock));

        // Mockando o salvamento após a atualização
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionarioMock);

        // Chamando o método e verificando o resultado
        String resultado = funcionarioService.update(funcionarioMock, 1L);
        assertEquals("Funcionário atualizado com sucesso!", resultado);

        // Verificando se o método save foi chamado
        verify(funcionarioRepository, times(1)).save(funcionarioMock);
    }

    @Test
    public void testUpdate_Falha_FuncionarioNaoEncontrado() {
        // Mockando que o funcionário não foi encontrado
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificando se a exceção é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.update(funcionarioMock, 1L);
        });
        assertEquals("Funcionário não encontrado!", exception.getMessage());

        // Verificando que o método save não foi chamado
        verify(funcionarioRepository, times(0)).save(any(Funcionario.class));
    }

    @Test
    public void testFindById_Sucesso() {
        // Mockando a busca do funcionário por ID
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionarioMock));

        // Chamando o método e verificando o resultado
        Funcionario resultado = funcionarioService.findById(1L);
        assertEquals(funcionarioMock, resultado);

        // Verificando se o método findById foi chamado
        verify(funcionarioRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_Falha_FuncionarioNaoEncontrado() {
        // Mockando que o funcionário não foi encontrado
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificando se a exceção é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.findById(1L);
        });
        assertEquals("Funcionário não encontrado!", exception.getMessage());

        // Verificando se o método findById foi chamado
        verify(funcionarioRepository, times(1)).findById(1L);
    }

    @Test
    public void testDelete_Sucesso() {
        // Mockando a existência do funcionário
        when(funcionarioRepository.existsById(1L)).thenReturn(true);

        // Chamando o método e verificando o resultado
        String resultado = funcionarioService.delete(1L);
        assertEquals("Funcionário deletado com sucesso!", resultado);

        // Verificando se o método deleteById foi chamado
        verify(funcionarioRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDelete_Falha_FuncionarioNaoEncontrado() {
        // Mockando que o funcionário não foi encontrado
        when(funcionarioRepository.existsById(1L)).thenReturn(false);

        // Verificando se a exceção é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.delete(1L);
        });
        assertEquals("Funcionário não encontrado!", exception.getMessage());

        // Verificando que o método deleteById não foi chamado
        verify(funcionarioRepository, times(0)).deleteById(1L);
    }
}
