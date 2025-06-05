package com.sistema.autenticator.model;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioResponse {
    private String username;
    private String senha; // criptografada
    private List<String> roles;
}
