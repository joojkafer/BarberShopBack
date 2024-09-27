package agenda.BarberShop.servicetest;

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

import agenda.BarberShop.entity.Servico;
import agenda.BarberShop.repository.ServicoRepository;
import agenda.BarberShop.service.ServicoService;

public class ServicoServiceTest {

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private ServicoService servicoService;

    private Servico servicoMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        servicoMock = new Servico();
        servicoMock.setIdServico(1L);
        servicoMock.setNome("Corte de Cabelo");
        servicoMock.setValor(50.0);
    }

    @Test
    public void testSave_Sucesso() {
        // Mockando o salvamento do serviço
        when(servicoRepository.save(any(Servico.class))).thenReturn(servicoMock);

        // Chamando o método e verificando o resultado
        String resultado = servicoService.save(servicoMock);
        assertEquals("Serviço salvo com sucesso!", resultado);

        // Verificando se o método save foi chamado
        verify(servicoRepository, times(1)).save(servicoMock);
    }

    @Test
    public void testUpdate_Sucesso() {
        // Mockando a busca do serviço existente
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servicoMock));

        // Mockando o salvamento após a atualização
        when(servicoRepository.save(any(Servico.class))).thenReturn(servicoMock);

        // Chamando o método e verificando o resultado
        String resultado = servicoService.update(servicoMock, 1L);
        assertEquals("Serviço atualizado com sucesso!", resultado);

        // Verificando se o método save foi chamado
        verify(servicoRepository, times(1)).save(servicoMock);
    }

    @Test
    public void testUpdate_Falha_ServicoNaoEncontrado() {
        // Mockando que o serviço não foi encontrado
        when(servicoRepository.findById(1L)).thenReturn(Optional.empty());

        // Chamando o método e verificando o resultado
        String resultado = servicoService.update(servicoMock, 1L);
        assertEquals("Serviço não encontrado!", resultado);

        // Verificando que o método save não foi chamado
        verify(servicoRepository, times(0)).save(any(Servico.class));
    }

    @Test
    public void testFindById_Sucesso() {
        // Mockando a busca do serviço por ID
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servicoMock));

        // Chamando o método e verificando o resultado
        Servico resultado = servicoService.findById(1L);
        assertEquals(servicoMock, resultado);

        // Verificando se o método findById foi chamado
        verify(servicoRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_Falha_ServicoNaoEncontrado() {
        // Mockando que o serviço não foi encontrado
        when(servicoRepository.findById(1L)).thenReturn(Optional.empty());

        // Chamando o método e verificando o resultado
        Servico resultado = servicoService.findById(1L);
        assertEquals(null, resultado);

        // Verificando se o método findById foi chamado
        verify(servicoRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAll_Sucesso() {
        // Mockando a busca de todos os serviços
        Servico servico2 = new Servico();
        servico2.setIdServico(2L);
        servico2.setNome("Barba");
        servico2.setValor(30.0);
        List<Servico> servicos = Arrays.asList(servicoMock, servico2);

        when(servicoRepository.findAll()).thenReturn(servicos);

        // Chamando o método e verificando o resultado
        List<Servico> resultado = servicoService.findAll();
        assertEquals(servicos, resultado);

        // Verificando se o método findAll foi chamado
        verify(servicoRepository, times(1)).findAll();
    }

    @Test
    public void testDelete_Sucesso() {
        // Mockando a existência do serviço
        when(servicoRepository.existsById(1L)).thenReturn(true);

        // Chamando o método e verificando o resultado
        String resultado = servicoService.delete(1L);
        assertEquals("Serviço deletado com sucesso!", resultado);

        // Verificando se o método deleteById foi chamado
        verify(servicoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDelete_Falha_ServicoNaoEncontrado() {
        // Mockando que o serviço não foi encontrado
        when(servicoRepository.existsById(1L)).thenReturn(false);

        // Chamando o método e verificando o resultado
        String resultado = servicoService.delete(1L);
        assertEquals("Serviço não encontrado!", resultado);

        // Verificando que o método deleteById não foi chamado
        verify(servicoRepository, times(0)).deleteById(1L);
    }
}
