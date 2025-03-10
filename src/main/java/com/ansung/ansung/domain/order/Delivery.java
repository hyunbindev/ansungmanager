package com.ansung.ansung.domain.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ansung.ansung.domain.Customer;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "delivery")
@Getter
@NoArgsConstructor
public class Delivery {
	@Id
	private String id;
	private String customerTel;
	private String jibunAddresss;
	private String roadAddress;
	private String Lat;
	private String Lng;
	private String remarks;
	List<ProductOrder> products;
	private boolean pending = true;
	@CreatedDate
	private LocalDateTime createdDate;
	
	public static class Builder{
		private String customerTel;
		private String jibunAddresss;
		private String roadAddress;
		private String Lat;
		private String Lng;
		private String remarks;
		List<ProductOrder> products;
		public Builder customer(Customer customer) {
			this.customerTel = customer.getTel();
			this.jibunAddresss = customer.getJibunAddress();
			this.roadAddress = customer.getRoadAddress();
			this.Lat = customer.getLat();
			this.Lng = customer.getLng();
			this.remarks = customer.getRemarks();
			return this;
		}
		public Builder products(List<ProductOrder> products) {
			this.products = products;
			return this;
		}
		public Delivery build() {
			return new Delivery(this);
		}
	}
	
	private Delivery(Builder builder) {
		this.customerTel = builder.customerTel;
		this.jibunAddresss = builder.jibunAddresss;
		this.roadAddress = builder.roadAddress;
		this.Lat = builder.Lat;
		this.Lng = builder.Lng;
		this.remarks = builder.remarks;
		this.products = builder.products;
	}
	public void complete() {
		this.pending = false;
	}
}