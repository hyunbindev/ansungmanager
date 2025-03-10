package com.ansung.ansung.service.salelog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansung.ansung.constant.exception.CommonExceptionEnum;
import com.ansung.ansung.controller.CategoryController;
import com.ansung.ansung.data.ProductOrderDto;
import com.ansung.ansung.data.SaleLogDto;
import com.ansung.ansung.domain.Product;
import com.ansung.ansung.domain.salelog.SaleLog;
import com.ansung.ansung.domain.salelog.SaleProduct;
import com.ansung.ansung.exception.CommonApiException;
import com.ansung.ansung.repository.ProductRepository;
import com.ansung.ansung.repository.SaleLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleLogService {
	private final SaleLogRepository saleLogRepository;
	private final ProductRepository productRepository;
	//판매 기록 추가
	@Transactional
	public SaleLog addLog(SaleLogDto saleLogDto) {
		List<SaleProduct>saleProducts = new ArrayList<>();
		int totalPrice = 0;
		
		for(ProductOrderDto saleProductDto : saleLogDto.getSaleProducts()) {
			Product product = productRepository.findById(saleProductDto.getProductId())
					.orElseThrow(()->new CommonApiException(CommonExceptionEnum.NO_PRODUCT));
			
			SaleProduct saleProduct = SaleProduct.builder()
					.productId(product.getId())
					.productName(product.getName())
					.productSize(product.getSize())
					.price(product.getPrice())
					.quantity(saleProductDto.getQuantity())
					.build();
			//주문 총합 계산
			totalPrice+=product.getPrice() * saleProductDto.getQuantity();
			//판매 상품 목록 리스트 추가
			saleProducts.add(saleProduct);
		}
		SaleLog saleLog = SaleLog.builder()
				.products(saleProducts)
				.payMethod(saleLogDto.getPayMethod())
				.totalPrice(totalPrice)
				.build();
		saleLog = saleLogRepository.save(saleLog);
		
		return saleLog;
	}
	//판매기록 개별 조회
	@Transactional(readOnly = true)
	public SaleLogDto getLog(String logId) {
		SaleLog saleLog = saleLogRepository.findById(logId)
				.orElseThrow(()->new CommonApiException(CommonExceptionEnum.NO_SALELOG));
		return SaleLogDto.builder()
				.id(saleLog.getId())
				.payMethod(saleLog.getPayMethod())
				.totalPrice(saleLog.getTotalPrice())
				.saleProducts(mappingProductOrderDto(saleLog))
				.build();
	}
	//날짜 기준 판매 기록 조회
	@Transactional(readOnly = true)
	public List<SaleLogDto> getLogByDateRange(LocalDateTime startDate,LocalDateTime endDate) {
		List<SaleLog> saleLogs = saleLogRepository.findBySaleDateBetween(startDate, endDate);
		List<SaleLogDto> saleLogDtos = new ArrayList<>();
		for(SaleLog saleLog : saleLogs) {
			List<ProductOrderDto> productOrderDtos = mappingProductOrderDto(saleLog);
			saleLogDtos.add(
					SaleLogDto.builder()
					.id(saleLog.getId())
					.saleProducts(productOrderDtos)
					.isDelivery(saleLog.isDelivery())
					.totalPrice(saleLog.getTotalPrice())
					.build()
					);
		}
		return saleLogDtos;
	}
	
	//판매 상품 DataTransferObject 변환
	private List<ProductOrderDto> mappingProductOrderDto(SaleLog saleLog){
		List<ProductOrderDto> saleProducts = new ArrayList<>();
		
		for(SaleProduct saleProduct : saleLog.getProducts()){
			saleProducts.add(ProductOrderDto.builder()
					.productId(saleProduct.getProductId())
					.productName(saleProduct.getProductName())
					.productPrice(saleProduct.getPrice())
					.quantity(saleProduct.getQuantity())
					.build());
		}
		return saleProducts;
	}
}