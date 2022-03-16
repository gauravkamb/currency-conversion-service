package com.gaurav.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.gaurav.microservices.currencyconversionservice.CurrencyExchangeServiceProxy;
import com.gaurav.microservices.currencyconversionservice.bean.CurrencyConversionBean;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j	
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean retrieveCalculation(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		Map<String, String> values = new HashMap<>();
		values.put("from", from);
		values.put("to", to);
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class, values);
		CurrencyConversionBean response =  responseEntity.getBody();
		response.setQuantity(quantity.multiply(response.getConversionMultiple()));
		return response;
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);

		log.info("{}", response);
		
		return new CurrencyConversionBean(from, to, response.getConversionMultiple(), quantity);
	}

}
