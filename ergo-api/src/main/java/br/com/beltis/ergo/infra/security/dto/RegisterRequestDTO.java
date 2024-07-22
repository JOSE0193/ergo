package br.com.beltis.ergo.infra.security.dto;

public record RegisterRequestDTO(
        String name,
        String email,
        String password
) {
}
