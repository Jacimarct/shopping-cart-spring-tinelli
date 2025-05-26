package com.ecom.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoRequest {

    @NotNull(message = "O ID do cliente é obrigatório.")
    private Long clienteId;

    @NotNull(message = "O valor total do pedido é obrigatório.")
    private Double valorTotal;

    @NotEmpty(message = "O CEP de entrega é obrigatório.")
    private String cepEntrega;

    @NotEmpty(message = "O número de entrega é obrigatório.")
    private String numeroEntrega;

    @NotEmpty(message = "O complemento de entrega é obrigatório.")
    private String complementoEntrega;

    @NotEmpty(message = "O CEP de cobrança é obrigatório.")
    private String cepCobranca;

    @NotEmpty(message = "O número de cobrança é obrigatório.")
    private String numeroCobranca;

    @NotEmpty(message = "O complemento de cobrança é obrigatório.")
    private String complementoCobranca;
}
