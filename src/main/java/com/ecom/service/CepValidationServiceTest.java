/*package com.ecom.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ecom.service.validation.LogradouroSimilarityValidator;



//import org.junit.jupiter.api.BeforeEach;

//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;

public class CepValidationServiceTest {

    private CepValidationService cepValidationService;

    @BeforeEach
    public void setup() {
        // Instância real do validador de logradouro
        LogradouroSimilarityValidator logValidator = new LogradouroSimilarityValidator();

        // Serviço de validação com injeção manual do validador
        cepValidationService = new CepValidationService();
        cepValidationService.logradouroValidator = logValidator;
    }

    @Test
    public void deveValidarEnderecoComDadosCorretos() {
        boolean valido = cepValidationService.validarEndereco(
                "Guaçuí", "es", "29560000",
                "guaçui", "ES", "29560000"
        );
        assertTrue(valido);
    }

    @Test
    public void deveInvalidarCidadeDiferente() {
        boolean valido = cepValidationService.validarEndereco(
                "Vitória", "ES", "29560000",
                "guaçui", "ES", "29560000"
        );
        assertFalse(valido);
    }

    @Test
    public void deveValidarEnderecoComLogradouroSimilar() {
        boolean valido = cepValidationService.validarEnderecoComLogradouro(
                "Guaçuí", "ES", "29560000", "Rua XV de Novembro",
                "Guaçuí", "ES", "29560000", "Rua 15 de Novembro"
        );
        assertTrue(valido);
    }

    @Test
    public void deveInvalidarLogradouroMuitoDiferente() {
        boolean valido = cepValidationService.validarEnderecoComLogradouro(
                "Guaçuí", "ES", "29560000", "Rua das Flores",
                "Guaçuí", "ES", "29560000", "Avenida Paulista"
        );
        assertFalse(valido);
    }

    @Test
    public void deveInvalidarSeLogradouroNulo() {
        boolean valido = cepValidationService.validarEnderecoComLogradouro(
                "Guaçuí", "ES", "29560000", null,
                "Guaçuí", "ES", "29560000", "Rua ABC"
        );
        assertFalse(valido);
    }
}
*/