package com.ngo.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.common.jwt.JwtTokenUtil;
import com.common.object.request.Request;
import com.ngo.entities.WhatsAppDetails;
import com.ngo.helper.WhatsAppHelper;
import com.ngo.object.request.WhatsAppRequestObject;


@Service
public class WhatsAppService {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private WhatsAppHelper whatsAppHelper;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;



	public WhatsAppRequestObject addWhatsDetails(Request<WhatsAppRequestObject> whatsAppRequestObject)
			throws BizException, Exception {
		WhatsAppRequestObject whatsAppRequest = whatsAppRequestObject.getPayload();
		whatsAppHelper.validateSmsTemplateRequest(whatsAppRequest);

			WhatsAppDetails existsWhatsApp = whatsAppHelper.getWhatsAppBySuperadminId(whatsAppRequest.getSuperadminId());
			if (existsWhatsApp == null) {
				WhatsAppDetails whatsAppDetails = whatsAppHelper.getWhatsAppDetailsByReqObj(whatsAppRequest);
				whatsAppHelper.saveWhatsAppDetails(whatsAppDetails);

				whatsAppRequest.setRespCode(Constant.SUCCESS_CODE);
				whatsAppRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
				return whatsAppRequest;
				
			} else {
				
				existsWhatsApp = whatsAppHelper.getUpdatedWhatsAppDetailsByReqObj(whatsAppRequest, existsWhatsApp);
				whatsAppHelper.updateWhatsAppDetails(existsWhatsApp);
				
				whatsAppRequest.setRespCode(Constant.SUCCESS_CODE);
				whatsAppRequest.setRespMesg(Constant.UPDATED_SUCCESS);
				return whatsAppRequest;
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
