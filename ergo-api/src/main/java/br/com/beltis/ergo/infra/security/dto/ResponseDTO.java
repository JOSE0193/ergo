package br.com.beltis.ergo.infra.security.dto;

public record ResponseDTO(
        String login,
        String token
) {
}
