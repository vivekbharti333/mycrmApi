package com.spring.object.request;

import lombok.Data;

@Data
public class CurrencyRequestObject {
	
	private String token;
	private Long id;
	
	private String country;
	private String currencyName;
	private String currencyCode;
	private String unicode;
	private String hexCode;
	private String htmlCode;
	private String cssCode;
	
	private int respCode;
	private String respMesg;
	
}
