package com.school.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "fee_structure")
public class FeeStructure {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
//	private 
//	fee_structure_id  BIGINT PK
//	org_id            BIGINT
//	class_id          BIGINT
//	academic_year     VARCHAR(9)
//	total_amount      DECIMAL(10,2)
//	is_active         BOOLEAN
	
	
	}
