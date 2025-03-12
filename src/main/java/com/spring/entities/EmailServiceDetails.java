package com.spring.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "email_service_details")
public class EmailServiceDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "email_type")
	private String emailType;
	
	@Column(name = "status")
	private String status;	
	
	@Column(name = "host")
	private String host;
	
	@Column(name = "port")
	private String port;
	
	@Column(name = "email_userid")
	private String emailUserid;
	
	@Column(name = "email_password")
	private String emailPassword;
	
	@Column(name = "email_from")
	private String emailFrom;
	
	@Column(name = "subject")
	private String subject;
	
	@Column(name = "receipt_url")
	private String receiptUrl;
	
	@Lob
	@Column(name = "email_body")
	private String emailBody;
	
	@Column(name = "regards")
	private String regards;
	
	@Column(name = "website")
	private String website;
	
	@Column(name = "service_provider")
	private String serviceProvider;
	
	@Column(name = "superadmin_id")
	private String superadminId;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
}
