package com.ansung.ansung.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ansung.ansung.data.SaleLogDto;
import com.ansung.ansung.service.salelog.SaleLogService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sale")
public class SaleController {
	private final SaleLogService saleLogService;
	@PostMapping
	public ResponseEntity<Void> addLog(@RequestBody SaleLogDto saleLogDto){
		saleLogService.addLog(saleLogDto);
		return ResponseEntity.ok().build();
	}
	@GetMapping("/{saleLogId}")
	public ResponseEntity<SaleLogDto> getLog(@PathVariable("saleLogId") String saleLogId){
		SaleLogDto response = saleLogService.getLog(saleLogId);
		return ResponseEntity.ok(response);
	}
	@GetMapping
	public ResponseEntity<List<SaleLogDto>> getLogByDateRange(@RequestParam("from") LocalDateTime startDate , @RequestParam("to") LocalDateTime endDate){
		List<SaleLogDto> response = saleLogService.getLogByDateRange(startDate, endDate);
		return ResponseEntity.ok(response);
	}
}
