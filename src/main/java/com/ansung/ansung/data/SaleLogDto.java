package com.ansung.ansung.data;

import java.util.List;

import com.ansung.ansung.constant.PayMethodEnum;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaleLogDto {
	private String id;
	@NotNull
	private PayMethodEnum payMethod;
	private List<ProductOrderDto> saleProducts;
	private boolean isDelivery;
	private Long customerId;
	private int totalPrice;
}