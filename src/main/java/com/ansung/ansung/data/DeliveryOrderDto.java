package com.ansung.ansung.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryOrderDto {
	private Long customerId;
	private List<ProductOrderDto> orders;
}