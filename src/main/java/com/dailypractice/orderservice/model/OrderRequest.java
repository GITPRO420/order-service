package com.dailypractice.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
	
	private Long productId;
	private Long amount;
	private Long quantity;
	private PaymentMode paymentMode;

}
