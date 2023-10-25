package com.spring.object.request;

import java.util.Date;


public class SmsTemplateRequestObject {
	
	private Long id;
	private String smsUserId;
	private String smsPassword;
	private String service;	
	private String smsUrl;
	private String smsSender;
	private String smsType;
	private String status;
	private String templateId;
	private String entityId;
	private String companyName;
	private String invoiceDomain;
	private String companyRegards;

	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	private String updatedBy;
	private String superadminId;
	
	private String requestedFor;
	private String token;
	
	private int respCode;
	private String respMesg;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getSuperadminId() {
		return superadminId;
	}
	public void setSuperadminId(String superadminId) {
		this.superadminId = superadminId;
	}
	public String getRequestedFor() {
		return requestedFor;
	}
	public void setRequestedFor(String requestedFor) {
		this.requestedFor = requestedFor;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getRespCode() {
		return respCode;
	}
	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}
	public String getRespMesg() {
		return respMesg;
	}
	public void setRespMesg(String respMesg) {
		this.respMesg = respMesg;
	}
	

}
