package agenda.BarberShop.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import agenda.BarberShop.config.JwtServiceGenerator;

@Service
public class LoginService {

	@Autowired
	private LoginRepository repository;

	@Autowired
	private JwtServiceGenerator jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String logar(Login login) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		Usuario user = repository.findByUsername(login.getUsername()).get();
		String jwtToken = jwtService.generateToken(user);
		return jwtToken;
	}

	public void registrar(Registro registro) throws Exception {
		if (!registro.getPassword().equals(registro.getConfirmarSenha())) {
			throw new Exception("As senhas não coincidem.");
		}

		Optional<Usuario> usuarioExistente = repository.findByUsername(registro.getUsername());
		if (usuarioExistente.isPresent()) {
			throw new Exception("O nome de usuário já está em uso.");
		}

		Usuario novoUsuario = new Usuario();
		novoUsuario.setUsername(registro.getUsername());
		novoUsuario.setPassword(passwordEncoder.encode(registro.getPassword()));
		novoUsuario.setRole("ATENDENTE");

		repository.save(novoUsuario);
	}
}
