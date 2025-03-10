package com.ansung.ansung.data;

import com.ansung.ansung.domain.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CustomerDto {
	private Long id;
	private String tel;
	private String jibunAddress;
	private String remarks;
	private String roadAddress;
	private String Lat;
	private String Lng;
	public Customer toEntity() {
		Customer entity = new Customer();
		entity.setId(this.id);
		entity.setTel(this.tel);
		entity.setJibunAddress(this.jibunAddress);
		entity.setRoadAddress(this.roadAddress);
		entity.setRemarks(this.remarks);
		entity.setLat(this.Lat);
		entity.setLng(this.Lng);
		return entity;
	}
	public CustomerDto () {};
}
