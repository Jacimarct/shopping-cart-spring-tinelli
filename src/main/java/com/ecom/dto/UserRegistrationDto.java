package com.ecom.dto;

import com.ecom.model.PaymentType;

	public class UserRegistrationDto {
	    private String name;
	    private String email;
	    private String password;
	    private String mobileNumber;
	    private String address;
	    private String city;
	    private String state;
	    private String pincode;
	    private String role;
	    private PaymentType paymentType;

	    @Override
		public String toString() {
			return "UserRegistrationDto [paymentType=" + paymentType + "]";
		}

		public PaymentType getPaymentType() {
			return paymentType;
		}

		public void setPaymentType(PaymentType paymentType) {
			this.paymentType = paymentType;
		}

		// Construtor padrão necessário para o Spring
	    public UserRegistrationDto() {
	        this.role = "ROLE_USER"; // Define o papel padrão como usuário comum
	    }

	    // Getters e Setters existentes
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getMobileNumber() {
	        return mobileNumber;
	    }

	    public void setMobileNumber(String mobileNumber) {
	        this.mobileNumber = mobileNumber;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public String getCity() {
	        return city;
	    }

	    public void setCity(String city) {
	        this.city = city;
	    }

	    public String getState() {
	        return state;
	    }

	    public void setState(String state) {
	        this.state = state;
	    }

	    public String getPincode() {
	        return pincode;
	    }

	    public void setPincode(String pincode) {
	        this.pincode = pincode;
	    }

	    public String getRole() {
	        return role;
	    }

	    public void setRole(String role) {
	        this.role = role;
	    }
	}