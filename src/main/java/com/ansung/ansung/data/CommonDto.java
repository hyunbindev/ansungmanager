package com.ansung.ansung.data;

import com.ansung.ansung.domain.Customer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class CommonDto {
	private String msg;
	private String redirectURL;
	
	
	public CommonDto(String msg , String redirectURL) {
		this.msg = msg;
		this.redirectURL = redirectURL;
	}
}