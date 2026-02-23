package com.whatsapp.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.whatsapp.helper.SendTextMessageHelper;
import com.whatsapp.helper.TemplateMapperHelper;
import com.whatsapp.object.request.TemplateRequestObject;
import com.whatsapp.response.SendWaMessageResponse;

@Service
public class TemplateServices {

	@Autowired
	private TemplateMapperHelper templateMapperHelper;
	
	@Autowired
	private SendTextMessageHelper sendTextMessageHelper;
	
	

	public SendWaMessageResponse sendTextMessage(Request<TemplateRequestObject> templateRequestObject)
			throws BizException, Exception {
		TemplateRequestObject templateRequest = templateRequestObject.getPayload();
		templateMapperHelper.validateTemplateRequest(templateRequest);
		
		String templateParameter = sendTextMessageHelper.getTextTemplateParameter(templateRequest);
		SendWaMessageResponse sendMessageResponse =  sendTextMessageHelper.callSendTemplateTextMessage(templateParameter);

		return sendMessageResponse;
	}

	public List<TemplateRequestObject> getWhatsAppTemplate(Request<TemplateRequestObject> templateRequestObject)
			throws IOException, BizException {
		TemplateRequestObject templateRequest = templateRequestObject.getPayload();
		templateMapperHelper.validateTemplateRequest(templateRequest);

		List<TemplateRequestObject> templateList = new ArrayList<>();
		templateList = templateMapperHelper.getTemplates(templateRequest);
		return templateList;
	}

}
