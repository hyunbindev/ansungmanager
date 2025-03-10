package com.ansung.ansung.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleDto {
	List<SaleProductDto>saleProductDto;
	Long CustomerId;
	boolean delivery;
}
