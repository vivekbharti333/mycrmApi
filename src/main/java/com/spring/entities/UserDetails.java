package com.spring.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import lombok.Data;

@Data
@Entity
@Table(name = "user_details")
public class UserDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "secret_key")
	private String secretKey;
	
	@Lob
	@Column(name = "user_picture")
	private String userPicture;
	
	@Column(name = "user_code")
	private String userCode;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@NonNull
	@Length(min = 5, max = 20)
	@Column(name = "login_id")
	private String loginId;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "service")
	private String service;
	
	@Column(name = "permissions")
	private String permissions;
	
	@Column(name = "role_type")
	private String roleType;
	
	@Column(name = "mobile_no")
	private String mobileNo;
	
	@Column(name = "alternate_mobile")
	private String alternateMobile;
	
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "aadhar_number")
	private String aadharNumber;
	
	@Column(name = "pan_number")
	private String panNumber;
	
	@Column(name = "dob")
	private Date dob;
	
	@Column(name = "is_pass_changed")
	private String isPassChanged;
	
	@Column(name = "validity_expire_on")
	private Date validityExpireOn;
	
	@Column(name = "currency_id")
	private Long currencyId;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_At")
	private Date updatedAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "superadmin_id")
	private String superadminId;
	
	
	public UserDetails() {
        
    }
	
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getSecretKey() {
//		return secretKey;
//	}
//
//	public void setSecretKey(String secretKey) {
//		this.secretKey = secretKey;
//	}
//
//	public String getUserPicture() {
//		return userPicture;
//	}
//
//	public void setUserPicture(String userPicture) {
//		this.userPicture = userPicture;
//	}
//
//	public String getUserCode() {
//		return userCode;
//	}
//
//	public void setUserCode(String userCode) {
//		this.userCode = userCode;
//	}
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//	public String getLoginId() {
//		return loginId;
//	}
//
//	public void setLoginId(String loginId) {
//		this.loginId = loginId;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public String getService() {
//		return service;
//	}
//
//	public void setService(String service) {
//		this.service = service;
//	}
//
//	public String getPermissions() {
//		return permissions;
//	}
//
//	public void setPermissions(String permissions) {
//		this.permissions = permissions;
//	}
//
//	public Date getValidityExpireOn() {
//		return validityExpireOn;
//	}
//
//	public void setValidityExpireOn(Date validityExpireOn) {
//		this.validityExpireOn = validityExpireOn;
//	}
//
//	public String getRoleType() {
//		return roleType;
//	}
//
//	public void setRoleType(String roleType) {
//		this.roleType = roleType;
//	}
//
//	public String getMobileNo() {
//		return mobileNo;
//	}
//
//	public void setMobileNo(String mobileNo) {
//		this.mobileNo = mobileNo;
//	}
//
//	public String getAlternateMobile() {
//		return alternateMobile;
//	}
//
//	public void setAlternateMobile(String alternateMobile) {
//		this.alternateMobile = alternateMobile;
//	}
//
//	public String getEmailId() {
//		return emailId;
//	}
//
//	public void setEmailId(String emailId) {
//		this.emailId = emailId;
//	}
//
//	public String getAadharNumber() {
//		return aadharNumber;
//	}
//
//	public void setAadharNumber(String aadharNumber) {
//		this.aadharNumber = aadharNumber;
//	}
//
//	public String getPanNumber() {
//		return panNumber;
//	}
//
//	public void setPanNumber(String panNumber) {
//		this.panNumber = panNumber;
//	}
//
//	public Date getDob() {
//		return dob;
//	}
//
//	public void setDob(Date dob) {
//		this.dob = dob;
//	}
//
//	public String getIsPassChanged() {
//		return isPassChanged;
//	}
//
//	public void setIsPassChanged(String isPassChanged) {
//		this.isPassChanged = isPassChanged;
//	}
//
//	public Date getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(Date createdAt) {
//		this.createdAt = createdAt;
//	}
//
//	public Date getUpdatedAt() {
//		return updatedAt;
//	}
//
//	public void setUpdatedAt(Date updatedAt) {
//		this.updatedAt = updatedAt;
//	}
//
//	public String getCreatedBy() {
//		return createdBy;
//	}
//
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}
//
//	public String getSuperadminId() {
//		return superadminId;
//	}
//
//	public void setSuperadminId(String superadminId) {
//		this.superadminId = superadminId;
//	}
//
//	@Override
//	public String toString() {
//		return "UserDetails [id=" + id + ", secretKey=" + secretKey + ", userPicture=" + userPicture + ", firstName="
//				+ firstName + ", lastName=" + lastName + ", loginId=" + loginId + ", password=" + password + ", status="
//				+ status + ", roleType=" + roleType + ", mobileNo=" + mobileNo + ", alternateMobile=" + alternateMobile
//				+ ", emailId=" + emailId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", createdBy="
//				+ createdBy + ", superadminId=" + superadminId + "]";
//	}

	
}
