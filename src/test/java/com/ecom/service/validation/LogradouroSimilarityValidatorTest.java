/*package com.ecom.service.validation;

import org.junit.jupiter.api.Test;

import com.ecom.util.LogradouroSimilarityValidator;

import static org.junit.jupiter.api.Assertions.*;

class LogradouroSimilarityValidatorTest {

    // Instância da classe a ser testada
    private final LogradouroSimilarityValidator validator = new LogradouroSimilarityValidator();

    @Test
    void deveAceitarLogradourosIdenticos() {
        String logradouroAPI = "Rua Comendador Aguiar";
        String enderecoUser = "Rua Comendador Aguiar";

        boolean resultado = validator.validarSimilaridadeEntreDoisLogradouros(logradouroAPI, enderecoUser);

        // ✅ Alteração: Verifica se o resultado é verdadeiro
        assertTrue(resultado);
    }

    @Test
    void deveAceitarVariacaoComComplemento() {
        String logradouroAPI = "Rua Comendador Aguiar";
        String enderecoUser = "rua comendador aguiar, 409 centro";

        boolean resultado = validator.validarSimilaridadeEntreDoisLogradouros(logradouroAPI, enderecoUser);

        // ✅ Alteração: Verifica se o resultado é verdadeiro
        assertTrue(resultado);
    }

    @Test
    void naoDeveAceitarLogradourosDiferentes() {
        String logradouroAPI = "Rua Comendador Aguiar";
        String enderecoUser = "Avenida Central";

        boolean resultado = validator.validarSimilaridadeEntreDoisLogradouros(logradouroAPI, enderecoUser);

        // ✅ Alteração: Verifica se o resultado é falso
        assertFalse(resultado);
    }

    @Test
    void deveLidarComValoresNulos() {
        String logradouroAPI = null;
        String enderecoUser = "Rua Comendador Aguiar";

        boolean resultado = validator.validarSimilaridadeEntreDoisLogradouros(logradouroAPI, enderecoUser);

        // ✅ Alteração: Verifica se o resultado é falso
        assertFalse(resultado);
    }
} */