package com.spring.object.request;

import java.util.Date;

import lombok.Data;

@Data
public class CashfreeRequestObject {
	
	private Long id;
	private String linkId;
	private String link_status;
	
	
	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	private String superadminId;
	
	private int respCode;
	private String respMesg;
	
	

}
