/* package com.ecom.service.validation;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LogradouroSimilarityValidator {

    // Lista de logradouros permitidos para o CEP 29560000
    private static final Set<String> LOGRADOUROS_PERMITIDOS = Stream.of(
        "Rua Principal",
        "Avenida Central",
        "Travessa Comercial",
        "Praça da Matriz"
    ).collect(Collectors.toSet());

    // Distância máxima permitida para considerar similar
    private static final int DISTANCIA_MAXIMA = 3;
    
    private final LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

    public boolean validarSimilaridade(String logradouro) {
        if (logradouro == null || logradouro.trim().isEmpty()) {
            return false;
        }

        String normalizedInput = normalizarLogradouro(logradouro);
        
        return LOGRADOUROS_PERMITIDOS.stream()
            .anyMatch(permitido -> {
                String normalizedPermitido = normalizarLogradouro(permitido);
                int distance = levenshteinDistance.apply(normalizedInput, normalizedPermitido);
                return distance <= DISTANCIA_MAXIMA;
            });
    }
    
    private String normalizarLogradouro(String logradouro) {
        return logradouro.trim()
            .toLowerCase()
            .replaceAll("\\s+", " ")
            .replaceAll("[^a-z0-9\\s]", "");
    }
} */

package com.ecom.service.validation;

import org.springframework.stereotype.Component;

@Component
public class LogradouroSimilarityValidator {

	public static boolean validar(String logradouro1, String logradouro2) { //public boolean validarSimilaridadeEntreDoisLogradouros(String logradouro1, String logradouro2) {
        if (logradouro1 == null || logradouro2 == null) {
            return false;
        }

        String normalizado1 = logradouro1.trim().toLowerCase();
        String normalizado2 = logradouro2.trim().toLowerCase();

        // Pode ser substituído por algoritmo de similaridade (como Levenshtein)
        return normalizado1.equals(normalizado2);
    }
}
