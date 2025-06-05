package com.sistema.autenticator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthRequest {
    private String username;

    @JsonProperty("senha")
    private String password;
}
