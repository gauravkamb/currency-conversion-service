package com.gaurav.microservices.currencyconversionservice.bean;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversionBean {
	
	private String from;
	private String to;
	private BigDecimal conversionMultiple;
	private BigDecimal quantity;

}
