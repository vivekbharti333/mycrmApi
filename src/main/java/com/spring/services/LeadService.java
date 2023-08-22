package com.spring.services;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.constant.Constant;
import com.spring.entities.LeadDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.LeadHelper;
import com.spring.object.request.ArticleRequestObject;
import com.spring.object.request.Request;


@Service
public class LeadService {
	
	@Autowired
	private LeadHelper leadHelper;
	
	
	@Transactional
	public ArticleRequestObject regFreshLead(Request<ArticleRequestObject> ArticleRequestObject) 
			throws BizException, Exception {
		ArticleRequestObject leadRequest = ArticleRequestObject.getPayload();
		leadHelper.validateLeadRequest(leadRequest);
		
		LeadDetails existsLeadDetails = leadHelper.getLeadDetailsByCustomerMobile(leadRequest.getCustomerMobile());
		if(existsLeadDetails == null) {
			
			LeadDetails leadDetails = leadHelper.getLeadDetailsByReqObj(leadRequest);
			leadDetails = leadHelper.saveLeadDetails(leadDetails);
			
			//send sms
			
			leadRequest.setRespCode(Constant.SUCCESS_CODE);
			leadRequest.setRespMesg("Successfully Register");
			return leadRequest;
		}else {
			leadRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			leadRequest.setRespMesg("Lead Already Registered");
			return leadRequest;
		}
	}


	public List<LeadDetails> getLeadDetails(Request<ArticleRequestObject> ArticleRequestObject) {
		ArticleRequestObject leadRequest = ArticleRequestObject.getPayload();
		List<LeadDetails> leadList = leadHelper.getLeadDetails(leadRequest);
		return leadList;
	}
	
	

}

