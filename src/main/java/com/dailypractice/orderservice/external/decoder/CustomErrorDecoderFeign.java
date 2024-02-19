package com.dailypractice.orderservice.external.decoder;

import java.io.IOException;

import com.dailypractice.orderservice.exception.CustomExceptionOrderService;
import com.dailypractice.orderservice.external.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomErrorDecoderFeign implements ErrorDecoder{

	@Override
	public Exception decode(String methodKey, Response response) {
		
		ObjectMapper objectMapper=new ObjectMapper();
		
		log.info("{}",response.request().url());
		log.info("{}",response.request().headers());
		
		try {
			ErrorResponse errorResponse=objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
			return new CustomExceptionOrderService(errorResponse.getErrorMessage(), errorResponse.getErrorCode(), response.status());
		} catch (IOException e) {
			
			throw new CustomExceptionOrderService("INTERNAL SERVER ERROR","INTERNAL_SERVER_ERROR",500);
		}
		
	
	}

}
