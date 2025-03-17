package com.ansung.ansung.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ansung.ansung.exception.CommonApiException;
import com.ansung.ansung.exception.DeliveryException;

@ControllerAdvice
public class ControllerExceptionAdvice {
	@ExceptionHandler(CommonApiException.class)
	public ResponseEntity<String> commonApiExceptionAdvice(CommonApiException exception){
		return new ResponseEntity<String>(exception.getMsg(),exception.getStatus());
	}
	@ExceptionHandler(DeliveryException.class)
	public ResponseEntity<String> deliveryExceptionAdvice(DeliveryException exception){
		return new ResponseEntity<String>(exception.getMessage(),exception.getStatus());
	}
}