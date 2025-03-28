package com.ecom.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import com.ecom.model.CepResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Service
public class CepService {

    private final RestTemplate restTemplate;

    public CepService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CepResponse consultarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        try {
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