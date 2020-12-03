package com.medispot.notification.models;

import lombok.Data;

@Data
public class PaymentRequest {
	private String description;
	private int amount;
	private String currency;
	private String stripeEmail;
	private Token token;
}
