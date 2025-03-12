package com.ansung.ansung.constant.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum CommonExceptionEnum {
	UNAUTHROIZED(HttpStatus.UNAUTHORIZED , "권한 없음"),
	NO_PRODUCT(HttpStatus.NO_CONTENT , "상품이 존재하지 않습니다."),
	NO_CUSTOMER(HttpStatus.NO_CONTENT , "고객정보가 존재하지 않습니다"),
	NO_CATEGORY(HttpStatus.NOT_FOUND,"분류가 존재하지 않습니다."),
	NO_SALELOG(HttpStatus.NOT_FOUND,"판매기록이 존재하지 않습니다"),
	NO_MANAGER(HttpStatus.UNAUTHORIZED,"관리자가 존재하지 않습니다"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"내부 서버오류");
	private final HttpStatus status;
	private final String msg;
	CommonExceptionEnum(HttpStatus status, String msg) {
		this.status = status;
		this.msg = msg;
	}
}