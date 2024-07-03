package com.spring.object.request;

import java.util.Date;

import lombok.Data;

@Data
public class SubscriptionRequestObject {
	
	private Long id;
	private String invoiceNo;
	private String packageName;
	private Date fromDate;
	private Date toDate;
	private String status;
	private Date createdAt;
	private String superadminId;
	
}
