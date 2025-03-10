package com.ansung.ansung.data.salelogstatics;

import java.util.List;

import lombok.Getter;

@Getter
public class IncomeDto<T extends IncomeElementDto<?>> {
	private List<T> incomes;
	private int cashIncome = 0;
	private int cardIncome = 0;
	private int totalIncome=0;
	public IncomeDto(List<T> incomeList) {
		this.incomes = incomeList;
		for(IncomeElementDto<?> element : incomeList) {
			this.totalIncome += element.getIncome();
			switch(element.getPayMethod()) {
			case PAY_METHOD_CARD:
				cardIncome += element.getIncome();
				break;
			case PAY_METHOD_CASH:
				cashIncome += element.getIncome();
				break;
			default:
				break;
			}
		}
	}
}