package com.school.object.request;

import lombok.Data;

@Data
public class SchoolReceiptDetailsRequest {
	
	private String feeType;   // Tuition, Transport, Exam, etc.
    private Double amount;
}

