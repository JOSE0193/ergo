package br.com.beltis.ergo.service;

import br.com.beltis.ergo.domain.exception.EntidadeEmUsoException;
import br.com.beltis.ergo.domain.exception.EntidadeNaoEncontradaException;
import br.com.beltis.ergo.domain.model.Projeto;
import br.com.beltis.ergo.dto.ProjetoDTO;
import br.com.beltis.ergo.mapper.ProjetoMapper;
import br.com.beltis.ergo.repository.ProjetoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjetoService {

    private ProjetoRepository projetoRepository;
    @Autowired
    private ProjetoMapper projetoMapper;

    public List<ProjetoDTO> consultar(){
        try {
            List<Projeto> listaProjetos = projetoRepository.findAll();
            return listaProjetos.stream().map(this.projetoMapper::convertTodto).collect(Collectors.toList());
        }catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existem projetos cadastrados"));
        }
    }

    public ProjetoDTO buscar(Long id){
        try{
            Projeto projeto = projetoRepository.findById(id).orElse(null);
            return this.projetoMapper.convertTodto(projeto);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                String.format("Não existe cadastro de projeto com o código %d", id));
        }
    }

    public Projeto salvar(Projeto projeto){
        return projetoRepository.save(projeto);
    }

    public void excluir(Long id){
        try {
            projetoRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de projeto com o código %d", id));
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("Projeto de código %d não pode ser excluido, pois está em uso", id));
        }
    }

}
