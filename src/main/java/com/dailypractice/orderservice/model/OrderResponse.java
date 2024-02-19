package com.dailypractice.orderservice.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
	
	private Long orderId;
	private Instant orderDate;
	private String orderStatus;
	private Long amount;
	private ProductDetails productDetails;
	private PaymentDetails paymentDetails;

}
