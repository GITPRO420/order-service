package com.dailypractice.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductDetails {

	private long productId;
	private String productName;
	private long price;
	private long quantity;

}
