package com.ngo.object.request;

import java.util.Date;

import javax.persistence.Column;

import lombok.Data;

@Data
public class DashboardReportObject {
	
	private String fundringingOfficerName;
    private int count;
    private double amount;
    private String currencyCode;

}
