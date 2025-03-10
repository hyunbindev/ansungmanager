package com.ansung.ansung.data.salelogstatics;

import java.time.LocalDateTime;

import com.ansung.ansung.constant.PayMethodEnum;

public class DailyIncomeDto extends IncomeElementDto<LocalDateTime>{
	public DailyIncomeDto(LocalDateTime date, int income,PayMethodEnum payMethod) {
		super(date, income ,payMethod);
	}
}
