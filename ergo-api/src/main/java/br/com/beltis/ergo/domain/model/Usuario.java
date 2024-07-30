package br.com.beltis.ergo.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "Usuario")
public class Usuario {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @NotEmpty(message = "The field username cannot be empty")
    @Column
    private String nome;

    @NotNull
    @Column(unique = true)
    private String email;

    @NonNull
    @NotEmpty(message = "The field password cannot be empty")
    private String password;


}
