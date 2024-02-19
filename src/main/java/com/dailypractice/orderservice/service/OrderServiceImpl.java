package com.dailypractice.orderservice.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dailypractice.orderservice.entity.Order;
import com.dailypractice.orderservice.exception.CustomExceptionOrderService;
import com.dailypractice.orderservice.external.client.PaymentServiceFeign;
import com.dailypractice.orderservice.external.client.ProductServiceFeign;
import com.dailypractice.orderservice.model.OrderRequest;
import com.dailypractice.orderservice.model.OrderResponse;
import com.dailypractice.orderservice.model.PaymentDetails;
import com.dailypractice.orderservice.model.PaymentRequest;
import com.dailypractice.orderservice.model.ProductDetails;
import com.dailypractice.orderservice.repository.IOrderRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IOrderRepository orderRepo;

	@Autowired
	private ProductServiceFeign productServiceFeign;

	@Autowired
	private PaymentServiceFeign paymentServiceFeign;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Long placeOrder(OrderRequest orderReq) {

		// ORDER ENTITY--> Save the data with Status Order Created..
		// PRODUCT SERVICE-> Block Products (Reduce the Quantity)
		// PAYMENT SERVICE-> Payment -> success-> Mark Order as COMPLETE Else CANCELLED.

		log.info("PLACING ORDER REQUEST{}", orderReq);

		productServiceFeign.reduceQuantity(orderReq.getProductId(), orderReq.getQuantity());

		Order order = Order.builder().amount(orderReq.getAmount()).orderStatus("CREATED")
				.productId(orderReq.getProductId()).qunatity(orderReq.getQuantity()).orderDate(Instant.now()).build();

		log.info("Creating Order with Status CREATD");

		order = orderRepo.save(order);

		log.info("Calling PAYMENT-SERVICE to complete the Payment");

		PaymentRequest paymentRequest = PaymentRequest.builder().orderId(order.getOrderId())
				.paymentMode(orderReq.getPaymentMode()).amount(orderReq.getAmount()).build();
		String orderStatus = null;
		try {
			paymentServiceFeign.doPayment(paymentRequest);
			log.info("Payment done Successfully... Changing the OrderStatus as PLACED");
			orderStatus = "PLACED";
		} catch (Exception e) {
			log.error("ERROR Occurred durin Payment, Changing OrderStatus as PAYMENT_FAILED");
			orderStatus = "PAYMENT_FAILED";
		}
		order.setOrderStatus(orderStatus);
		orderRepo.save(order);

		log.info("ORDER PLACED SUCCESSFULLY WITH ORDER ID{}", order.getOrderId());

		return order.getOrderId();
	}

	@Override
	public OrderResponse getOrderDetails(Long orderId) {
		log.info("Get Order Details for Order Id{}", orderId);
		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new CustomExceptionOrderService("ORDER NOT FOUND FOR THE ORDER ID::" + orderId,
						"ORDER_NOT_FOUND", 404));
		log.info("Invoking PRODUCT SERVICE to fetch Product Details::{}");
		
		ProductDetails productDetails = restTemplate
				.getForObject("http://PRODUCT-SERVICE/product/" + order.getProductId(), ProductDetails.class);

		log.info("Getting PAYMENT information from PAYMENT-SERVICE");
		PaymentDetails paymentRespose = restTemplate.getForObject("http://PAYMENT-SERVICE/payment/order/" + orderId,
				PaymentDetails.class);
		log.info("ORDER Details::{}", order);
		OrderResponse orderResponse = OrderResponse.builder().orderId(orderId).orderStatus(order.getOrderStatus())
				.amount(order.getAmount()).orderDate(order.getOrderDate()).productDetails(productDetails)
				.paymentDetails(paymentRespose).build();
		return orderResponse;
	}
}
