package com.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.EmailServiceDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.EmailHelper;
import com.spring.object.request.EmailServiceRequestObject;
import com.spring.object.request.Request;

@Service
public class EmailService {

	@Autowired
	private EmailHelper emailHelper;

	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	

	public EmailServiceRequestObject addUpdateEmailServiceDetails(Request<EmailServiceRequestObject> emailServiceRequestObject)
			throws BizException, Exception {
		EmailServiceRequestObject emailServiceRequest = emailServiceRequestObject.getPayload();
		emailHelper.validateEmailServiceRequest(emailServiceRequest);

		EmailServiceDetails existsEmailServiceDetails = emailHelper.getEmailDetailsByEmailTypeAndSuperadinId(emailServiceRequest.getEmailType(), emailServiceRequest.getSuperadminId());
		if (existsEmailServiceDetails == null) {
			EmailServiceDetails emailServiceDetails = emailHelper.getEmailServiceDetailsByReqObj(emailServiceRequest);
			emailServiceDetails = emailHelper.saveEmailServiceDetails(emailServiceDetails);

			emailServiceRequest.setRespCode(Constant.SUCCESS_CODE);
			emailServiceRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
			return emailServiceRequest;
		} else {
			existsEmailServiceDetails = emailHelper.getUpdatedEmailServiceDetailsByReqObj(emailServiceRequest, existsEmailServiceDetails);
			existsEmailServiceDetails = emailHelper.updateEmailServiceDetails(existsEmailServiceDetails);
			
			emailServiceRequest.setRespCode(Constant.ALREADY_EXISTS);
			emailServiceRequest.setRespMesg(Constant.ALLREADY_EXISTS_MSG);
			return emailServiceRequest;
		}
	}
	
	
//	public EmailServiceRequestObject updateEmailServiceDetails(Request<EmailServiceRequestObject> emailServiceRequestObject)
//			throws BizException, Exception {
//		EmailServiceRequestObject emailServiceRequest = emailServiceRequestObject.getPayload();
//		emailHelper.validateEmailServiceRequest(emailServiceRequest);
//
//		EmailServiceDetails emailServiceDetails = emailHelper.getEmailDetailsByEmailTypeAndSuperadinId(emailServiceRequest.getEmailType(), emailServiceRequest.getSuperadminId());
//		if (emailServiceDetails != null) {
//			emailServiceDetails = emailHelper.getUpdatedEmailServiceDetailsByReqObj(emailServiceRequest, emailServiceDetails);
//			emailServiceDetails = emailHelper.updateEmailServiceDetails(emailServiceDetails);
//
//			emailServiceRequest.setRespCode(Constant.SUCCESS_CODE);
//			emailServiceRequest.setRespMesg(Constant.UPDATED_SUCCESS);
//			return emailServiceRequest;
//		} else {
//			emailServiceRequest.setRespCode(Constant.BAD_REQUEST_CODE);
//			emailServiceRequest.setRespMesg("Not Exists");
//			return emailServiceRequest;
//		}
//	}

	public List<EmailServiceDetails> getEmailServiceDetailsList(Request<EmailServiceRequestObject> paymentModeRequestObject) {
		EmailServiceRequestObject paymentModeRequest = paymentModeRequestObject.getPayload();

		List<EmailServiceDetails> emailServiceDetailsList = new ArrayList<>();
		emailServiceDetailsList = emailHelper.getEmailServiceDetailsList(paymentModeRequest);
		return emailServiceDetailsList;
	}
	
	



}
