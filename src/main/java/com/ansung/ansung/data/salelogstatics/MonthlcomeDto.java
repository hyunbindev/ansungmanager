package com.ansung.ansung.data.salelogstatics;

import java.time.LocalDate;

import com.ansung.ansung.constant.PayMethodEnum;

public class MonthlcomeDto extends IncomeElementDto<LocalDate>{
	public MonthlcomeDto(LocalDate date, int income ,PayMethodEnum payMethod) {
		super(date, income,payMethod);
	}
}