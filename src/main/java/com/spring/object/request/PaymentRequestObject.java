package com.spring.object.request;

import java.util.Date;


public class PaymentRequestObject {
	
	private Long id;
	private String paymentMode;
	private String paymentModeIds;
	private String status;
	private String superadminId;
	private Date createdAt;
	private String createdBy;
	private String updatedBy;
	
	private String requestedFor;
	
	private int respCode;
	private String respMesg;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getPaymentModeIds() {
		return paymentModeIds;
	}
	public void setPaymentModeIds(String paymentModeIds) {
		this.paymentModeIds = paymentModeIds;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getRequestedFor() {
		return requestedFor;
	}
	public void setRequestedFor(String requestedFor) {
		this.requestedFor = requestedFor;
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
