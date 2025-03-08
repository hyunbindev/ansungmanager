package com.ansung.ansung.domain.salelog;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Document(collection = "saleProduct")
@Getter
@Builder
public class SaleProduct {
	@Id
	private String id;
	private Long productId;
	private String productName;
	private String productSize;
	private int price;
	private int quantity;
	@CreatedDate
	private LocalDateTime saleTime;
}
