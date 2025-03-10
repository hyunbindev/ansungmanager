package com.ansung.ansung.data;

import com.ansung.ansung.domain.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCategoryDto {
	private Long id;
	private String name;
	public ProductCategory toEntity() {
		ProductCategory entity = new ProductCategory();
		entity.setId(id);
		entity.setName(name);
		return entity;
	}
}