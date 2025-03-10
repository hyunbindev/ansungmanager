package com.ansung.ansung.data;

import com.ansung.ansung.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductDto {
	private Long id;
	private String name;
	private ProductCategoryDto category;
	private String size;
	private int price;
	public Product toEntity() {
		Product entity = new Product();
		entity.setId(this.id);
		entity.setName(this.name);
		entity.setSize(this.size);
		entity.setPrice(this.price);
		
		if(category != null) {
			entity.setCategory(category.toEntity());
		}
		
		return entity;
	}
	public ProductDto() {}
}
