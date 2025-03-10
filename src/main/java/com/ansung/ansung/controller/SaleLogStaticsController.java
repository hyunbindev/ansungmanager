package com.ansung.ansung.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ansung.ansung.data.salelogstatics.DailyIncomeDto;
import com.ansung.ansung.data.salelogstatics.IncomeDto;
import com.ansung.ansung.data.salelogstatics.MonthlcomeDto;
import com.ansung.ansung.service.salelog.SaleLogStaticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sale/statics")
@RequiredArgsConstructor
public class SaleLogStaticsController {
	private final SaleLogStaticsService saleLogStaticsService;
	@GetMapping("/daily")
	public ResponseEntity<IncomeDto<DailyIncomeDto>> getDailyIncomeStatics(
			@RequestParam("date") LocalDate date){
		return ResponseEntity.ok(saleLogStaticsService.getDailyIncome(date));
	}
	@GetMapping("/month")
	public ResponseEntity<IncomeDto<MonthlcomeDto>> getMonthIncomeStatics(
			@RequestParam("date") LocalDate date){
		return ResponseEntity.ok(saleLogStaticsService.getMonthIncome(date));
	}
}
