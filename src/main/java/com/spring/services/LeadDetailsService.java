package com.spring.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.entities.EnquiryDetails;
import com.spring.entities.LeadDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationHelper;
import com.spring.helper.LeaddetailsHelper;
import com.spring.object.request.LeadRequestObject;
import com.spring.object.request.Request;


@Service
public class LeadDetailsService {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private LeaddetailsHelper leaddetailsHelper;
	
	@Autowired
	private DonationHelper donationHelper;
	
	private LocalDate localDate = LocalDate.now();
	private LocalDate nextday = localDate.plus(1, ChronoUnit.DAYS);
	private LocalDate preday = localDate.minus(1, ChronoUnit.DAYS);
	private LocalDate firstDateOfMonth = localDate.withDayOfMonth(1);
	private LocalDate lastDateOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());

	private Date todayDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	private Date tomorrowDate = Date.from(nextday.atStartOfDay(ZoneId.systemDefault()).toInstant());
	private Date previousDate = Date.from(preday.atStartOfDay(ZoneId.systemDefault()).toInstant());
	private Date firstDateMonth = Date.from(firstDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
	private Date lastDateMonth = Date.from(lastDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

	

	@Transactional
	public LeadRequestObject createLead(Request<LeadRequestObject> leadRequestObject)
			throws BizException, Exception {
		LeadRequestObject leadRequest = leadRequestObject.getPayload();
		leaddetailsHelper.validateEnquiryRequest(leadRequest);

		if(leadRequest.getStatus().equals("")) {
			leadRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			leadRequest.setRespMesg("Select Status");
			return leadRequest;
		}
		
		LeadDetails leadDetails = leaddetailsHelper.getLeadDetailsByReqObj(leadRequest);
		leaddetailsHelper.saveLeadDetails(leadDetails);
		
		DonationDetails donationDetails = donationHelper.getDonationDetailsByIdAndSuperadminId( leadRequest.getId(), leadRequest.getSuperadminId());
		if(donationDetails != null) {
			donationDetails.setCalled("YES");
		}
		
		leadRequest.setRespCode(Constant.SUCCESS_CODE);
		leadRequest.setRespMesg("Submited Successfully");
		return leadRequest;

	}
	
	public LeadRequestObject getLeadCountByStatus(Request<LeadRequestObject> leadRequestObject) 
	        throws BizException, Exception {
	    LeadRequestObject leadRequest = leadRequestObject.getPayload();

	    if (leadRequest.getRequestedFor().equals("TODAY")) {
	        Long todayWin = leaddetailsHelper.getLeadCountByStatus(leadRequest, todayDate, tomorrowDate, "WIN");
	        leadRequest.setTodayWin(todayWin);
	        
	        Long todayLost = leaddetailsHelper.getLeadCountByStatus(leadRequest, todayDate, tomorrowDate, "LOST");
	        leadRequest.setTodayLost(todayLost);
	        
	        Long todayFollowup = leaddetailsHelper.getLeadCountByStatus(leadRequest, todayDate, tomorrowDate, "FOLLOWUP");
	        leadRequest.setTodayFollowup(todayFollowup);
	        
	        Long todayLead = leaddetailsHelper.getTotalLeadCount(leadRequest, todayDate, tomorrowDate);
	        leadRequest.setTodayLead(todayLead);
	    }
	    leadRequest.setRespCode(Constant.SUCCESS_CODE);
	    return leadRequest;
	}

		

	public List<LeadDetails> getLeadList(Request<LeadRequestObject> leadRequestObject) {
		LeadRequestObject leadRequest = leadRequestObject.getPayload();

		List<LeadDetails> leadList = new ArrayList<LeadDetails>();
		if(leadRequest.getRequestedFor().equals("TODAY")) {	
			leadList = leaddetailsHelper.getLeadList(leadRequest, todayDate, tomorrowDate);
			return leadList;
			
		} else if(leadRequest.getRequestedFor().equals("YESTERDAY")) {
			leadList = leaddetailsHelper.getLeadList(leadRequest, previousDate, todayDate);
			return leadList;
			
		} else if(leadRequest.getRequestedFor().equals("MONTH")) {
			leadList = leaddetailsHelper.getLeadList(leadRequest,firstDateMonth, lastDateMonth);
			return leadList;
		}
		return leadList; 
		
		
	}
	
	

}
