package com.spring.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "donation_type")
public class DonationType {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "program_name")
	private String programName;
	
	@Column(name = "program_amount")
	private Double programAmount;
	
	@Column(name = "status")
	private String status;

	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_At")
	private Date updatedAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "superadmin_id")
	private String superadminId;
	
}
