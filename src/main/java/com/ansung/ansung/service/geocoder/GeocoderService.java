package com.ansung.ansung.service.geocoder;



import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ansung.ansung.data.GeoCoderDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GeocoderService {
	
	private final String url;
	private final String APP_KEY;
	private final String SECRET_KEY;
	
	public GeocoderService (Environment enviroment) {
		this.url = enviroment.getProperty("naver.map.api.geocoderurl");
		this.APP_KEY = enviroment.getProperty("naver.map.api.appkey");
		this.SECRET_KEY = enviroment.getProperty("naver.map.api.secretkey");
	}
	
	public GeoCoderDto getGeoCode(String address) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("x-ncp-apigw-api-key-id", APP_KEY);
		httpHeaders.add("x-ncp-apigw-api-key", SECRET_KEY);
		httpHeaders.add("Accept", "application/json");
		
		HttpEntity request = new HttpEntity(httpHeaders);
		
		ResponseEntity<GeoCoderDto> response = restTemplate.exchange(url+"?query="+address, HttpMethod.GET,request,GeoCoderDto.class);
		
		return response.getBody();
	}
}
