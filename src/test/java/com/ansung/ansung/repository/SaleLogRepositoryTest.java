package com.ansung.ansung.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.ansung.ansung.constant.PayMethodEnum;
import com.ansung.ansung.domain.salelog.SaleLog;

@DataMongoTest
class SaleLogRepositoryTest {
	@Autowired
	private SaleLogRepository testRepo;
	
	@Test
	void getByRangeTest() {
		SaleLog saleLog = SaleLog.builder()
				.isDelivery(false)
				.payMethod(PayMethodEnum.PAY_METHOD_CARD)
				.build();
		SaleLog log = testRepo.save(saleLog);
		LocalDateTime from = LocalDate.now().minusDays(1).atStartOfDay();
		LocalDateTime to = LocalDate.now().plusDays(1).atStartOfDay();
		List<SaleLog> logs = testRepo.findBySaleDateBetween(from, to);
		testRepo.delete(log);
		assertThat(logs.size()).isEqualTo(1);
	}
	
	@Test
	@DisplayName("날짜 단위 조회")
	void getByDateTest() {
		int saveCount = 10;
		List<SaleLog> testEntitys = new ArrayList<>();

		for(int i =0; i<saveCount; i++) {
			SaleLog saleLog = SaleLog.builder()
					.isDelivery(false)
					.payMethod(PayMethodEnum.PAY_METHOD_CARD)
					.build();
			SaleLog saleLogEntity = testRepo.save(saleLog);
			testEntitys.add(saleLogEntity);
		}
		LocalDate today = LocalDate.now();
		LocalDateTime from = today.atStartOfDay();
		LocalDateTime to = today.atTime(23, 59);
		List<SaleLog> logs = testRepo.findBySaleDateBetween(from, to);
		assertThat(logs.size()).isEqualTo(testEntitys.size());
	}
}
