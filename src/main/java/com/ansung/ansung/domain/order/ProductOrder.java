package com.ansung.ansung.domain.order;

import com.ansung.ansung.domain.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductOrder {
	private String productName;
	private int price;
	private int quantity;
	private String size;
	public ProductOrder(Product product,int quantity) {
		this.productName = product.getName();
		this.price = product.getPrice();
		this.quantity = quantity;
		this.size = product.getSize();
	}
}
