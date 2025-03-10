package com.ansung.ansung.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ansung.ansung.data.DeliveryOrderDto;
import com.ansung.ansung.data.response.PendingDeliveryDto;
import com.ansung.ansung.service.delivery.DeliveryService;
import com.ansung.ansung.service.firebase.FCMService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
@Slf4j
public class DeliveryController {
	private final DeliveryService deliveryService;
	private final FCMService fcmService;
	@PostMapping
	public ResponseEntity<Void> addDeliveryOrder(@RequestBody DeliveryOrderDto dto){
		PendingDeliveryDto result= deliveryService.addDeliveryOrder(dto);
		fcmService.sendAllMessage("새로운 배달 주문", result.getRoadAddress());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@GetMapping
	public ResponseEntity<List<PendingDeliveryDto>> getPendingDelivery(@RequestParam(value ="isPending", defaultValue = "true")boolean isPending){
		return new ResponseEntity<List<PendingDeliveryDto>>
			(deliveryService.getDeliveryOrder(isPending),HttpStatus.OK);
	}
	@GetMapping("/detail")
	public ResponseEntity<PendingDeliveryDto> getPendingDeliveryDetail(@RequestParam("deliveryId")String deliveryId){
		return new ResponseEntity<PendingDeliveryDto>
			(deliveryService.getPendingDeliveryDetail(deliveryId),HttpStatus.OK);
	}
	@PostMapping("/complete")
	public ResponseEntity<Void> completeDeliveryOrder(@RequestParam(value ="deliveryId", required = true)String deliveryId){
		deliveryService.completeDeliveryOrder(deliveryId);
		fcmService.sendAllMessage("배달 완료", "배달 완료");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}