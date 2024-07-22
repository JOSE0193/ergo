package br.com.beltis.ergo.infra.security.dto;

public record LoginRequestDTO(
        String email,
        String password
) {
}
