package br.com.beltis.ergo.mapper;

import br.com.beltis.ergo.domain.model.Projeto;
import br.com.beltis.ergo.dto.ProjetoDTO;
import br.com.beltis.ergo.dto.ProjetoRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ProjetoMapper {

    public ProjetoDTO convertTodto(Projeto projeto){
        return new ProjetoDTO(projeto.getId(), projeto.getTitulo(), projeto.getDescricao(), projeto.getDataInicio());
    };

    public Projeto toModel(ProjetoRequestDTO dto){
        Projeto projeto = new Projeto();
        projeto.setTitulo(dto.titulo());
        projeto.setDescricao(dto.descricao());
        projeto.setDataInicio(LocalDate.now());
        return projeto;
    }

}
