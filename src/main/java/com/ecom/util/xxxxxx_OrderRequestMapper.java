/*package com.ecom.util;

import com.ecom.dto.OrderRequest;
import com.ecom.dto.EnderecoDTO;
import com.ecom.model.Pedido;
import com.ecom.model.Endereco;
import com.ecom.model.OrderRequest as OrderRequestModel;
import com.ecom.model.ItemPedido;

import java.util.List;
import java.util.stream.Collectors;

public class xxxxxx_OrderRequestMapper {

    public static OrderRequestModel toModel(OrderRequest dto) {
        if (dto == null) {
            return null;
        }

        OrderRequestModel model = new OrderRequestModel();

        model.setEnderecoEntrega(toEnderecoModel(dto.getEnderecoEntrega()));
        model.setEnderecoCobranca(toEnderecoModel(dto.getEnderecoCobranca()));
        model.setItensPedido(toItensPedidoModel(dto.getItensPedido()));
        model.setFormaPagamento(dto.getFormaPagamento());
        model.setObservacoes(dto.getObservacoes());

        return model;
    }

    private static Endereco toEnderecoModel(EnderecoDTO dto) {
        if (dto == null) {
            return null;
        }

        Endereco endereco = new Endereco();
        endereco.setCep(dto.getCep());
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());

        return endereco;
    }

    private static List<ItemPedido> toItensPedidoModel(List<ItemPedido> itensDto) {
        if (itensDto == null) {
            return null;
        }

        // Se ItemPedido for o mesmo nos dois pacotes (DTO e Model), você pode retornar direto
        // Se forem diferentes, será necessário criar também um mapper para ItemPedidoDTO

        return itensDto.stream()
                .map(item -> {
                    ItemPedido novo = new ItemPedido();
                    novo.setProduto(item.getProduto());
                    novo.setQuantidade(item.getQuantidade());
                    novo.setPreco(item.getPreco());
                    return novo;
                })
                .collect(Collectors.toList());
    }
}
*/