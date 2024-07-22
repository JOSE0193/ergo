package br.com.beltis.ergo.mapper;

import br.com.beltis.ergo.domain.model.Projeto;
import br.com.beltis.ergo.domain.model.Tarefa;
import br.com.beltis.ergo.domain.model.enums.Prioridade;
import br.com.beltis.ergo.dto.ProjetoRequestDTO;
import br.com.beltis.ergo.dto.TarefaDTO;
import br.com.beltis.ergo.dto.TarefaRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TarefaMapper {

    public TarefaDTO convertTarefasDTO(Tarefa tarefa){
        return new TarefaDTO(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getPrioridade(),
                tarefa.getEstimativaHoras(),
                tarefa.getProjeto().getTitulo()
        );
    };

    public Tarefa toModel(TarefaRequestDTO dto){
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());
        tarefa.setPrioridade(Prioridade.get(dto.prioridade()));
        tarefa.setEstimativaHoras(dto.estimativaHoras());
        tarefa.setProjeto(dto.projeto());
        return tarefa;
    }

}
