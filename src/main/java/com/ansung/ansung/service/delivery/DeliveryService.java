package com.ansung.ansung.service.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansung.ansung.constant.exception.CommonExceptionEnum;
import com.ansung.ansung.constant.exception.DeliveryExceptionEnum;
import com.ansung.ansung.data.DeliveryOrderDto;
import com.ansung.ansung.data.ProductOrderDto;
import com.ansung.ansung.data.response.PendingDeliveryDto;
import com.ansung.ansung.domain.Customer;
import com.ansung.ansung.domain.Product;
import com.ansung.ansung.domain.order.Delivery;
import com.ansung.ansung.domain.order.ProductOrder;
import com.ansung.ansung.exception.CommonApiException;
import com.ansung.ansung.exception.DeliveryException;
import com.ansung.ansung.repository.CustomerRepository;
import com.ansung.ansung.repository.DeliveryRepository;
import com.ansung.ansung.repository.ProductRepository;
import com.ansung.ansung.service.firebase.FCMService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryService {
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;
	private final DeliveryRepository deliveryRepository;
	//배달 주문 추가
	@Transactional
	public PendingDeliveryDto addDeliveryOrder(DeliveryOrderDto dto) {
		Customer customer = customerRepository.findById(dto.getCustomerId())
				.orElseThrow(()->new CommonApiException(CommonExceptionEnum.NO_CUSTOMER));
		List<ProductOrder>products = getProducts(dto.getOrders());
		Delivery delivery = new Delivery.Builder()
				.customer(customer)
				.products(products)
				.build();
		deliveryRepository.save(delivery);
		PendingDeliveryDto pendingDelivery = PendingDeliveryDto.builder()
				.deliveryId(delivery.getId())
				.roadAddress(delivery.getRoadAddress())
				.createdDate(delivery.getCreatedDate())
				.customerTel(delivery.getCustomerTel())
				.Lat(delivery.getLat())
				.Lng(delivery.getLng())
				.build();
		return pendingDelivery;
	}
	//배달 완료
	@Transactional
	public void completeDeliveryOrder(String deliveryId) {
		Delivery delivery = deliveryRepository.findById(deliveryId)
				.orElseThrow(()->new DeliveryException(DeliveryExceptionEnum.NO_DELIVERY_ORDER));
		delivery.complete();
		deliveryRepository.save(delivery);
	}
	//배달 조회
	@Transactional(readOnly = true)
	public List<PendingDeliveryDto> getDeliveryOrder(boolean isPending) {
		List<Delivery> pendingDeliverys = deliveryRepository.findByPending(isPending);
		List<PendingDeliveryDto> deliveryDtos = new ArrayList<>();
		for(Delivery delivery :pendingDeliverys) {
			PendingDeliveryDto pendingDelivery = PendingDeliveryDto.builder()
					.deliveryId(delivery.getId())
					.roadAddress(delivery.getRoadAddress())
					.createdDate(delivery.getCreatedDate())
					.customerTel(delivery.getCustomerTel())
					.Lat(delivery.getLat())
					.Lng(delivery.getLng())
					.build();
			deliveryDtos.add(pendingDelivery);
		}
		return deliveryDtos;
	}
	//배달 취소
	@Transactional
	public void cancelDeliveryOrder(String deliveryId) {
		Delivery delivery = deliveryRepository.findById(deliveryId)
				.orElseThrow(()->new DeliveryException(DeliveryExceptionEnum.NO_DELIVERY_ORDER));
		
		deliveryRepository.delete(delivery);
	}
	
	@Transactional(readOnly=true)
	public PendingDeliveryDto getPendingDeliveryDetail(String deliveryId) {
		Delivery delivery = deliveryRepository.findById(deliveryId)
				.orElseThrow(()->new DeliveryException(DeliveryExceptionEnum.NO_DELIVERY_ORDER));
		
		return PendingDeliveryDto.builder()
				.deliveryId(delivery.getId())
				.customerTel(delivery.getCustomerTel())
				.roadAddress(delivery.getRoadAddress())
				.remarks(delivery.getRemarks())
				.Lat(delivery.getLat())
				.Lng(delivery.getLng())
				.products(delivery.getProducts())
				.build();
	}
	
	@Transactional(readOnly = true)
	private List<ProductOrder> getProducts(List<ProductOrderDto>orders){
		List<ProductOrder> productsOrders = new ArrayList<>();
		for(ProductOrderDto productOrder : orders) {
			Product product = productRepository.findById(productOrder.getProductId())
					.orElseThrow(()-> new CommonApiException(CommonExceptionEnum.NO_PRODUCT));
			ProductOrder order = new ProductOrder(product,productOrder.getQuantity());
			productsOrders.add(order);
		}
		return productsOrders;
	}
}
