package com.ngo.entities;

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
@Table(name = "lead_details")
public class LeadDetails {
	

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "donor_name")
	private String donorName;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	@Column(name = "emailId")
	private String emailId;
	
	@Column(name = "status")
	private String status;
	
	@Lob
	@Column(name = "notes")
	private String notes;
	
	@Column(name = "followup_date")
	private Date followupDate;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_At")
	private Date updatedAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "createdby_name")
	private String createdbyName;
	
	@Column(name = "team_leader_id")
	private String teamLeaderId;
	
	@Column(name = "superadmin_id")
	private String superadminId;

	

}
