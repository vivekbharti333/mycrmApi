package com.ngo.object.request;

import java.util.Date;

import lombok.Data;


@Data
public class WhatsAppRequestObject {
	
	private Long id;
	private String whatsappUrl;
	private String apiKey;
	
	private String type;
	private String serviceProvider;
	private String phoneNumberId;
	private String version;
	private String userAccessToken;
	
	private String whatsAppNumber;
	private String receiptDownloadUrl;
	private String status;	
	private String superadminId;
	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	
	private String requestedFor;
	private String token;
	
	private int respCode;
	private String respMesg;
	

}
