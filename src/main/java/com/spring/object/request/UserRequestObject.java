package com.spring.object.request;

import java.util.Date;
import java.util.List;

import com.spring.entities.CurrencyMaster;

import lombok.Data;

@Data
public class UserRequestObject {
	
	private Long id;
	private String token;
	private String userPicture;
	private String userCode;
	private String loginId;
	private String password;
	private String status;
	private String roleType;
	private String firstName;
	private String lastName;
	private String mobileNo;
	private String otp;
	private String alternateMobile;
	private String emailId;
	private String aadharNumber;
	private String panNumber;
	
	public List<AddressRequestObject> addressList;
	
	private String service;
	private String permissions;
	
	private String isPassChanged;
	private Date dob;
	private Date validityExpireOn;
	
	//address start
	private String userType;
	private String addressType;
	private String addressLine;
	private String landmark;
	private String district;
	private String city;
	private String state;
	private String country;
	private String pincode; 
	//address end
	
	private Long currencyId;
	
	private CurrencyMaster currencyMaster;
	
	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	private String teamLeaderId;
	private String superadminId;
	
	private String requestedFor;
	
	private String searchParam;
	private int respCode;
	private String respMesg;
	
	

}
