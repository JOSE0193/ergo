package br.com.beltis.ergo.dto;

import br.com.beltis.ergo.domain.model.enums.Prioridade;

public record TarefaDTO(
        Long id,
        String titulo,
        String descricao,
        Prioridade prioridade,
        Integer estimativaHoras,
        String tituloProjeto
) {
}
