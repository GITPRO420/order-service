package com.dailypractice.orderservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dailypractice.orderservice.model.PaymentRequest;

@FeignClient(name="PAYMENT-SERVICE/payment")
public interface PaymentServiceFeign {
	
	@PostMapping
	ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

}
