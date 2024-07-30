package br.com.beltis.ergo.infra.security.dto;

import br.com.beltis.ergo.domain.model.enums.UserRole;

public record RegisterRequestDTO(
        String name,
        String login,
        String password
) { }
