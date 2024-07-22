package br.com.beltis.ergo.dto;

import java.time.LocalDate;

public record ProjetoDTO(
        Long id,
        String titulo,
        String descricao,
        LocalDate dataInicio
) {
}
