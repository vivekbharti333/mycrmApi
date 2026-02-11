package com.invoice.object.request;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ProductRequestObject {

	private Long companyId;
	
    private Long id;
    private String productName;
    private String description;
    private BigDecimal rate;
    private String quantityType;
    private Long quantity;
    
    private Date createdAt;
	private String superadminId;
	private String createdBy;


	private String requestFor;
	private int respCode;
	private String respMesg;
}
