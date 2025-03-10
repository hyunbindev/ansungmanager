package com.ansung.ansung.service.salelog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ansung.ansung.data.salelogstatics.DailyIncomeDto;
import com.ansung.ansung.data.salelogstatics.IncomeDto;
import com.ansung.ansung.data.salelogstatics.MonthlcomeDto;
import com.ansung.ansung.domain.salelog.SaleLog;
import com.ansung.ansung.repository.SaleLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleLogStaticsService {
	private final SaleLogRepository saleLogRepository;
	
	public IncomeDto<DailyIncomeDto> getDailyIncome(LocalDate searchDate) {
		LocalDateTime from = searchDate.atStartOfDay();
		LocalDateTime to = searchDate.atTime(23, 59, 59);
		List<SaleLog> saleLogs = saleLogRepository.findBySaleDateBetween(from, to);
		List<DailyIncomeDto> dailyIncomeDtos = new ArrayList<>();
		for(SaleLog saleLog : saleLogs) {
			dailyIncomeDtos.add(new DailyIncomeDto(saleLog.getSaleDate(), saleLog.getTotalPrice(), saleLog.getPayMethod()));
		}
		return new IncomeDto<DailyIncomeDto>(dailyIncomeDtos);
	}
	public IncomeDto<MonthlcomeDto> getMonthIncome(LocalDate searchMonthDate) {
		LocalDateTime from = searchMonthDate.withDayOfMonth(searchMonthDate.getMonthValue()).atStartOfDay();
		LocalDateTime to = from.plusMonths(1).minusDays(1);
		List<SaleLog> saleLogs = saleLogRepository.findBySaleDateBetween(from, to);
		List<MonthlcomeDto> dailyIncomeDtos = new ArrayList<>();
		for(SaleLog saleLog : saleLogs) {
			dailyIncomeDtos.add(new MonthlcomeDto(saleLog.getSaleDate().toLocalDate(), saleLog.getTotalPrice(), saleLog.getPayMethod()));
		}
		return new IncomeDto<MonthlcomeDto>(dailyIncomeDtos);
	}
}
