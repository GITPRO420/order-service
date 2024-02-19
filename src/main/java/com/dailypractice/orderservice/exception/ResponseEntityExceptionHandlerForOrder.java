
package com.dailypractice.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dailypractice.orderservice.external.response.ErrorResponse;

@ControllerAdvice
public class ResponseEntityExceptionHandlerForOrder extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomExceptionOrderService.class)
	public ResponseEntity<ErrorResponse> handleCustomeExceptionOrderService(CustomExceptionOrderService exception) {

		return new ResponseEntity<>(ErrorResponse.builder().errorMessage(exception.getMessage())
				.errorCode(exception.getErrorCode()).build(), HttpStatus.valueOf(exception.getStatus()));
	}

}
