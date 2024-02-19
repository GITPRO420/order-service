package com.dailypractice.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailypractice.orderservice.model.OrderRequest;
import com.dailypractice.orderservice.model.OrderResponse;
import com.dailypractice.orderservice.service.IOrderService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {
	
	@Autowired
	private IOrderService orderService;
	
	@PostMapping("/placeorder")
	public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderReq){
		Long orderId=orderService.placeOrder(orderReq);
		log.info("ORDER ID{}",orderId);
		return new ResponseEntity<Long>(orderId,HttpStatus.OK);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable("orderId") Long orderId){
		OrderResponse orderResponse=orderService.getOrderDetails(orderId);
		return new ResponseEntity<>(orderResponse,HttpStatus.OK);						
	}

}
