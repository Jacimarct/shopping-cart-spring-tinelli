package com.ecom.service;

import org.springframework.stereotype.Service;
import java.text.Normalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CepValidationService {

    private static final Logger logger = LoggerFactory.getLogger(CepValidationService.class);

    public boolean validarEndereco(
        String cidadeRecebida, 
        String estadoRecebido, 
        String cepRecebido, 
        String cidadeEsperada, 
        String estadoEsperado, 
        String cepEsperado) 
    {
        logger.debug(">> ENTROU no método validarEndereco()");
        logger.debug("== INÍCIO DA VALIDAÇÃO DO ENDEREÇO ==");

        // Logs dos valores originais
        logger.debug("Recebido - Cidade: '{}', Estado: '{}', CEP: '{}'",
                     cidadeRecebida, estadoRecebido, cepRecebido);

        // Verificação de valores nulos
        if (cidadeRecebida == null || estadoRecebido == null || cepRecebido == null) {
            logger.debug("Um ou mais campos recebidos são nulos. Retornando false.");
            return false;
        }

        // Normalização
        String cidadeNormalizada         = removeAcentos(cidadeRecebida.trim().toLowerCase());
        String cidadeEsperadaNormalizada = removeAcentos(cidadeEsperada.trim().toLowerCase());
        String estadoNormalizado         = estadoRecebido.trim().toUpperCase();
        String estadoEsperadoNormalizado = estadoEsperado.trim().toUpperCase();
        String cepNormalizado            = cepRecebido.trim();
        String cepEsperadoNormalizado    = cepEsperado.trim();

        // Logs dos valores normalizados
        logger.debug("Normalizado - Cidade: '{}', Estado: '{}', CEP: '{}'",
                     cidadeNormalizada, estadoNormalizado, cepNormalizado);
        logger.debug("Esperado    - Cidade: '{}', Estado: '{}', CEP: '{}'",
                     cidadeEsperadaNormalizada, estadoEsperadoNormalizado, cepEsperadoNormalizado);

        // Comparações
        boolean cidadeValida = cidadeNormalizada.equals(cidadeEsperadaNormalizada);
        boolean estadoValido = estadoNormalizado.equals(estadoEsperadoNormalizado);
        boolean cepValido    = cepNormalizado.equals(cepEsperadoNormalizado);

        logger.debug("Resultados - Cidade: {}, Estado: {}, CEP: {}",
                     cidadeValida, estadoValido, cepValido);

        boolean resultadoFinal = cidadeValida && estadoValido && cepValido;

        // Log de diferenças (se houver)
        if (!resultadoFinal) {
            StringBuilder diferencas = new StringBuilder();
            if (!cidadeValida)
                diferencas.append(String.format("Cidade: recebida='%s', esperada='%s'\n",
                                                cidadeNormalizada, cidadeEsperadaNormalizada));
            if (!estadoValido)
                diferencas.append(String.format("Estado: recebido='%s', esperado='%s'\n",
                                                estadoNormalizado, estadoEsperadoNormalizado));
            if (!cepValido)
                diferencas.append(String.format("CEP: recebido='%s', esperado='%s'\n",
                                                cepNormalizado, cepEsperadoNormalizado));

            logger.debug("Diferenças encontradas:\n{}", diferencas.toString());
        }

        logger.debug("== FIM DA VALIDAÇÃO DO ENDEREÇO == Resultado: {}", resultadoFinal);
        return resultadoFinal;
    }

    // Método para remover acentos
    private static String removeAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                         .replaceAll("[^\\p{ASCII}]", "");
    }
}


