package com.spring.object.request;

import java.util.Date;
import java.util.List;


public class UserRequestObject {
	
	private Long id;
	private String token;
	private String loginId;
	private String password;
	private String status;
	private String roleType;
	private String firstName;
	private String lastName;
	private String mobileNo;
	private String alternateMobile;
	private String emailId;
	
	public List<AddressRequestObject> addressList;
	
	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	private String superadminId;
	
	private String requestedFor;
	
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
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getAlternateMobile() {
		return alternateMobile;
	}
	public void setAlternateMobile(String alternateMobile) {
		this.alternateMobile = alternateMobile;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public List<AddressRequestObject> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<AddressRequestObject> addressList) {
		this.addressList = addressList;
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
	
	public String getRequestedFor() {
		return requestedFor;
	}
	public void setRequestedFor(String requestedFor) {
		this.requestedFor = requestedFor;
	}
	
	@Override
	public String toString() {
		return "UserRequestObject [id=" + id + ", token=" + token + ", loginId=" + loginId + ", password=" + password
				+ ", status=" + status + ", roleType=" + roleType + ", firstName=" + firstName + ", lastName="
				+ lastName + ", mobileNo=" + mobileNo + ", alternateMobile=" + alternateMobile + ", emailId=" + emailId
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", createdBy=" + createdBy
				+ ", superadminId=" + superadminId + ", requestedFor=" + requestedFor + ", respCode=" + respCode
				+ ", respMesg=" + respMesg + "]";
	}	

}
