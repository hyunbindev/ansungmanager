package com.ansung.ansung.domain.salelog;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ansung.ansung.constant.PayMethodEnum;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "salelog")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleLog {
	@Id
	private String id;
	List<SaleProduct> products;
	private PayMethodEnum payMethod;
	private int totalPrice;
	private boolean isDelivery;
	@CreatedDate
	private LocalDateTime saleDate;
}