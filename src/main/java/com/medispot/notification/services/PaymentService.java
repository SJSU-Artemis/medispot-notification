package com.medispot.notification.services;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.medispot.notification.models.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Service
public class PaymentService {
	@Value("${stripe.secret.key}")
	private String secretKey;
	
    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

	public Charge charge(PaymentRequest chargeRequest) throws StripeException {
		Map<String, Object> chargeParams = new HashMap<>();
		chargeParams.put("amount", chargeRequest.getAmount());
		chargeParams.put("currency", "USD");
		chargeParams.put("source", chargeRequest.getToken().getId());
		Charge charge = Charge.create(chargeParams);
		return charge;
	}
}
