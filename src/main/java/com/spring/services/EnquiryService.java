package com.spring.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.EnquiryDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.EnquiryHelper;
import com.spring.object.request.EnquiryRequestObject;
import com.spring.object.request.Request;


@Service
public class EnquiryService {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private EnquiryHelper enquiryHelper;

	

	@Transactional
	public EnquiryRequestObject createEnquiry(Request<EnquiryRequestObject> enquiryRequestObject)
			throws BizException, Exception {
		EnquiryRequestObject enquiryRequest = enquiryRequestObject.getPayload();
		enquiryHelper.validateEnquiryRequest(enquiryRequest);

		EnquiryDetails enquiryDetails = enquiryHelper.getEnquiryDetailsByReqObj(enquiryRequest);
		enquiryHelper.saveEnquiryDetails(enquiryDetails);
		
		enquiryRequest.setRespCode(Constant.SUCCESS_CODE);
		enquiryRequest.setRespMesg("Enquiry Submited");
		return enquiryRequest;

	}
		

	public List<EnquiryDetails> getEnquiryList(Request<EnquiryRequestObject> enquiryRequestObject) {
		EnquiryRequestObject enquiryRequest = enquiryRequestObject.getPayload();

		List<EnquiryDetails> enquiryList = new ArrayList<EnquiryDetails>();
		enquiryList = enquiryHelper.getEnquiryList(enquiryRequest);
		return enquiryList;
	}
	
	

}
