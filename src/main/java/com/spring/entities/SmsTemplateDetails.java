package com.spring.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "sms_template_details")
public class SmsTemplateDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	
	@Column(name = "invoice_header_id")
	private Long invoiceHeaderId;
	
	@Column(name = "sms_user_id")
	private String smsUserId;
	
	@Column(name = "sms_password")
	private String smsPassword;
	
	@Column(name = "service")
	private String service;	
	
	@Column(name = "sms_url")
	private String smsUrl;
	
	@Column(name = "sms_sender")
	private String smsSender;
	
	@Column(name = "sms_type")
	private String smsType;
	
	@Column(name = "status")
	private String status;	
	
	@Column(name = "template_id")
	private String templateId;
	
	@Column(name = "entity_id")
	private String entityId;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "invoice_domain")
	private String invoiceDomain;
	
	@Column(name = " company_regards")
	private String companyRegards;
	
	@Column(name = "superadmin_id")
	private String superadminId;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInvoiceHeaderId() {
		return invoiceHeaderId;
	}

	public void setInvoiceHeaderId(Long invoiceHeaderId) {
		this.invoiceHeaderId = invoiceHeaderId;
	}

	public String getSmsUserId() {
		return smsUserId;
	}

	public void setSmsUserId(String smsUserId) {
		this.smsUserId = smsUserId;
	}

	public String getSmsPassword() {
		return smsPassword;
	}

	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSmsUrl() {
		return smsUrl;
	}

	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}

	public String getSmsSender() {
		return smsSender;
	}

	public void setSmsSender(String smsSender) {
		this.smsSender = smsSender;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getInvoiceDomain() {
		return invoiceDomain;
	}

	public void setInvoiceDomain(String invoiceDomain) {
		this.invoiceDomain = invoiceDomain;
	}

	public String getCompanyRegards() {
		return companyRegards;
	}

	public void setCompanyRegards(String companyRegards) {
		this.companyRegards = companyRegards;
	}

	public String getSuperadminId() {
		return superadminId;
	}

	public void setSuperadminId(String superadminId) {
		this.superadminId = superadminId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	
	
}
