package br.com.beltis.ergo.infra.security.dto;

public record UserDTO(

        String nome,
        String login,
        String role
) {
}
