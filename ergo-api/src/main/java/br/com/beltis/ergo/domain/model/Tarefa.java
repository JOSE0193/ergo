package br.com.beltis.ergo.domain.model;

import br.com.beltis.ergo.domain.model.enums.Prioridade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "Tarefa")
public class Tarefa implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "prioridade",nullable = false)
    @Convert(converter = Prioridade.PrioridadeConverter.class)
    private Prioridade prioridade;

    @NotNull
    @Column(name = "data_inicio",nullable = false)
    private Integer estimativaHoras;

    @ManyToOne
    @JoinColumn(name = "tarefa_id", nullable = false)
    private Projeto projeto;

}
