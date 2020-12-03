package com.medispot.notification.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medispot.notification.models.PaymentRequest;
import com.medispot.notification.services.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@RestController
@CrossOrigin
@RequestMapping("/api/payment")
public class PaymentController {
	

		private PaymentService paymentService;

		@Autowired
		public PaymentController(PaymentService paymentService) {
			this.paymentService = paymentService;
		}

		@PostMapping
		public String completePayment(@RequestBody PaymentRequest request) throws StripeException {
			Charge charge =  paymentService.charge(request);
			return  charge.toJson();
		}
}
