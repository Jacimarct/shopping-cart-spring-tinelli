package com.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PedidoResponse {
    private Long pedidoId;
    private String mensagem;
}
