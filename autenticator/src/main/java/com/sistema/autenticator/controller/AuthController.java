package com.sistema.autenticator.controller;

import com.sistema.autenticator.model.AuthRequest;
import com.sistema.autenticator.model.AuthResponse;
import com.sistema.autenticator.model.UsuarioResponse;
import com.sistema.autenticator.security.JwtUtil;
import com.sistema.autenticator.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        if (authRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Senha é obrigatória");
        }

        UsuarioResponse usuario = authService.obterUsuarioComSenhaEPerfil(authRequest.getUsername());

        if (usuario == null || usuario.getSenha() == null ||
                !authService.getPasswordEncoder().matches(authRequest.getPassword(), usuario.getSenha())) {
            return ResponseEntity.status(401).body("{\"error\": \"Credenciais inválidas\"}");
        }

        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRoles());
        return ResponseEntity.ok(new AuthResponse(token, usuario.getRoles()));
    }
}
