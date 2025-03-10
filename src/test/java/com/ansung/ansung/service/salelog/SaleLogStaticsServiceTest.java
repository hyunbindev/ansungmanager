package com.ansung.ansung.service.salelog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ansung.ansung.constant.PayMethodEnum;
import com.ansung.ansung.data.salelogstatics.DailyIncomeDto;
import com.ansung.ansung.data.salelogstatics.IncomeDto;
import com.ansung.ansung.domain.salelog.SaleLog;
import com.ansung.ansung.domain.salelog.SaleProduct;
import com.ansung.ansung.repository.SaleLogRepository;

@ExtendWith(MockitoExtension.class)
public class SaleLogStaticsServiceTest {
	@Mock
	private SaleLogRepository saleLogRepository;
	@InjectMocks
	private SaleLogStaticsService service;
	
	private static SaleLog saleLog1;
	private static SaleLog saleLog2;
	private static List<SaleLog> saleLogList;
	@BeforeAll
	static void setup() {
		saleLog1 = SaleLog.builder()
		.id("saleLog1")
		.products(new ArrayList<SaleProduct>())
		.payMethod(PayMethodEnum.PAY_METHOD_CASH)
		.totalPrice(100000)
		.saleDate(LocalDateTime.now())
		.build();
		saleLog2 = SaleLog.builder()
				.id("saleLog2")
				.products(new ArrayList<SaleProduct>())
				.payMethod(PayMethodEnum.PAY_METHOD_CASH)
				.totalPrice(5000)
				.saleDate(LocalDateTime.now())
				.build();
		saleLogList = new ArrayList<>();
		saleLogList.add(saleLog1);
		saleLogList.add(saleLog2);
	}
	@Test
	@DisplayName("daily income history")
	void dailyIncomeStaticsTest() {
		when(saleLogRepository.findBySaleDateBetween(any(LocalDateTime.class),any(LocalDateTime.class))).thenReturn(saleLogList);
		LocalDate today = LocalDate.now();
		IncomeDto<DailyIncomeDto> response = service.getDailyIncome(today);
		int expectTotalIncome =0;
		for(SaleLog saleLog : saleLogList) {
			expectTotalIncome += saleLog.getTotalPrice();
		}
		assertThat(response.getTotalIncome()).isEqualTo(expectTotalIncome);
	}
	@Test
	@DisplayName("month income")
	void monthIncomeStaticsTest() {
		
	}
}
