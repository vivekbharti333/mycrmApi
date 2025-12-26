package com.ngo.object.request;

import java.util.Date;

import lombok.Data;

@Data
public class APIRequestObject {
	
	private Long id;
	private String serviceProvider;
	private String serviceFor;
	private String userName;
	private String apiKeys;
	private String apiValue;
	
	private Date createdAt;
	
	private String requestFor;
	
	private int respCode;
	private String respMesg;


}
