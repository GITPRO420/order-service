package com.dailypractice.orderservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class CustomExceptionOrderService extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private int status;
	
	public CustomExceptionOrderService(String message, String errorCode,int status)
	{
		super(message);
		this.errorCode=errorCode;
		this.status=status;
	}

}
