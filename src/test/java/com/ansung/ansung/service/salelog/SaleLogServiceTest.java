package com.ansung.ansung.service.salelog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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

import com.ansung.ansung.constant.PayMethodEnum;
import com.ansung.ansung.constant.exception.CommonExceptionEnum;
import com.ansung.ansung.data.ProductOrderDto;
import com.ansung.ansung.data.SaleLogDto;
import com.ansung.ansung.domain.Product;
import com.ansung.ansung.domain.salelog.SaleLog;
import com.ansung.ansung.domain.salelog.SaleProduct;
import com.ansung.ansung.exception.CommonApiException;
import com.ansung.ansung.repository.CustomerRepository;
import com.ansung.ansung.repository.ProductRepository;
import com.ansung.ansung.repository.SaleLogRepository;

@ExtendWith(MockitoExtension.class)
class SaleLogServiceTest {
	@Mock
	private SaleLogRepository saleLogRepository;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private CustomerRepository customerRepository;
	@InjectMocks
	private SaleLogService saleLogService;
	
	private static Product product1;
	private static Product product2;
	private static Product invalidProduct;
	private static SaleLog saleLog1;
	@BeforeAll
	public static void setup() {
		product1 = new Product();
		product1.setId(1L);
		product1.setName("진천쌀");
		product1.setPrice(12000);
		product1.setSize("10kg");
		
		product2 = new Product();
		product2.setId(2L);
		product2.setName("철원 오대쌀");
		product2.setPrice(47000);
		product2.setSize("20kg");
		
		invalidProduct = new Product();
		invalidProduct.setId(3L);
		
		saleLog1 = SaleLog.builder()
		.id("asdfasdf")
		.products(new ArrayList<SaleProduct>())
		.payMethod(PayMethodEnum.PAY_METHOD_CASH)
		.totalPrice(100000)
		.saleDate(LocalDateTime.now())
		.build();
	}
	
	@Test
	@DisplayName("판매 로그 추가 테스트")
	void addLogTest() {
		//given
		List<ProductOrderDto> products = new ArrayList<>();
		ProductOrderDto productDto1 = ProductOrderDto.builder()
				.productId(1L)
				.quantity(2)
				.build();
		ProductOrderDto productDto2 = ProductOrderDto.builder()
				.productId(2L)
				.quantity(3)
				.build();
		products.add(productDto1);
		products.add(productDto2);
		
	    when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
	    when(productRepository.findById(product2.getId())).thenReturn(Optional.of(product2));
	    
		SaleLogDto givenDto = SaleLogDto.builder()
				.isDelivery(false)
				.saleProducts(products)
				.payMethod(PayMethodEnum.PAY_METHOD_CASH)
				.build();
		//when
		saleLogService.addLog(givenDto);
		//then
		verify(saleLogRepository, times(1)).save(any(SaleLog.class));
	}
	@Test
	@DisplayName("존재하지 않는 상품 판매")
	void addLogTestByInvalidProduct() {
		when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
		when(productRepository.findById(product2.getId())).thenReturn(Optional.of(product2));
		when(productRepository.findById(invalidProduct.getId())).thenReturn(Optional.empty());
		
		List<ProductOrderDto> products = new ArrayList<>();
		
		ProductOrderDto productDto1 = ProductOrderDto.builder()
				.productId(product1.getId())
				.quantity(2)
				.build();
		ProductOrderDto productDto2 = ProductOrderDto.builder()
				.productId(product2.getId())
				.quantity(3)
				.build();
		
		ProductOrderDto invalidProductDto = ProductOrderDto.builder()
				.productId(invalidProduct.getId())
				.quantity(4)
				.build();
		
		SaleLogDto givenDto = SaleLogDto.builder()
				.isDelivery(false)
				.saleProducts(products)
				.payMethod(PayMethodEnum.PAY_METHOD_CASH)
				.build();
		
		products.add(productDto1);
		products.add(productDto2);
		products.add(invalidProductDto);
		
		CommonApiException excepetion = assertThrows(CommonApiException.class, () -> {
			SaleLog response = saleLogService.addLog(givenDto);
	        assertThat(saleLog1.getId()).isEqualTo(response.getId());
	    });
		assertThat(excepetion.getEnum()).isEqualTo(CommonExceptionEnum.NO_PRODUCT);
	}
	
	@Test
	@DisplayName("판매 로그 조회")
	void getLogTest() {
		when(saleLogRepository.findById(saleLog1.getId())).thenReturn(Optional.of(saleLog1));
		SaleLogDto log = saleLogService.getLog(saleLog1.getId());
		assertThat(log.getId()).isEqualTo(saleLog1.getId());
	}
	
	@Test
	@DisplayName("없는 판매 로그 조회")
	void getInvalidLogTest() {
		when(saleLogRepository.findById(saleLog1.getId())).thenReturn(Optional.empty());
		CommonApiException exception = assertThrows(CommonApiException.class, () -> {
			SaleLogDto response = saleLogService.getLog(saleLog1.getId());
	        assertThat(saleLog1.getId()).isEqualTo(response.getId());
	    });
		assertThat(exception.getEnum()).isEqualTo(CommonExceptionEnum.NO_SALELOG);
	}
}
