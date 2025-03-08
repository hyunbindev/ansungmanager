package com.ansung.ansung.exception;

import org.springframework.http.HttpStatus;

import com.ansung.ansung.constant.exception.DeliveryExceptionEnum;

public class DeliveryException extends RuntimeException{
	private DeliveryExceptionEnum exceptionEnum;
	public DeliveryException(DeliveryExceptionEnum exceptionEnum) {
		super(exceptionEnum.getMsg());
		this.exceptionEnum = exceptionEnum;
	}
	public HttpStatus getStatus() {
		return this.exceptionEnum.getStatus();
	}
}
