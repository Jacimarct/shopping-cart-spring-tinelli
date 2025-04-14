package com.ecom.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentType {
    COD,
    PIX,
    CREDIT_CARD,
    DEBIT_CARD
   
	}