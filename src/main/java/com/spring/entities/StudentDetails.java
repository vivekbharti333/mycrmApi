package com.spring.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "student_details")
public class StudentDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	private String studentId;
	private String password;
	private String studentFirstName;
	private String studentLastName;
	private String studentSTD;
	private String studentFatherName;
	private String studentMotherName;
	private String studentWhatsAppNumber;
	private String studentAlternateNumber;
	private String studentEmailId;
	
	private String academyId;
	private String createdBy;
	private String createdAt;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStudentFirstName() {
		return studentFirstName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	public String getStudentLastName() {
		return studentLastName;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	public String getStudentSTD() {
		return studentSTD;
	}
	public void setStudentSTD(String studentSTD) {
		this.studentSTD = studentSTD;
	}
	public String getStudentFatherName() {
		return studentFatherName;
	}
	public void setStudentFatherName(String studentFatherName) {
		this.studentFatherName = studentFatherName;
	}
	public String getStudentMotherName() {
		return studentMotherName;
	}
	public void setStudentMotherName(String studentMotherName) {
		this.studentMotherName = studentMotherName;
	}
	public String getStudentWhatsAppNumber() {
		return studentWhatsAppNumber;
	}
	public void setStudentWhatsAppNumber(String studentWhatsAppNumber) {
		this.studentWhatsAppNumber = studentWhatsAppNumber;
	}
	public String getStudentAlternateNumber() {
		return studentAlternateNumber;
	}
	public void setStudentAlternateNumber(String studentAlternateNumber) {
		this.studentAlternateNumber = studentAlternateNumber;
	}
	public String getStudentEmailId() {
		return studentEmailId;
	}
	public void setStudentEmailId(String studentEmailId) {
		this.studentEmailId = studentEmailId;
	}
	public String getAcademyId() {
		return academyId;
	}
	public void setAcademyId(String academyId) {
		this.academyId = academyId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
			
}
