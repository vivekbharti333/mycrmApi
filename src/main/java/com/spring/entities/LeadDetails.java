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
@Table(name = "lead_details")
public class LeadDetails {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "enquiry_for")
	private String enquiryFor;
	
	@Column(name = "enquiry_source")
	private String enquirySource;
	
	@Column(name = "business_type")
	private String businessType;
	
	@Column(name = "customer_first_name")
	private String customerFirstName;
	
	@Column(name = "customer_last_name")
	private String customerLastName;
	
	@Column(name = "customer_mobile")
	private String customerMobile;
	
	@Column(name = "customer_alternate_mobile")
	private String customerAlternateMobile;
	
	@Column(name = "customer_emailid")
	private String customerEmailId;
	
	@Column(name = "customer_city")
	private String customerCity;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "updated_on")
	private Date updatedOn;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "superadmin_id")
	private String superadminId;
	
	@Lob
	@Column(name = "notes")
	private String notes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnquiryFor() {
		return enquiryFor;
	}

	public void setEnquiryFor(String enquiryFor) {
		this.enquiryFor = enquiryFor;
	}

	public String getEnquirySource() {
		return enquirySource;
	}

	public void setEnquirySource(String enquirySource) {
		this.enquirySource = enquirySource;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerAlternateMobile() {
		return customerAlternateMobile;
	}

	public void setCustomerAlternateMobile(String customerAlternateMobile) {
		this.customerAlternateMobile = customerAlternateMobile;
	}

	public String getCustomerEmailId() {
		return customerEmailId;
	}

	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "LeadDetails [id=" + id + ", enquiryFor=" + enquiryFor + ", enquirySource=" + enquirySource
				+ ", businessType=" + businessType + ", customerFirstName=" + customerFirstName + ", customerLastName="
				+ customerLastName + ", customerMobile=" + customerMobile + ", customerAlternateMobile="
				+ customerAlternateMobile + ", customerEmailId=" + customerEmailId + ", customerCity=" + customerCity
				+ ", status=" + status + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", createdBy="
				+ createdBy + ", superadminId=" + superadminId + ", notes=" + notes + "]";
	}
		
}
