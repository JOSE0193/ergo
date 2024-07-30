package br.com.beltis.ergo.domain.model.enums;

import jakarta.persistence.AttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
@ToString
public enum UserRole {

    ADMIN("ADMIN"),
    USER("USER");

    private String valor;

    public static UserRole get(String valor) {
        return Arrays.stream(values())
                .filter(it -> it.valor.equals(valor))
                .findFirst()
                .orElse(null);
    }


    public static class UserRoleConverter implements AttributeConverter<UserRole, String> {

        @Override
        public String convertToDatabaseColumn(UserRole userRole) {
            return Optional.ofNullable(userRole)
                    .map(UserRole::getValor)
                    .orElse(null);

        }

        @Override
        public UserRole convertToEntityAttribute(String valor) {
            return UserRole.get(valor);
        }
    }

}
