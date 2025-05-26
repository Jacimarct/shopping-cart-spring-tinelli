package com.ecom.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDate orderDate;

	@ManyToOne
	private Product product;

	private Double price;
	
	private Integer quantity;
	
	@ManyToOne
	private UserDtls user;
	
	private String status;
	
//	private String paymentType;
	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	private PaymentType paymentType;
	
	@ManyToOne
	@OneToOne(cascade = CascadeType.ALL)
	private Order orderAddress;
	
	  private String orderId; 
	  private Boolean printed; 
	  private String productName;
	  
	  @Column(name = "total_amount") 
	  private BigDecimal totalAmount;

      private String pincode;
      
      private String address;
      
      private String city;
      
      private String state;
	  
	  
	  //	@OneToOne(cascade = CascadeType.ALL)
//	private Order orderAddress;

}
