package com.spring.services;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.spring.constant.Constant;
import com.spring.entities.AddressDetails;
import com.spring.entities.CustomerDetails;
import com.spring.entities.SmsTemplateDetails;
import com.spring.entities.UserDetails;
import com.spring.enums.RoleType;
import com.spring.exceptions.BizException;
import com.spring.helper.AddressHelper;
import com.spring.helper.SmsTemplateHelper;
import com.spring.helper.UserHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.AddressRequestObject;
import com.spring.object.request.CustomerRequestObject;
import com.spring.object.request.Request;
import com.spring.object.request.SmsTemplateRequestObject;
import com.spring.object.request.UserRequestObject;


@Service
public class SmsTemplateService {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private SmsTemplateHelper smsTemplateHelper;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;



	public SmsTemplateRequestObject addSmsTemplate(Request<SmsTemplateRequestObject> smsTemplateRequestObject)
			throws BizException, Exception {
		SmsTemplateRequestObject smsTemplateRequest = smsTemplateRequestObject.getPayload();
		smsTemplateHelper.validateSmsTemplateRequest(smsTemplateRequest);

//		Boolean isValid = jwtTokenUtil.validateJwtToken(smsTemplateRequest.getCreatedBy(),smsTemplateRequest.getToken());
//		if (isValid) {
			SmsTemplateDetails existsSmsTemplateDetails = smsTemplateHelper.getSmsDetailsBySuperadminIdAndHeaderIdAndSmsType(smsTemplateRequest.getSuperadminId(),smsTemplateRequest.getInvoiceHeaderId(), smsTemplateRequest.getSmsType());
			if (existsSmsTemplateDetails == null) {
				SmsTemplateDetails smsTemplateDetails = smsTemplateHelper.getSmsTemplateDetailsByReqObj(smsTemplateRequest);
				smsTemplateHelper.saveSmsTemplateDetails(smsTemplateDetails);

				smsTemplateRequest.setRespCode(Constant.SUCCESS_CODE);
				smsTemplateRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
				return smsTemplateRequest;
			} else {
				smsTemplateRequest.setRespCode(Constant.ALREADY_EXISTS);
				smsTemplateRequest.setRespMesg(Constant.ALLREADY_EXISTS_MSG);
				return smsTemplateRequest;
			}
//		} else {
//			smsTemplateRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
//			smsTemplateRequest.setRespMesg(Constant.INVALID_TOKEN);
//			return smsTemplateRequest;
//		}
	}
	
	
//	public SmsTemplateRequestObject updateSmsTemplate(Request<SmsTemplateRequestObject> smsTemplateRequestObject)
//			throws BizException, Exception {
//		SmsTemplateRequestObject smsTemplateRequest = smsTemplateRequestObject.getPayload();
//		smsTemplateHelper.validateSmsTemplateRequest(smsTemplateRequest);
//
//		Boolean isValid = jwtTokenUtil.validateJwtToken(smsTemplateRequest.getCreatedBy(),
//				smsTemplateRequest.getToken());
//		if (isValid) {
//			SmsTemplateDetails smsTemplateDetails = smsTemplateHelper.getSmsDetailsBySuperadminId(smsTemplateRequest.getSuperadminId(), smsTemplateRequest.getSmsType());
//			if (smsTemplateDetails != null) {
//				smsTemplateDetails = smsTemplateHelper.getUpdatedSmsTemplateDetailsByReqObj(smsTemplateRequest, smsTemplateDetails);
//				smsTemplateHelper.UpdateSmsTemplateDetails(smsTemplateDetails);
//
//				smsTemplateRequest.setRespCode(Constant.SUCCESS_CODE);
//				smsTemplateRequest.setRespMesg(Constant.UPDATED_SUCCESS);
//				return smsTemplateRequest;
//			} else {
//				smsTemplateRequest.setRespCode(Constant.BAD_REQUEST_CODE);
//				smsTemplateRequest.setRespMesg(Constant.DATA_NOT_FOUND);
//				return smsTemplateRequest;
//			}
//		} else {
//			smsTemplateRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
//			smsTemplateRequest.setRespMesg(Constant.INVALID_TOKEN);
//			return smsTemplateRequest;
//		}
//	}
	
	
	public List<SmsTemplateDetails> getSmsTemplateList(Request<SmsTemplateRequestObject> smsTemplateRequestObject) 
			throws BizException {
		SmsTemplateRequestObject smsTemplateRequest = smsTemplateRequestObject.getPayload();
		smsTemplateHelper.validateSmsTemplateRequest(smsTemplateRequest);
		
		Boolean isValid = jwtTokenUtil.validateJwtToken(smsTemplateRequest.getCreatedBy(), smsTemplateRequest.getToken());
		List<SmsTemplateDetails> smsTemplateList = new ArrayList<SmsTemplateDetails>();
		if (isValid) {
			smsTemplateList = smsTemplateHelper.getSmsTemplateList(smsTemplateRequest);
			return smsTemplateList;
		}
		return smsTemplateList;
	}

	

}
