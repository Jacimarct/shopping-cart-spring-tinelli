package com.ecom.model;

import java.util.List;

import org.apache.catalina.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name="carts")
 
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	 @ManyToOne
	 @JoinColumn(name = "user_id") 
	 private UserDtls user;
	
	 @ManyToOne
	 @JoinColumn(name = "product_id") 
	 private Product product;
	
	private Integer quantity;
	
	private Double totalPrice;
    private Double totalOrderPrice; // Adicione se necessário
    
 		
}
