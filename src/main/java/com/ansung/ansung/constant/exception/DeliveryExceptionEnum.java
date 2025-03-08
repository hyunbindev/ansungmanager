package com.ansung.ansung.constant.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum DeliveryExceptionEnum {
	NO_DELIVERY_ORDER(HttpStatus.NO_CONTENT , "주문이 존재하지 않습니다.");
	private final HttpStatus status;
	private final String msg;
	DeliveryExceptionEnum(HttpStatus status, String msg){
		this.status = status;
		this.msg = msg;
	}
}
