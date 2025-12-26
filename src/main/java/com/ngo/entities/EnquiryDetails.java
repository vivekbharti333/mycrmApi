package com.ngo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "enquiry_details")
@Data
public class EnquiryDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "enquiry_for")
	private String enquiryFor;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "ip_address")
	private String ipAddress;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	
	
	
}
