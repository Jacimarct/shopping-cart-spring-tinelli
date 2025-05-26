/*
package com.ecom.service;

import com.ecom.model.CepResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CepService {

    // 📝 Corrigido import do Logger (removido cast inadequado)
    private static final Logger logger = LoggerFactory.getLogger(CepService.class);

    private final RestTemplate restTemplate;

    public CepService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CepResponse consultarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        try {
            // 🔧 Evita fazer duas chamadas: obtém string apenas se necessário para debug
            CepResponse response = restTemplate.getForObject(url, CepResponse.class);

            // 📝 Verifica nulo e erro explicitamente
            if (response == null || Boolean.TRUE.equals(response.getErro())) {
                throw new IllegalArgumentException("CEP inválido ou não encontrado.");
            }

            logger.debug("Resposta da API ViaCEP (objeto): {}", response);
            return response;

        } catch (RestClientException e) {
            logger.error("Erro ao consultar CEP: {}", e.getMessage(), e); // 📝 Log com stack trace
            throw new RuntimeException("Erro ao consultar CEP: " + e.getMessage(), e);
        }
    }
}

*/


package com.ecom.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import com.ecom.model.CepResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ch.qos.logback.classic.Logger;

import org.slf4j.Logger.*;
import org.slf4j.LoggerFactory;

//import ch.qos.logback.classic.Logger;

@Service
public class CepService {
	
		private static final Logger logger = (Logger) LoggerFactory.getLogger(CepService.class);

	private final RestTemplate restTemplate;
    public CepService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
	
	
	
    public CepResponse consultarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        try {
        	
            // Captura a resposta da API como uma string (dados brutos)
            String dados = restTemplate.getForObject(url, String.class);
            

			// Log da resposta da API
            logger.debug("Resposta da API Mazinho: {} ", dados);
        	
            CepResponse response = restTemplate.getForObject(url, CepResponse.class);

            if (response == null || response.getErro() != null) {
                throw new IllegalArgumentException("CEP inválido ou não encontrado.");
            }

            return response;
        } catch (RestClientException e) {
            throw new RuntimeException("Erro ao consultar CEP: " + e.getMessage(), e);
        }
    }
} 