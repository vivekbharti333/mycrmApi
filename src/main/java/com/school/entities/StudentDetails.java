package com.school.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "student_details")
public class StudentDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "admission_no")
	private String admissionNo;

	@Column(name = "roll_number")
	private String rollNumber;

	@Column(name = "student_picture")
	private String studentPicture;

	@Column(name = "grade")
	private String grade;

	@Column(name = "grade_section")
	private String gradeSection;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "dob")
	private String dob;

	@Column(name = "dob_place")
	private String dobPlace;

	@Column(name = "gender")
	private String gender;

	@Column(name = "blood_group")
	private String bloodGroup;

	@Column(name = "nationality")
	private String nationality;

	@Column(name = "category")
	private String category;

	@Column(name = "religion")
	private String religion;

	@Column(name = "aadhar_number")
	private String aadharNumber;

	@Column(name = "birth_certificate_number")
	private String birthCertificateNumber;

	@Column(name = "permanent_education_number")
	private String permanentEducationNumber;

	@Column(name = "eshiksha_id")
	private String eShikshaId;

	@Column(name = "session_name")
	private String sessionName;

	@Column(name = "sibling_admission_number")
	private String siblingAdmissionNumber;


	/// Parent Details
	@Column(name = "father_name")
	private String fatherName;

	@Column(name = "father_mobile_no")
	private String fatherMobileNo;

	@Column(name = "mother_name")
	private String motherName;

	@Column(name = "mother_mobile_no")
	private String motherMobileNo;


	/// Current Address
	@Column(name = "current_address")
	private String currentAddress;

	@Column(name = "current_city")
	private String currentCity;

	@Column(name = "current_state")
	private String currentState;

	@Column(name = "current_pin")
	private String currentPin;


	/// Permanent Address
	@Column(name = "permanent_address")
	private String permanentAddress;

	@Column(name = "permanent_city")
	private String permanentCity;

	@Column(name = "permanent_state")
	private String permanentState;

	@Column(name = "permanent_pin")
	private String permanentPin;


	/// Previous Details
	@Column(name = "previous_school")
	private String previousSchool;

	@Column(name = "reason_for_change")
	private String reasonForChange;

	@Column(name = "last_class_attended")
	private String lastClassAttended;


	/// Audit Fields
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_by_name")
	private String createdByName;

	@Column(name = "superadmin_id")
	private String superadminId;


}
