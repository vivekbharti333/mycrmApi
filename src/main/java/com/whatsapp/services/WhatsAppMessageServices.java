package com.whatsapp.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.entities.WhatsAppMessage;
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.ngo.object.request.WhatsAppRequestObject;
import com.whatsapp.helper.SendTextMessageHelper;
import com.whatsapp.helper.TemplateMapperHelper;
import com.whatsapp.helper.WhatsAppMessageHelper;
import com.whatsapp.object.request.WhatsAppMessageRequestObject;
import com.whatsapp.response.WhatsAppMessageResponse;

@Service
public class WhatsAppMessageServices {

	@Autowired
	private TemplateMapperHelper templateMapperHelper;
	
	@Autowired
	private SendTextMessageHelper sendTextMessageHelper;
	
	@Autowired 
	private WhatsAppMessageHelper whatsAppMessageHelper;
	
	

	public WhatsAppMessageResponse sendTextMessage(Request<WhatsAppMessageRequestObject> WhatsAppMessageRequestObject)
			throws BizException, Exception {
		WhatsAppMessageRequestObject whatsAppRequest = WhatsAppMessageRequestObject.getPayload();
		whatsAppMessageHelper.validateWhatsAppMessageRequest(whatsAppRequest);
		
		String templateParameter = sendTextMessageHelper.getTextTemplateParameter(whatsAppRequest);
		WhatsAppMessageResponse sendMessageResponse =  sendTextMessageHelper.callSendTemplateTextMessage(templateParameter);

		return sendMessageResponse;
	}


	public List<WhatsAppMessage> getWhatsAppMessage(Request<WhatsAppMessageRequestObject> whatsAppMessageRequestObject) 
		throws IOException, BizException {
		WhatsAppMessageRequestObject whatsAppMessageRequest = whatsAppMessageRequestObject.getPayload();
		whatsAppMessageHelper.validateWhatsAppMessageRequest(whatsAppMessageRequest);

		List<WhatsAppMessage> messageList = new ArrayList<>();
		messageList = whatsAppMessageHelper.getWhatsAppMessage(whatsAppMessageRequest);
		return messageList;
	}

}
