package com.spring.object.request;

import java.util.Date;

public class AttendanceRequestObject {
	
	private Long id;
	private String token;

	private String punchInStatus;
	private String punchInImage;
	private Date punchInDateTime;
	private String punchInLocation;
	
	private String punchOutStatus;
	private String punchOutImage;
	private Date punchOutDateTime;
	private String punchOutLocation;
	
	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	private String superadminId;
	
	private String requestFor;
	
	private int respCode;
	private String respMesg;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPunchInStatus() {
		return punchInStatus;
	}
	public void setPunchInStatus(String punchInStatus) {
		this.punchInStatus = punchInStatus;
	}
	public String getPunchInImage() {
		return punchInImage;
	}
	public void setPunchInImage(String punchInImage) {
		this.punchInImage = punchInImage;
	}
	public Date getPunchInDateTime() {
		return punchInDateTime;
	}
	public void setPunchInDateTime(Date punchInDateTime) {
		this.punchInDateTime = punchInDateTime;
	}
	public Date getPunchOutDateTime() {
		return punchOutDateTime;
	}
	public void setPunchOutDateTime(Date punchOutDateTime) {
		this.punchOutDateTime = punchOutDateTime;
	}
	public String getPunchInLocation() {
		return punchInLocation;
	}
	public void setPunchInLocation(String punchInLocation) {
		this.punchInLocation = punchInLocation;
	}
	public String getPunchOutStatus() {
		return punchOutStatus;
	}
	public void setPunchOutStatus(String punchOutStatus) {
		this.punchOutStatus = punchOutStatus;
	}
	public String getPunchOutImage() {
		return punchOutImage;
	}
	public void setPunchOutImage(String punchOutImage) {
		this.punchOutImage = punchOutImage;
	}
	public String getPunchOutLocation() {
		return punchOutLocation;
	}
	public void setPunchOutLocation(String punchOutLocation) {
		this.punchOutLocation = punchOutLocation;
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
	public String getSuperadminId() {
		return superadminId;
	}
	public void setSuperadminId(String superadminId) {
		this.superadminId = superadminId;
	}
	public String getRequestFor() {
		return requestFor;
	}
	public void setRequestFor(String requestFor) {
		this.requestFor = requestFor;
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
		return "AttendanceRequestObject [id=" + id + ", token=" + token + ", punchInStatus=" + punchInStatus
				+ ", punchInImage=" + punchInImage + ", punchInDateTime=" + punchInDateTime + ", punchInLocation="
				+ punchInLocation + ", punchOutStatus=" + punchOutStatus + ", punchOutImage=" + punchOutImage
				+ ", punchOutDateTime=" + punchOutDateTime + ", punchOutLocation=" + punchOutLocation + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", createdBy=" + createdBy + ", superadminId=" + superadminId
				+ ", requestFor=" + requestFor + ", respCode=" + respCode + ", respMesg=" + respMesg + "]";
	}
	
	

}
