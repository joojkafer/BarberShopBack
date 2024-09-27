package agenda.BarberShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

@Validated
@RestController
@RequestMapping("/barbeiro")
public class BarbeiroController {
	
	@Autowired
    private BarbeiroService barbeiroService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Barbeiro barbeiro) {
        try {
            String mensagem = this.barbeiroService.save(barbeiro);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Barbeiro barbeiro, @PathVariable long id) {
        try {
            String mensagem = this.barbeiroService.update(barbeiro, id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Barbeiro> findById(@PathVariable long id) {
        try {
        	Barbeiro barbeiro = this.barbeiroService.findById(id);
            return new ResponseEntity<>(barbeiro, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Barbeiro>> findAll() {
        try {
            List<Barbeiro> lista = this.barbeiroService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String message = barbeiroService.delete(id);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
}
