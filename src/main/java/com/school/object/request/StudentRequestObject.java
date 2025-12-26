package com.school.object.request;

import java.util.Date;
import lombok.Data;

@Data
public class StudentRequestObject {
	
	private String token;
	private Long id;
	
	private String admissionNo;
	private String rollNumber;
	private String studentPicture;
	private String grade;
	private String gradeSection;
	private String firstName;
	private String middleName;
	private String lastName;
	private String dob;
	private String dobPlace;
	private String gender;
	private String bloodGroup;
	private String nationality;
	private String category;
	private String religion;
	private String aadharNumber;
	private String birthCertificateNumber;
	private String permanentEducationNumber;
	private String eShikshaId;
	private String sessionName;
	private String siblingAdmissionNumber;
	
	/// Parent Details
	private String fatherName;
	private String fatherMobileNo;
	private String motherName;
	private String motherMobileNo;
	
	/// Current Address
	private String currentAddress;
	private String currentCity;
	private String currentState;
	private String currentPin;

	/// Permanent Address
	private String permanentAddress;
	private String permanentCity;
	private String permanentState;
	private String permanentPin;
	
	/// Previous Details
	private String previousSchool;
	private String reasonForChange;
	private String lastClassAttended;

	/// Audit Fields
	private String status;
	private Date createdAt;
	private String createdBy;
	private String createdByName;
    private String superadminId; 
	
	private int respCode;
	private String respMesg;
	
}
