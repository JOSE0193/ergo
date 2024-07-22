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
public enum Prioridade {
    MUITO_ALTA("Muito Alta"),
    ALTA("Alta"),
    BAIXA("Baixa"),
    MUITO_BAIXA("Muito Baixa");

    private final String value;

    public static Prioridade get(String valor) {
        return Arrays.stream(values())
                .filter(it -> it.value.equals(valor))
                .findFirst()
                .orElse(null);
    }

    public static class PrioridadeConverter implements AttributeConverter<Prioridade, String> {

        @Override
        public String convertToDatabaseColumn(Prioridade prioridade) {
            return Optional.ofNullable(prioridade)
                    .map(Prioridade::getValue)
                    .orElse(null);
        }

        @Override
        public Prioridade convertToEntityAttribute(String valor) {
            return Prioridade.get(valor);
        }
    }

}
