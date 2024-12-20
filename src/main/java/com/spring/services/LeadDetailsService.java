package com.spring.services;

import java.util.ArrayList;
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

	

	@Transactional
	public LeadRequestObject createLead(Request<LeadRequestObject> leadRequestObject)
			throws BizException, Exception {
		LeadRequestObject leadRequest = leadRequestObject.getPayload();
		leaddetailsHelper.validateEnquiryRequest(leadRequest);

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
		

	public List<LeadDetails> getLeadList(Request<LeadRequestObject> leadRequestObject) {
		LeadRequestObject leadRequest = leadRequestObject.getPayload();

		List<LeadDetails> leadList = new ArrayList<LeadDetails>();
		leadList = leaddetailsHelper.getLeadList(leadRequest);
		return leadList;
	}
	
	

}
