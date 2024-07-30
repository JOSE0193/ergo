package br.com.beltis.ergo.repository;

import br.com.beltis.ergo.domain.model.ApplicationUser;
import br.com.beltis.ergo.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, UUID> {

    @Query("SELECT u FROM ApplicationUser u WHERE u.login = :login")
    Optional<ApplicationUser> findByLogin(@Param("login") String login);

}
