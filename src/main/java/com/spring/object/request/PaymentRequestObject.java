package com.spring.object.request;

import java.util.Date;

import lombok.Data;

@Data
public class PaymentRequestObject {
	
	private Long id;
	private String paymentMode;
	private String paymentModeIds;
	private String status;
	private String superadminId;
	private Date createdAt;
	private String createdBy;
	private String updatedBy;
	
	//Payment gateway
	private String pgProvider;
	private String url;
	private String merchantId;
	private String saltIndex;
	private String saltKey;
	private String response;
	private String responseCode;

	//Payment details
	private String donorName;
	private String mobileNumber;
	private Double amount;
	private String transactionId;
	private String invoiceNumber;

	private String requestedFor;
	
	private int respCode;
	private String respMesg;
	
}
