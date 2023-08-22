package com.spring.object.request;

import java.util.Date;

public class LeadRequestObject {
	
	private String sessionId;
	private String enquiryFor;
	private String enquirySource;
	private String businessType;
	private String customerFirstName;
	private String customerLastName;
	private String customerMobile;
	private String customerAlternateMobile;
	private String customerEmailId;
	private String customerCity;
	private String status;
	private Date createdOn;
	private Date updatedOn;
	private String createdBy;
	private String superadminId;
	private String notes;
	
	private int respCode;
	private String respMesg;
	
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getEnquiryFor() {
		return enquiryFor;
	}
	public void setEnquiryFor(String enquiryFor) {
		this.enquiryFor = enquiryFor;
	}
	public String getEnquirySource() {
		return enquirySource;
	}
	public void setEnquirySource(String enquirySource) {
		this.enquirySource = enquirySource;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getCustomerFirstName() {
		return customerFirstName;
	}
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}
	public String getCustomerLastName() {
		return customerLastName;
	}
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public String getCustomerAlternateMobile() {
		return customerAlternateMobile;
	}
	public void setCustomerAlternateMobile(String customerAlternateMobile) {
		this.customerAlternateMobile = customerAlternateMobile;
	}
	public String getCustomerEmailId() {
		return customerEmailId;
	}
	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}
	public String getCustomerCity() {
		return customerCity;
	}
	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getSuperadminId() {
		return superadminId;
	}
	public void setSuperadminId(String superadminId) {
		this.superadminId = superadminId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
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
	
	
	@Override
	public String toString() {
		return "LeadRequestObject [sessionId=" + sessionId + ", enquiryFor=" + enquiryFor + ", enquirySource="
				+ enquirySource + ", businessType=" + businessType + ", customerFirstName=" + customerFirstName
				+ ", customerLastName=" + customerLastName + ", customerMobile=" + customerMobile
				+ ", customerAlternateMobile=" + customerAlternateMobile + ", customerEmailId=" + customerEmailId
				+ ", customerCity=" + customerCity + ", status=" + status + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + ", createdBy=" + createdBy + ", superadminId=" + superadminId + ", notes=" + notes
				+ ", respCode=" + respCode + ", respMesg=" + respMesg + "]";
	}
	
}
