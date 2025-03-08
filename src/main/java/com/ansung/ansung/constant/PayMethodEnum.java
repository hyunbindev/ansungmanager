package com.ansung.ansung.constant;

import lombok.Getter;

@Getter
public enum PayMethodEnum {
	PAY_METHOD_CARD("카드"),
	PAY_METHOD_CASH("현금");
	private final String methodType;
	PayMethodEnum(String method){
		this.methodType = method;
	}
}
