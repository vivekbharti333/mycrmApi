package com.ngo.entities;

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
@Table(name = "whats_app_details")
public class WhatsAppDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "service_provider")
	private String serviceProvider;
	
	@Column(name = "whats_app_number")
	private String whatsAppNumber;
	
	@Column(name = "receipt_download_url")
	private String receiptDownloadUrl;
	
	//Start new field
	@Column(name = "phone_number_id")
	private String phoneNumberId;
	
	@Column(name = "version")
	private String version;
	
	@Lob
	@Column(name = "user_access_token")
	private String userAccessToken;
	//End
	
	@Column(name = "status")
	private String status;	
	
	@Column(name = "superadmin_id")
	private String superadminId;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	
}
