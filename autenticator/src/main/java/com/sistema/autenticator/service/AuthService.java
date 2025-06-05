package com.sistema.autenticator.service;

import com.sistema.autenticator.model.UsuarioResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Getter
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioResponse obterUsuarioComSenhaEPerfil(String username) {
        UsuarioResponse usuario = buscarUsuario("http://localhost:8081/api/professores/internal/auth/" + username);

        if (usuario == null) {
            usuario = buscarUsuario("http://localhost:8081/api/administradores/internal/auth/" + username);
        }

        return usuario;
    }

    private UsuarioResponse buscarUsuario(String url) {
        try {
            return restTemplate.getForObject(url, UsuarioResponse.class);
        } catch (Exception e) {
            return null; // Usuário não encontrado nesse serviço
        }
    }

}


