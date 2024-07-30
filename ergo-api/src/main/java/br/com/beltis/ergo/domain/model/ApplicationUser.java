package br.com.beltis.ergo.domain.model;

import br.com.beltis.ergo.domain.model.enums.Prioridade;
import br.com.beltis.ergo.domain.model.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "ApplicationUser")
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @NotNull
    @NotEmpty(message = "The field username cannot be empty")
    @Column
    private String name;

    @Email
    @NotEmpty(message = "The field username cannot be empty")
    @Column(unique = true)
    private String login;

    @NotEmpty(message = "The field password cannot be empty")
    private String password;

    @Column()
    @Convert(converter = UserRole.UserRoleConverter.class)
    private UserRole role;

    public ApplicationUser(String name, String login, String password, UserRole role) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER"));
        else return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}