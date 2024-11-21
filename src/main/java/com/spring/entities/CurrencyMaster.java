package com.spring.entities;

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
@Table(name = "currency_master")
public class CurrencyMaster {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "currency_name")
	private String currencyName;
	
	@Column(name = "currency_code")
	private String currencyCode;
	
	@Column(name = "unicode")
	private String unicode;
	
	@Column(name = "hex_code")
	private String hexCode;
	
	@Column(name = "html_code")
	private String htmlCode;
	
	@Column(name = "css_code")
	private String cssCode;
	
	@Column(name = "created_at")
	private Date createdAt;
	
}