/* package com.ecom.service;

import org.springframework.stereotype.Service;

import java.text.Normalizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CepValidationService {

    private static final Logger logger = LoggerFactory.getLogger(CepValidationService.class);

    public boolean validarEndereco(
        String cidadeRecebida, 
        String estadoRecebido, 
        String cepRecebido, 
//        String logradouroRecebido, 
        String cidadeEsperada, 
        String estadoEsperado, 
        String cepEsperado) 
//        String logradouroEsperado) 
    {

        logger.debug(">> ENTROU no método validarEndereco()");
        logger.debug("== INÍCIO DA VALIDAÇÃO DO ENDEREÇO ==");

        // Logs dos valores recebidos
        logger.debug("Cidade recebida    (original): '{}'", cidadeRecebida);
        logger.debug("Estado recebido    (original): '{}'", estadoRecebido);
        logger.debug("CEP recebido:      (original): '{}'", cepRecebido);
//        logger.debug("Endereço recebido: (original): '{}'", logradouroRecebido);

        // ✅ Verificação de valores nulos
        if (cidadeRecebida == null || estadoRecebido == null || cepRecebido == null) { //|| logradouroRecebido == null) { // 🔧 Modificado para verificar todos
            logger.debug("Um ou mais campos recebidos são nulos. Retornando false.");
            return false;
        }

        // Normalização dos valores recebidos
        String cidadeNormalizada = removeAcentos(cidadeRecebida.trim().toLowerCase());
        String estadoNormalizado = estadoRecebido.trim().toUpperCase();
        String cepNormalizado = cepRecebido.trim();
//        String logradouroNormalizado = removeAcentos(logradouroRecebido.trim().toUpperCase());

        // ✅ Logs dos valores normalizados recebidos
        logger.debug("Cidade normalizada recebida: '{}'", cidadeNormalizada);
        logger.debug("Estado normalizado recebido: '{}'", estadoNormalizado);
        logger.debug("CEP normalizado recebido: '{}'", cepNormalizado);
//        logger.debug("Logradouro normalizado recebido: '{}'", logradouroNormalizado);

        // Normalização dos valores esperados
        String cidadeEsperadaNormalizada = removeAcentos(cidadeEsperada.trim().toLowerCase());
        String estadoEsperadoNormalizado = estadoEsperado.trim().toUpperCase();
        String cepEsperadoNormalizado = cepEsperado.trim();
//        String logradouroEsperadoNormalizado = removeAcentos(logradouroEsperado.trim().toUpperCase());

        // ✅ Logs dos valores esperados normalizados
        logger.debug("Cidade esperada normalizada: '{}'", cidadeEsperadaNormalizada);
        logger.debug("Estado esperado normalizado: '{}'", estadoEsperadoNormalizado);
        logger.debug("CEP esperado normalizado: '{}'", cepEsperadoNormalizado);
//        logger.debug("Logradouro esperado normalizado: '{}'", logradouroEsperadoNormalizado);

        // Comparação de dados
        boolean cidadeValida = cidadeNormalizada.equals(cidadeEsperadaNormalizada);
        boolean estadoValido = estadoNormalizado.equals(estadoEsperadoNormalizado);
        boolean cepValido = cepNormalizado.equals(cepEsperadoNormalizado);
//        boolean logradouroValido = logradouroNormalizado.equals(logradouroEsperadoNormalizado);

        // Logs de resultados de cada comparação
        logger.debug("Resultado comparação cidade: {}", cidadeValida);
        logger.debug("Resultado comparação estado: {}", estadoValido);
        logger.debug("Resultado comparação cep: {}", cepValido);
//        logger.debug("Resultado comparação logradouro: {}", logradouroValido);

        // Verificação final e log das diferenças
        boolean resultadoFinal = cidadeValida && estadoValido && cepValido;  // && logradouroValido;

        if (!resultadoFinal) {
            StringBuilder diferencas = new StringBuilder(); // ✅ Adicionado aqui, pois só é necessário se houver erro
            if (!cidadeValida)
                diferencas.append("Cidade: recebida='").append(cidadeNormalizada).append("', esperada='").append(cidadeEsperadaNormalizada).append("'\n");
            if (!estadoValido)
                diferencas.append("Estado: recebido='").append(estadoNormalizado).append("', esperado='").append(estadoEsperadoNormalizado).append("'\n");
            if (!cepValido)
                diferencas.append("CEP: recebido='").append(cepNormalizado).append("', esperado='").append(cepEsperadoNormalizado).append("'\n");
//            if (!logradouroValido)
//                diferencas.append("Logradouro: recebido='").append(logradouroNormalizado).append("', esperado='").append(logradouroEsperadoNormalizado).append("'\n");

            logger.debug("Diferenças encontradas:\n{}", diferencas.toString());
        }

        logger.debug("== FIM DA VALIDAÇÃO DO ENDEREÇO == Resultado: {}", resultadoFinal);
        return resultadoFinal;
    }

    // Método para remover acentos
    private static String removeAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                         .replaceAll("[^\\p{ASCII}]", "");
    }
} */
              