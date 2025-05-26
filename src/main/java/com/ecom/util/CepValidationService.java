/*package com.ecom.util;

//import com.ecom.util.LogradouroSimilarityValidator;
import com.ecom.service.validation.LogradouroSimilarityValidator;

import org.springframework.stereotype.Service;

@Service
public class CepValidationService {

    public boolean validarEndereco(
            String cidadeInformada,
            String estadoInformado,
            String cepInformado,
            String logradouroInformado,
            String cidadeEsperada,
            String estadoEsperado,
            String cepEsperado
    ) {
        if (cidadeInformada == null || estadoInformado == null || cepInformado == null || logradouroInformado == null) {
            return false;
        }

        boolean cidadeValida = cidadeInformada.trim().equalsIgnoreCase(cidadeEsperada);
        boolean estadoValido = estadoInformado.trim().equalsIgnoreCase(estadoEsperado);
        boolean cepValido = cepInformado.trim().equals(cepEsperado);

        boolean logradouroValido = LogradouroSimilarityValidator.validar(logradouroInformado, "Rua");
        
        // Você pode trocar "Rua" por um padrão que queira garantir

        return cidadeValida && estadoValido && cepValido && logradouroValido;
    }
}
*/