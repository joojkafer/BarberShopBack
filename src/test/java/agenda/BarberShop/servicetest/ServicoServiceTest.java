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
    public void testSave_Sucesso() throws Exception {
        when(servicoRepository.save(any(Servico.class))).thenReturn(servicoMock);
        String resultado = servicoService.save(servicoMock);
        assertEquals("Serviço salvo com sucesso!", resultado);
        verify(servicoRepository, times(1)).save(servicoMock);
    }

    @Test
    public void testSave_Falha_NomeServicoNulo() throws Exception {
        Servico servicoInvalido = new Servico();
        servicoInvalido.setIdServico(2L);
        servicoInvalido.setNome(null);
        servicoInvalido.setValor(30.0);

        Exception exception = assertThrows(Exception.class, () -> {
            servicoService.save(servicoInvalido);
        });
        assertEquals("O nome do serviço não pode ser nulo.", exception.getMessage());

        verify(servicoRepository, times(0)).save(any(Servico.class));
    }

    @Test
    public void testUpdate_Sucesso() throws Exception {
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servicoMock));
        when(servicoRepository.save(any(Servico.class))).thenReturn(servicoMock);
        String resultado = servicoService.update(servicoMock, 1L);
        assertEquals("Serviço atualizado com sucesso!", resultado);
        verify(servicoRepository, times(1)).save(servicoMock);
    }

    @Test
    public void testUpdate_Falha_ServicoNaoEncontrado() throws Exception {
        when(servicoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            servicoService.update(servicoMock, 1L);
        });
        assertEquals("Serviço não encontrado!", exception.getMessage());

        verify(servicoRepository, times(0)).save(any(Servico.class));
    }

    @Test
    public void testFindById_Sucesso() throws Exception {
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servicoMock));
        Servico resultado = servicoService.findById(1L);
        assertEquals(servicoMock, resultado);
        verify(servicoRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_Falha_ServicoNaoEncontrado() throws Exception {
        when(servicoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            servicoService.findById(1L);
        });
        assertEquals("Serviço não encontrado!", exception.getMessage());

        verify(servicoRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAll_Sucesso() throws Exception {
        Servico servico2 = new Servico();
        servico2.setIdServico(2L);
        servico2.setNome("Barba");
        servico2.setValor(30.0);
        List<Servico> servicos = Arrays.asList(servicoMock, servico2);
        when(servicoRepository.findAll()).thenReturn(servicos);
        List<Servico> resultado = servicoService.findAll();
        assertEquals(servicos, resultado);
        verify(servicoRepository, times(1)).findAll();
    }

    @Test
    public void testDelete_Sucesso() throws Exception {
        when(servicoRepository.existsById(1L)).thenReturn(true);

        String resultado = servicoService.delete(1L);
        assertEquals("Serviço deletado com sucesso!", resultado);

        verify(servicoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDelete_Falha_ServicoNaoEncontrado() throws Exception {
        when(servicoRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(Exception.class, () -> {
            servicoService.delete(1L);
        });
        assertEquals("Serviço não encontrado!", exception.getMessage());

        verify(servicoRepository, times(0)).deleteById(1L);
    }
}
