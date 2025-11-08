package com.spring.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "attendance_Details")
public class AttendanceDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "attendanceDate")
	private LocalDate attendanceDate;
	
	@Lob
	@Column(name = "punch_in_image")
	private String punchInImage;
	
	@Column(name = "punch_in_time")
	private LocalTime punchInTime;
	
	@Column(name = "latitude_in")
	private String latitudeIn;
	
	@Column(name = "longitude_in")
	private String longitudeIn;
	
	@Column(name = "punch_in_location")
	private String punchInLocation;
	
	@Column(name = "punch_in_status")
	private String punchInStatus;
	
	@Lob
	@Column(name = "punch_out_image")
	private String punchOutImage;
	
	@Column(name = "punch_out_time")
	private LocalTime punchOutTime;
	
	@Column(name = "latitude_out")
	private String latitudeOut;
	
	@Column(name = "longitude_out")
	private String longitudeOut;
	
	@Column(name = "punch_out_location")
	private String punchOutLocation;
	
	@Column(name = "punch_out_status")
	private String punchOutStatus;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "superadmin_id")
	private String superadminId;

}
