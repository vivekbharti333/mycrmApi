package com.whatsapp.helper;

import org.springframework.stereotype.Component;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.whatsapp.object.request.TemplateRequestObject;

@Component
public class WhatsAppTemplateHelper {
	
	public void validateTemplateRequest(TemplateRequestObject templateRequestObject) throws BizException {
		if (templateRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}


	
}