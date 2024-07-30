package br.com.beltis.ergo.infra.security.dto;

public record LoginRequestDTO(
        String login,
        String password
) {
}
