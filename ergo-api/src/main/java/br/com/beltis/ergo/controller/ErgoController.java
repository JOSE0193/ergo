package br.com.beltis.ergo.controller;

import br.com.beltis.ergo.domain.exception.EntidadeEmUsoException;
import br.com.beltis.ergo.domain.exception.EntidadeNaoEncontradaException;
import br.com.beltis.ergo.domain.model.Projeto;
import br.com.beltis.ergo.domain.model.Tarefa;
import br.com.beltis.ergo.domain.model.enums.Prioridade;
import br.com.beltis.ergo.dto.ProjetoDTO;
import br.com.beltis.ergo.dto.ProjetoRequestDTO;
import br.com.beltis.ergo.dto.TarefaRequestDTO;
import br.com.beltis.ergo.mapper.ProjetoMapper;
import br.com.beltis.ergo.mapper.TarefaMapper;
import br.com.beltis.ergo.repository.ProjetoRepository;
import br.com.beltis.ergo.repository.TarefaRepository;
import br.com.beltis.ergo.service.ProjetoService;
import br.com.beltis.ergo.service.TarefaService;
import br.com.beltis.ergo.dto.TarefaDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@AllArgsConstructor
@RequestMapping("/v1/api/ergo")
public class ErgoController {

    @Autowired
    private ProjetoService projetoService;
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private TarefaService tarefaService;
    @Autowired
    private ProjetoMapper projetoMapper;
    @Autowired
    private TarefaMapper tarefaMapper;


    @GetMapping("/listar-Projetos")
    public ResponseEntity<List<?>> listarProjetos(){
        List<ProjetoDTO> listaProjetos = projetoService.consultar();
        if(listaProjetos.isEmpty()){
            return ResponseEntity.notFound().build();
        } return ResponseEntity.ok(listaProjetos);
    }

    @GetMapping("/buscar-projeto/{id}")
    public ResponseEntity<ProjetoDTO> buscarProjeto(@PathVariable Long id){
        ProjetoDTO projetoDTO = projetoService.buscar(id);
        if(projetoDTO != null){
            return ResponseEntity.ok(projetoDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/adicionar-projeto")
    @ResponseStatus(HttpStatus.CREATED)
    public Projeto adicionarProjeto(@RequestBody @Valid ProjetoRequestDTO dto){
        return projetoService.salvar(projetoMapper.toModel(dto));
    }

    @PutMapping("/atualizar-projeto/{id}")
    public ResponseEntity<?> atualizarProjeto(@PathVariable Long id, @RequestBody ProjetoRequestDTO projetoRequestDTO){
        Projeto projetoAtual = projetoRepository.findById(id).orElse(null);
        if (projetoAtual != null) {
            BeanUtils.copyProperties(projetoMapper.toModel(projetoRequestDTO), projetoAtual, "id", "dataInicio");
            projetoAtual = projetoService.salvar(projetoAtual);
            return ResponseEntity.ok(projetoAtual);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/excluir-projeto/{id}")
    public ResponseEntity<?> excluirProjeto (@PathVariable Long id){
        try {
            projetoService.excluir(id);
            return ResponseEntity.noContent().build();
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/listar-tarefas")
    public ResponseEntity<List<?>> listarTarefas(){
        List<TarefaDTO> listarTarefas = tarefaService.consultar();
        if(listarTarefas.isEmpty()){
            return ResponseEntity.notFound().build();
        } return ResponseEntity.ok(listarTarefas);
    }

    @GetMapping("/buscar-tarefa/{id}")
    public ResponseEntity<TarefaDTO> buscarTarefa(@PathVariable Long id){
        TarefaDTO tarefaDTO = tarefaService.buscar(id);
        if(tarefaDTO != null){
            return ResponseEntity.ok(tarefaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/adicionar-tarefa")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionarTarefa(@RequestBody @Valid TarefaRequestDTO dto){
        try {
            Tarefa tarefa = tarefaService.salvar(this.tarefaMapper.toModel(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(tarefa);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/atualizar-tarefa/{id}")
    public ResponseEntity<?> atualizarTarefa(@PathVariable Long id, @RequestBody TarefaRequestDTO tarefaRequestDTO){
        Tarefa tarefaAtual = tarefaRepository.findById(id).orElse(null);
        if (tarefaAtual != null) {
            BeanUtils.copyProperties(this.tarefaMapper.toModel(tarefaRequestDTO), tarefaAtual, "id");
            tarefaAtual = tarefaService.salvar(tarefaAtual);
            return ResponseEntity.ok(tarefaAtual);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/excluir-tarefa/{id}")
    public ResponseEntity<?> excluirTarefa (@PathVariable Long id){
        try {
            tarefaService.excluir(id);
            return ResponseEntity.noContent().build();
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}