package com.spring.object.request;

import java.util.Date;


public class OptionRequestObject {
	
	private Long id;
	private String OptionType;
	private String optionFor;
	private String status;
	private String superadminId;
	private Date createdAt;
	private Date updatedAt;
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
	public String getOptionType() {
		return OptionType;
	}
	public void setOptionType(String optionType) {
		OptionType = optionType;
	}
	public String getOptionFor() {
		return optionFor;
	}
	public void setOptionFor(String optionFor) {
		this.optionFor = optionFor;
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
