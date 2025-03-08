package com.ansung.ansung.data.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ansung.ansung.domain.order.ProductOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PendingDeliveryDto {
	private String deliveryId;
	private String roadAddress;
	private String customerTel;
	private String Lat;
	private String Lng;
	private LocalDateTime createdDate;
	private String remarks;
	private List<ProductOrder> products;
}