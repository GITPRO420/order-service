package com.dailypractice.orderservice.service;

import com.dailypractice.orderservice.model.OrderRequest;
import com.dailypractice.orderservice.model.OrderResponse;

public interface IOrderService {

	Long placeOrder(OrderRequest orderReq);

	OrderResponse getOrderDetails(Long orderId);

}
