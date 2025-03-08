package com.ansung.ansung.service.delivery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ansung.ansung.constant.exception.CommonExceptionEnum;
import com.ansung.ansung.data.DeliveryOrderDto;
import com.ansung.ansung.data.ProductOrderDto;
import com.ansung.ansung.domain.Customer;
import com.ansung.ansung.domain.Product;
import com.ansung.ansung.domain.order.Delivery;
import com.ansung.ansung.exception.CommonApiException;
import com.ansung.ansung.repository.CustomerRepository;
import com.ansung.ansung.repository.DeliveryRepository;
import com.ansung.ansung.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private DeliveryRepository deliveryRepository;
	
	@InjectMocks
	private DeliveryService deliveryService;
	
	private static Customer customer;
	private static Product product;
	private static DeliveryOrderDto dto;
	
	@BeforeAll
	public static void setup() {
		customer = new Customer();
		customer.setId(1L);
		customer.setTel("01054426043");
		customer.setJibunAddress("장안 2동");
		customer.setRoadAddress("장안 2동");
		customer.setLat("2");
		customer.setLng("2");
		customer.setRemarks("3층");
		
		product = new Product();
		product.setId(1L);
		product.setName("진천쌀");
		product.setPrice(12000);
		product.setSize("10kg");
		
		dto = new DeliveryOrderDto();
		dto.setCustomerId(1L);
		List<ProductOrderDto> productOrders = new ArrayList<>();
		ProductOrderDto productOrderDto = new ProductOrderDto();
		productOrderDto.setProductId(1L);
		productOrderDto.setQuantity(10);
		productOrders.add(productOrderDto);
		dto.setOrders(productOrders);
	}
	
	@Test
	@DisplayName("배달주문 추가 테스트")
	void addDeliveryOrderTest() {
		//given
		when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
		
		//when
		deliveryService.addDeliveryOrder(dto);
		verify(deliveryRepository, times(1)).save(any(Delivery.class));
		
	}
}
