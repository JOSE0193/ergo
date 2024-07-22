package br.com.beltis.ergo.service;

import br.com.beltis.ergo.domain.exception.EntidadeEmUsoException;
import br.com.beltis.ergo.domain.exception.EntidadeNaoEncontradaException;
import br.com.beltis.ergo.domain.model.Projeto;
import br.com.beltis.ergo.domain.model.Tarefa;
import br.com.beltis.ergo.dto.TarefaDTO;
import br.com.beltis.ergo.mapper.TarefaMapper;
import br.com.beltis.ergo.repository.ProjetoRepository;
import br.com.beltis.ergo.repository.TarefaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private TarefaMapper tarefaMapper;

    public List<TarefaDTO> consultar(){
        List<Tarefa> listaTarefas = tarefaRepository.findAll();
        return listaTarefas.stream().map(this.tarefaMapper::convertTarefasDTO).collect(Collectors.toList());
    }

    public TarefaDTO buscar(Long id){
        try{
            Tarefa tarefa = tarefaRepository.findById(id).orElse(null);
            return this.tarefaMapper.convertTarefasDTO(tarefa);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de tarefa com o código %d", id));
        }
    }

    public Tarefa salvar(Tarefa tarefa){
        Long projetoId = tarefa.getProjeto().getId();
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(()-> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de projeto com código %d", projetoId)));
        tarefa.setProjeto(projeto);
        return tarefaRepository.save(tarefa);
    }

    public void excluir(Long id){
        try {
            tarefaRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de tarefa com o código %d", id));
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("Tarefa de código %d não pode ser excluido, pois está em uso", id));
        }
    }

}
