package com.spring.object.request;

import lombok.Data;

@Data
public class EnquiryRequestObject {
	
	private Long id;
	private String fullName;
	private String mobileNumber;
	private String emailId;
	private String enquiryFor;
	private String status;
	private String ipAddress;
	
	private String requestedFor;
	
	private String token;
	private int respCode;
	private String respMesg;
	
	
	
}
