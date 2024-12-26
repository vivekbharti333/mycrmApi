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
	private String teamLeaderId;
	private String superadminId;
	private String roleType;

	private String requestedFor;
	
	private Long todayWin;
	private Long todayLost;
	private Long todayFollowup;
	private Long todayLead;
	

	private String token;
	private int respCode;
	private String respMesg;

}
