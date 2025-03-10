package com.ansung.ansung.exception;

import org.springframework.http.HttpStatus;

import com.ansung.ansung.constant.exception.CommonExceptionEnum;


public class CommonApiException extends RuntimeException {
	private CommonExceptionEnum excpetionEnum;
	private HttpStatus status;
	
	public CommonApiException(CommonExceptionEnum exceptionEnum){
		super(exceptionEnum.getMsg());
		this.excpetionEnum = exceptionEnum;
		this.status = excpetionEnum.getStatus();
	}
	
	public CommonApiException(HttpStatus status , String msg) {
		super(msg);
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return this.status;
	}
	
	public String getMsg() {
		return this.getMessage();
	}
	public CommonExceptionEnum getEnum() {
		return this.excpetionEnum;
	}
}
