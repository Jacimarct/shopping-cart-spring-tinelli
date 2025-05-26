package com.ecom.service;

import com.ecom.service.impl.OrderServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecom.model.OrderRequest;

@Service
public class PedidoService {  // Adicionei uma classe que contém o método

    @Autowired
    private CepValidationService cepValidationService; // Certifique-se de que esta classe existe e está corretamente injetada

    public void processarPedido(com.ecom.dto.OrderRequest orderRequest) throws Exception {
        boolean cepValido = cepValidationService.validarEndereco(
            orderRequest.getCity(),
            orderRequest.getState(),
            orderRequest.getPincode(),
//            orderRequest.getAddress(), ********************************************************************************************************
            "Guaçuí",
            "ES",
            "29560000"
            
        );

        if (!cepValido) {
            throw new Exception("Endereço fora da área de cobertura (Guaçuí - ES) ou logradouro inválido.");
        }

        // Continuação do processamento do pedido...
    }
}
