package com.ngo.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.ngo.entities.WhatsAppDetails;
import com.ngo.helper.WhatsAppHelper;
import com.ngo.object.request.WhatsAppRequestObject;


@Service
public class WhatsAppService {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private WhatsAppHelper whatsAppHelper;


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

	}
	
	
	

}
