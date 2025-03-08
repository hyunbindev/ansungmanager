package com.ansung.ansung.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDto {
	private ProductDto product;
	private int quantity;
}