package com.invoice.entities;

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
@Table(name = "customers_details")
public class CustomersDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "customer_name", length = 200, nullable = false)
    private String customerName;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "gst_number", length = 20)
    private String gstNumber;

    @Column(name = "billing_address", columnDefinition = "TEXT")
    private String billingAddress;
    
    @Column(name = "delivery_address", columnDefinition = "TEXT")
    private String deliveryAddresses;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;
    
    @Column(name = "created_by", updatable = false)
    private Date createdBy;
    
    @Column(name = "superadmin_id", updatable = false)
    private Date superadminId;

}
