package com.ansung.ansung.data;

import java.util.ArrayList;
import java.util.List;

import com.ansung.ansung.domain.order.ProductOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOrderDto {
	private Long productId;
	private String productName;
	private String productSize;
	private int productPrice;
	private int quantity;
	public static ProductOrderDto ProductOrderToDto(ProductOrder productOrder) {
		return ProductOrderDto.builder()
				.productId(productOrder.getProductId())
				.productName(productOrder.getProductName())
				.productSize(productOrder.getSize())
				.productPrice(productOrder.getPrice())
				.quantity(productOrder.getQuantity())
				.build();
	}
	public static List<ProductOrderDto> ProductOrderListToDtoList(List<ProductOrder> productOrders){
		List<ProductOrderDto> dtoList = new ArrayList<>();
		for(ProductOrder productOrder : productOrders) {
			dtoList.add(ProductOrderDto.ProductOrderToDto(productOrder));
		}
		return dtoList;
	}
}