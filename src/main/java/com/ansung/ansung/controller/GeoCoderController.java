package com.ansung.ansung.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ansung.ansung.data.AddressDto;
import com.ansung.ansung.data.GeoCoderDto;
import com.ansung.ansung.service.geocoder.GeocoderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/geocoder")
@RequiredArgsConstructor
public class GeoCoderController {
	private final GeocoderService geocoderService;
	@GetMapping
	public ResponseEntity<AddressDto> seachByAddress(@RequestParam("address")String address) {
		GeoCoderDto response = geocoderService.getGeoCode(address);
		if(response.getAddresses().isEmpty()) {
			return new ResponseEntity<AddressDto>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<AddressDto>(response.getAddresses().get(0),HttpStatus.OK);
	}
}
