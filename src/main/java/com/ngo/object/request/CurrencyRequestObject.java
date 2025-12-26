package com.ngo.object.request;

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
	
	private String currencyMasterIds;
	private String superadminId;
	
	private String requestedFor;
	private int respCode;
	private String respMesg;
	
}
