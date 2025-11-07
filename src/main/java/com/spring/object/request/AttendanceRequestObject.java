package com.spring.object.request;

import java.util.Date;

import lombok.Data;

@Data
public class AttendanceRequestObject {
	
	private Long id;
	private String token;
	
	private String originalImage;
	private String clickImage;
	private String status;
	private float similarity;
	
	private String punchInStatus;
	private Date punchInDateTime;
	private String punchInLocation;
	
	
	private String punchOutStatus;
	private Date punchOutDateTime;
	private String punchOutLocation;
	
	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	private String superadminId;
	
	private String requestFor;
	
	private int respCode;
	private String respMesg;


}
