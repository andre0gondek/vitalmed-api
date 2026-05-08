package distribuidora_api.ui_interface.controller;

import distribuidora_api.domain.entity.Usuario;
import distribuidora_api.infraestructure.security.DadosAutenticacao;
import distribuidora_api.infraestructure.security.DadosTokenJWT;
import distribuidora_api.infraestructure.security.JwtService; // ← troca aqui
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager manager;
    private final JwtService jwtService; // ← era TokenService

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                dados.email(), dados.senha()
        );
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = jwtService.gerarToken((Usuario) authentication.getPrincipal()); // ← era tokenService
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}