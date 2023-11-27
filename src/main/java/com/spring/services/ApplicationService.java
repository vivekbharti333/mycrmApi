package com.spring.services;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.constant.Constant;
import com.spring.entities.ApplicationHeaderDetails;
import com.spring.entities.DonationType;
import com.spring.exceptions.BizException;
import com.spring.helper.ApplicationHelper;
import com.spring.helper.DonationTypeHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.ApplicationRequestObject;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.Request;


@Service
public class ApplicationService {
	
	@Autowired
	private ApplicationHelper applicationHelper;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	@Transactional
	public ApplicationRequestObject addApplicationHeader(Request<ApplicationRequestObject> applicationRequestObject)
			throws BizException, Exception {
		ApplicationRequestObject applicationRequest = applicationRequestObject.getPayload();
		applicationHelper.validateApplicationRequest(applicationRequest);
		
//		Boolean isValid = jwtTokenUtil.validateJwtToken(applicationRequest.getCreatedBy(), donationRequest.getToken());
//		logger.info("Add Donation. Is valid? : " + donationRequest.getLoginId() + " is " + isValid);
//
//		if (isValid) {
		
		ApplicationHeaderDetails applicationHeaderdetails = applicationHelper.getApplicationHeaderDetailsBySuperadminId(applicationRequest.getSuperadminId());
		if(applicationHeaderdetails == null) {
			
			ApplicationHeaderDetails applicationHeader = applicationHelper.getApplicationDetailsByReqObj(applicationRequest);
			applicationHeader = applicationHelper.saveApplicationHeaderDetails(applicationHeader);

			applicationRequest.setRespCode(Constant.SUCCESS_CODE);
			applicationRequest.setRespMesg("Successfully Register");
			return applicationRequest;
		}else {
			applicationHeaderdetails = applicationHelper.getUpdatedApplicationDetailsByReqObj(applicationRequest, applicationHeaderdetails);
			applicationHeaderdetails = applicationHelper.updateApplicationHeaderDetails(applicationHeaderdetails);
			
			applicationRequest.setRespCode(Constant.SUCCESS_CODE);
			applicationRequest.setRespMesg("Update Successfully");
			return applicationRequest;
		}	
	}

	public ApplicationRequestObject getApplicationHeaderDetailsBySuperadminId(Request<ApplicationRequestObject> applicationRequestObject) throws BizException, Exception {
		ApplicationRequestObject applicationRequest = applicationRequestObject.getPayload();
		applicationHelper.validateApplicationRequest(applicationRequest);
		
		ApplicationHeaderDetails applicationHeaderdetails = applicationHelper.getApplicationHeaderDetailsBySuperadminId(applicationRequest.getSuperadminId());
		if(applicationHeaderdetails != null) {
			
			applicationRequest.setDisplayLogo(applicationHeaderdetails.getDisplayLogo());
			applicationRequest.setDisplayName(applicationHeaderdetails.getDisplayName());
			applicationRequest.setWebsite(applicationHeaderdetails.getWebsite());
			applicationRequest.setEmailId(applicationHeaderdetails.getEmailId());
			applicationRequest.setPhoneNumber(applicationHeaderdetails.getPhoneNumber());
			
			applicationRequest.setRespCode(Constant.SUCCESS_CODE);
			applicationRequest.setRespMesg("Successfully");
			return applicationRequest;
		}
		
		return null;
	}
	

	public List<ApplicationHeaderDetails> getApplicationHeaderDetails(Request<ApplicationRequestObject> applicationRequestObject) {
		ApplicationRequestObject applicationRequest = applicationRequestObject.getPayload();
		List<ApplicationHeaderDetails> applicationHeaderDetails = applicationHelper.getApplicationHeaderDetailsBySuperadminId(applicationRequest);
		return applicationHeaderDetails;

	}





	
	
	

}

