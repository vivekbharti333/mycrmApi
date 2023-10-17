package com.spring.services;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.constant.Constant;
import com.spring.entities.DonationType;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationTypeHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.Request;


@Service
public class DonationTypeService {
	
	@Autowired
	private DonationTypeHelper donationTypeHelper;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	@Transactional
	public DonationRequestObject addDonationType(Request<DonationRequestObject> donationRequestObject)
			throws BizException, Exception {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		donationTypeHelper.validateDonationRequest(donationRequest);
		
		Boolean isValid = jwtTokenUtil.validateJwtToken(donationRequest.getCreatedBy(), donationRequest.getToken());
		logger.info("Add Donation. Is valid? : " + donationRequest.getLoginId() + " is " + isValid);

		if (isValid) {
			
			DonationType donationType = donationTypeHelper.getDonationTypeByReqObj(donationRequest);
			donationType = donationTypeHelper.saveDonationType(donationType);

			donationRequest.setRespCode(Constant.SUCCESS_CODE);
			donationRequest.setRespMesg("Successfully Register");
			return donationRequest;
		}else {
			donationRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
			donationRequest.setRespMesg(Constant.INVALID_TOKEN);
			return donationRequest; 
		}
	}


	public List<DonationType> getDonationTypeListBySuperadminId(Request<DonationRequestObject> donationRequestObject) {
		DonationRequestObject donationRequest = donationRequestObject.getPayload();
		Boolean isValid = jwtTokenUtil.validateJwtToken(donationRequest.getCreatedBy(), donationRequest.getToken());

		List<DonationType> donationTypeList = new ArrayList<>();
//		if (isValid) {
			donationTypeList = donationTypeHelper.getDonationTypeListBySuperadminId(donationRequest);
			return donationTypeList;
//		}
//		return donationTypeList;

	}


	
	
	

}

