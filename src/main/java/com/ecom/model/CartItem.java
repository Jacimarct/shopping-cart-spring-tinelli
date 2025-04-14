package com.ecom.model;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name="cart_items")
public class CartItem {

	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

@ManyToOne
@JoinColumn(name="product_id")
 private Product product;

 private Integer quantity;

 @ManyToOne
 @JoinColumn(name="cart_id")
 private Cart cart;

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public Product getProduct() {
	return product;
}

public void setProduct(Product product) {
	this.product = product;
}

public Integer getQuantity() {
	return quantity;
}

public void setQuantity(Integer quantity) {
	this.quantity = quantity;
}

public Cart getCart() {
	return cart;
}

public void setCart(Cart cart) {
	this.cart = cart;
}
 
}
