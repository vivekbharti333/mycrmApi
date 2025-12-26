package com.ngo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@Column(name = "whatsapp_url")
	private String whatsappUrl;
	
	@Column(name = "api_key")
	private String apiKey;
	
	@Column(name = "whats_app_number")
	private String whatsAppNumber;
	
	@Column(name = "receipt_download_url")
	private String receiptDownloadUrl;
	
	@Column(name = "status")
	private String status;	
	
	@Column(name = "superadmin_id")
	private String superadminId;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	
}
