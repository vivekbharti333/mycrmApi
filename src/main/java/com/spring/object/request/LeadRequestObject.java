package com.spring.object.request;

import java.util.Date;

import javax.persistence.Column;

import lombok.Data;

@Data
public class LeadRequestObject {

	private Long id;
	private String donorName;
	private String mobileNumber;
	private String emailId;
	private String status;
	private String notes;
	private Date followupDate;
	private String createdBy;
	private String createdbyName;
	private String superadminId;

	private String requestedFor;

	private String token;
	private int respCode;
	private String respMesg;

}
