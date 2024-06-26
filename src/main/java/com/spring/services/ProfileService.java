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
import com.spring.helper.ProfileHelper;
import com.spring.helper.SmsTemplateHelper;
import com.spring.helper.UserHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.AddressRequestObject;
import com.spring.object.request.CustomerRequestObject;
import com.spring.object.request.PaymentRequestObject;
import com.spring.object.request.Request;
import com.spring.object.request.SmsTemplateRequestObject;
import com.spring.object.request.UserRequestObject;


@Service
public class ProfileService {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private ProfileHelper profileHelper;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;



	public PaymentRequestObject addSmsTemplate(Request<PaymentRequestObject> paymentRequestObject)
			throws BizException, Exception {
		PaymentRequestObject PaymentRequest = paymentRequestObject.getPayload();
		profileHelper.validatePaymentRequest(PaymentRequest);
		
		
		return PaymentRequest;
	}
	
	
//	public List<SmsTemplateDetails> getSmsTemplateList(Request<SmsTemplateRequestObject> smsTemplateRequestObject) 
//			throws BizException {
//		SmsTemplateRequestObject smsTemplateRequest = smsTemplateRequestObject.getPayload();
//		smsTemplateHelper.validateSmsTemplateRequest(smsTemplateRequest);
//		
//		Boolean isValid = jwtTokenUtil.validateJwtToken(smsTemplateRequest.getCreatedBy(), smsTemplateRequest.getToken());
//		List<SmsTemplateDetails> smsTemplateList = new ArrayList<SmsTemplateDetails>();
//		if (isValid) {
//			smsTemplateList = smsTemplateHelper.getSmsTemplateList(smsTemplateRequest);
//			return smsTemplateList;
//		}
//		return smsTemplateList;
//	}

	

}
