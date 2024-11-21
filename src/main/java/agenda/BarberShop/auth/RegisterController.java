package agenda.BarberShop.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
@CrossOrigin(origins = "*")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody Registro registro) {
        try {
            loginService.registrar(registro);
            return new ResponseEntity<>("Usuário registrado com sucesso.", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Erro no registro: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
