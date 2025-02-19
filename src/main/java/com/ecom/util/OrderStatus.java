package com.ecom.util;

public enum OrderStatus {

	IN_PROGRESS(1, " Em Andamento"), 
	ORDER_RECEIVED(2, " Pedido Recebido"), 
	PRODUCT_PACKED(3, " Produto Embalado"),
	OUT_FOR_DELIVERY(4, " Saiu para Entrega"), 
	DELIVERED(5, " Entregue"),
	CANCEL(6," Cancelado"),
	SUCCESS(7," Êxito");

	private Integer id;

	private String name;

	private OrderStatus(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}