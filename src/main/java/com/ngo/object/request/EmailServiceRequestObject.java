package com.ngo.object.request;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Lob;

import lombok.Data;

@Data
public class EmailServiceRequestObject {
	
	private Long id;
	private String emailType;
	private String status;	
	private String host;
	private String port;
	private String emailUserid;
	private String emailPassword;
	private String emailFrom;
	private String subject;
	private String emailBody;
	private String regards;
	private String website;
	private String serviceProvider;

	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	private String updatedBy;
	private String superadminId;
	
	private String requestedFor;
	private String token;
	
	private int respCode;
	private String respMesg;
	
	

}
