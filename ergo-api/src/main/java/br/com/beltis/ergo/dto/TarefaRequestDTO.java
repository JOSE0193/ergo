package br.com.beltis.ergo.dto;

import br.com.beltis.ergo.domain.model.Projeto;

public record TarefaRequestDTO(
        String titulo,
        String descricao,
        String prioridade,
        Integer estimativaHoras,
        Projeto projeto
) {
}
