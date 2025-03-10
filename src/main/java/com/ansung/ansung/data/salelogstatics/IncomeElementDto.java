package com.ansung.ansung.data.salelogstatics;

import java.time.temporal.Temporal;

import com.ansung.ansung.constant.PayMethodEnum;

import lombok.Getter;

@Getter
public abstract class IncomeElementDto<T extends Temporal>
{
	private T date;
	private int income;
	private PayMethodEnum payMethod;
	public IncomeElementDto(T date , int income,PayMethodEnum payMethod) {
		this.date = date;
		this.income = income;
		this.payMethod = payMethod;
	}
}