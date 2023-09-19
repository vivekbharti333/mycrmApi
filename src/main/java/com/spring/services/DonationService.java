package com.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.entities.LeadDetails;
import com.spring.enums.RequestFor;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationHelper;
import com.spring.helper.LeadHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.ArticleRequestObject;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.Request;


@Service
public class DonationService {
	
	@Autowired
	private DonationHelper donationHelper;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	@Transactional
	public DonationRequestObject addDonation(Request<DonationRequestObject> donationRequestObject)
			throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationHelper.validateDonationRequest(donationRequest);
		
		Boolean isValid = jwtTokenUtil.validateJwtToken(donationRequest.getLoginId(), donationRequest.getToken());
		logger.info("Add Donation. Is valid? : " + donationRequest.getLoginId() + " is " + isValid);

		if (isValid) {
			DonationDetails leadDetails = donationHelper.getDonationDetailsByReqObj(donationRequest);
			leadDetails = donationHelper.saveDonationDetails(leadDetails);

			// send sms

			donationRequest.setRespCode(Constant.SUCCESS_CODE);
			donationRequest.setRespMesg("Successfully Register");
			return donationRequest;
		}else {
			donationRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
			donationRequest.setRespMesg(Constant.INVALID_TOKEN);
			return donationRequest; 
		}
	}



	public List<DonationDetails> getDonationListBySuperadmin(Request<DonationRequestObject> donationRequestObject) {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		Boolean isValid = jwtTokenUtil.validateJwtToken(donationRequest.getCreatedBy(), donationRequest.getToken());

		List<DonationDetails> donationList = new ArrayList<>();
		if (isValid) {
			donationList = donationHelper.getDonationListBySuperadmin(donationRequest);
			return donationList;
		}
		return donationList;

	}



	public DonationRequestObject getCountAndSum(Request<DonationRequestObject> donationRequestObject) 
			throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();	
		donationHelper.validateDonationRequest(donationRequest);
		
		Boolean isValid = jwtTokenUtil.validateJwtToken(donationRequest.getCreatedBy(), donationRequest.getToken());
		logger.info("Add Donation Is valid? : "+donationRequest.getCreatedBy()+" is " + isValid);

		if (!isValid) {
			
			//for today
			donationRequest.setRequestedFor(RequestFor.TODAY.name());
			
			 donationHelper.getCountAndSum(donationRequest);
//			int count =  donationHelper.getCountAndSum(donationRequest);
			
//			System.out.println("1 : "+count);
			
			//for yesterday
//			donationRequest.setRequestedFor(RequestFor.YESTERDAY.name());
//			Map<String, Long> count1 =  donationHelper.getCountAndSum(donationRequest);
//			
//			donationRequest.setRequestedFor(RequestFor.WEEK.name());
//			Map<String, Long> count2 =  donationHelper.getCountAndSum(donationRequest);
//			
//			donationRequest.setRequestedFor(RequestFor.MONTH.name());
//			Map<String, Long> count3 =  donationHelper.getCountAndSum(donationRequest);
			
//			System.out.println(count);
//			System.out.println(count1);
//			System.out.println(count2);
//			System.out.println(count3);
			
			return donationRequest;
			
		}else {
			return donationRequest;
		}
		
		
	}


	
	

}

