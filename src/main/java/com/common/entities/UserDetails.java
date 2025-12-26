package com.common.entities;

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
	
	
}
