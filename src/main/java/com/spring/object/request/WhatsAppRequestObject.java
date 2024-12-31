package com.spring.object.request;

import java.util.Date;

import lombok.Data;


@Data
public class WhatsAppRequestObject {
	
	private Long id;
	private String whatsappUrl;
	private String apiKey;
	private String whatsAppNumber;
	private String status;	
	private String superadminId;
	private Date createdAt;
	private Date updatedAt;
	private String updatedBy;
	
	private String requestedFor;
	private String token;
	
	private int respCode;
	private String respMesg;
	

}
