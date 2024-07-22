package br.com.beltis.ergo.repository;

import br.com.beltis.ergo.domain.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query("SELECT t FROM Tarefa t")
    List<Tarefa> findByAll();

    @Query("SELECT t FROM Tarefa t WHERE t.id = :id")
    Optional<Tarefa> findById(@Param("id") String id);

    @Query("SELECT t FROM Tarefa t WHERE t.titulo = :titulo")
    Optional<Tarefa> findByNome(@Param("titulo") String nome);


}
