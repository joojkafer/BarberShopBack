package agenda.BarberShop.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import agenda.BarberShop.entity.Agendamento;
import agenda.BarberShop.service.AgendamentoService;
import jakarta.validation.Valid;

@Validated
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody Agendamento agendamento) {
        try {
            String mensagem = agendamentoService.save(agendamento);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody Agendamento agendamento, @PathVariable long id) {
        try {
            String mensagem = agendamentoService.update(agendamento, id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        try {
            Agendamento agendamento = agendamentoService.findById(id);
            return ResponseEntity.ok(agendamento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) LocalDateTime dataInicio,
            @RequestParam(required = false) LocalDateTime dataFim,
            @RequestParam(required = false) Long idBarbeiro,
            @RequestParam(required = false) Long idFuncionario) {
        try {
            List<Agendamento> lista = agendamentoService.findAll(dataInicio, dataFim, idBarbeiro, idFuncionario);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            String mensagem = agendamentoService.delete(id);
            return ResponseEntity.ok(mensagem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
