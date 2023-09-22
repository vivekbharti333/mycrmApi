package com.spring.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table(name = "attendance_Details")
public class AttendanceDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "punch_in_status")
	private String punchInStatus;
	
	@Lob
	@Column(name = "punch_in_image")
	private String punchInImage;
	
	@Column(name = "punch_in_date_time")
	private Date punchInDateTime;
	
	@Column(name = "punch_in_location")
	private String punchInLocation;
	
	
	@Column(name = "punch_out_status")
	private String punchOutStatus;
	
	@Lob
	@Column(name = "punch_out_image")
	private String punchOutImage;
	
	@Column(name = "punch_out_date_time")
	private Date punchOutDateTime;
	
	@Column(name = "punch_out_location")
	private String punchOutLocation;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "superadmin_id")
	private String superadminId;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getPunchOutDateTime() {
		return punchOutDateTime;
	}

	public void setPunchOutDateTime(Date punchOutDateTime) {
		this.punchOutDateTime = punchOutDateTime;
	}

	public String getPunchOutLocation() {
		return punchOutLocation;
	}

	public void setPunchOutLocation(String punchOutLocation) {
		this.punchOutLocation = punchOutLocation;
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
}
