package com.spring.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "option_type_details")
public class OptionTypeDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "option_type")
	private String OptionType;
	
	@Column(name = "option_for")
	private String optionFor;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "superadmin_id")
	private String superadminId;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOptionType() {
		return OptionType;
	}

	public void setOptionType(String optionType) {
		OptionType = optionType;
	}

	public String getOptionFor() {
		return optionFor;
	}

	public void setOptionFor(String optionFor) {
		this.optionFor = optionFor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getSuperadminId() {
		return superadminId;
	}

	public void setSuperadminId(String superadminId) {
		this.superadminId = superadminId;
	}

	
		
}
