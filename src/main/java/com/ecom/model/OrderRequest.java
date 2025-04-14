package com.ecom.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.EnumType;
import lombok.Data;
import lombok.ToString;

@ToString
@Data

public class OrderRequest {

	private String firstName;

	private String lastName;

	private String email;

	private String mobileNo;

	private String address;

	private String city;

	private String state;

	private String pincode;
	
	 private BigDecimal totalAmount;
	 
	 private PaymentType paymentType;

	 private List<Cart> items;
	 
	 
}
