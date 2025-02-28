package com.spring.model;

import java.util.Date;
import java.util.List;

import com.spring.entities.DonationType;
import com.spring.entities.DonationTypeAmount;

import lombok.Data;

@Data
public class ProgramDetails {
	
	
	private Long id;
	private String programName;
	private Double programAmount;
	private String status;
	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	private String superadminId;
	private List<DonationTypeAmount> donationTypeAmount;

}
