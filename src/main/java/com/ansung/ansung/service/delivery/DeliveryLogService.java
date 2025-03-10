package com.ansung.ansung.service.delivery;


import org.springframework.stereotype.Service;

import com.ansung.ansung.constant.PayMethodEnum;
import com.ansung.ansung.data.ProductOrderDto;
import com.ansung.ansung.data.SaleLogDto;
import com.ansung.ansung.domain.order.Delivery;
import com.ansung.ansung.service.salelog.SaleLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryLogService {
	private final SaleLogService saleLogService;
	
	public void logCompeleteDelivery(Delivery delivery) {
		SaleLogDto saleLogDto = SaleLogDto.builder()
				.payMethod(PayMethodEnum.PAY_METHOD_CASH)
				.saleProducts(ProductOrderDto.ProductOrderListToDtoList(delivery.getProducts()))
				.build();
		saleLogService.addLog(saleLogDto);
	}
}