package agenda.BarberShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agenda.BarberShop.entity.Barbeiro;
import agenda.BarberShop.service.BarbeiroService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/barbeiro")
@CrossOrigin(origins = "http://localhost:4200")
public class BarbeiroController {

    @Autowired
    private BarbeiroService barbeiroService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Barbeiro>> findAll() {
        List<Barbeiro> barbeiros = barbeiroService.findAll();
        return new ResponseEntity<>(barbeiros, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Barbeiro> findById(@PathVariable long id) {
        Barbeiro barbeiro = barbeiroService.findById(id);
        return new ResponseEntity<>(barbeiro, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Barbeiro barbeiro) {
        try {
            String mensagem = barbeiroService.save(barbeiro);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            // Log da exceção completa para investigar a causa do erro
            e.printStackTrace();
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody Barbeiro barbeiro, @PathVariable long id) {
        try {
            String mensagem = barbeiroService.update(barbeiro, id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            String mensagem = barbeiroService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
