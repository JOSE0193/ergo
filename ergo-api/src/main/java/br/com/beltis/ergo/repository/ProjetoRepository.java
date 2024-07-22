package br.com.beltis.ergo.repository;

import br.com.beltis.ergo.domain.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    @Query("SELECT p FROM Projeto p")
    List<Projeto> findAll();

    @Query("SELECT p FROM Projeto p WHERE p.id = :id")
    Optional<Projeto> findById(@Param("id") String id);

    @Query("SELECT p FROM Projeto p WHERE p.titulo = :titulo")
    Optional<Projeto> findByNome(@Param("titulo") String nome);

}
