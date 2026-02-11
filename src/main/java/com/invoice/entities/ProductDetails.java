package com.invoice.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "product_details")
public class ProductDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", length = 200, nullable = false)
    private String productName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal rate;
    
    @Column(name = "quantityType", precision = 10, scale = 2, nullable = false)
    private String quantityType;
    
    @Column(name = "quantity", precision = 10, scale = 2, nullable = false)
    private Long quantity;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;
    
    @Column(name = "created_by", updatable = false)
    private String createdBy;
    
    @Column(name = "superadmin_id", updatable = false)
    private String superadminId;
}
