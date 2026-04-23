package com.whatsapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.constant.Constant;
import com.common.entities.WhatsAppMessage;
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.common.object.response.GenricResponse;
import com.common.object.response.Response;
import com.ngo.object.request.WhatsAppRequestObject;
import com.whatsapp.object.request.TemplateRequestObject;
import com.whatsapp.object.request.WhatsAppMessageRequestObject;
import com.whatsapp.response.WhatsAppMessageResponse;
import com.whatsapp.services.TemplateServices;
import com.whatsapp.services.WhatsAppMessageServices;


@CrossOrigin(origins = "*")
@RestController
public class WhatsAppMessageController {
	
	@Autowired
	private WhatsAppMessageServices whatsAppMessageServices;
	
	@RequestMapping(path = "sendTextMessage", method = RequestMethod.POST)
	public Response<WhatsAppMessageResponse> sendTextMessage(@RequestBody Request<WhatsAppMessageRequestObject> whatsAppMessageRequestObject,
			HttpServletRequest request) {
		GenricResponse<WhatsAppMessageResponse> responseObj = new GenricResponse<WhatsAppMessageResponse>();
		try {
			WhatsAppMessageResponse response = whatsAppMessageServices.sendTextMessage(whatsAppMessageRequestObject);
			return responseObj.createSuccessResponse(response, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
    
    @RequestMapping(path = "getWhatsAppMessage", method = RequestMethod.POST)
	public Response<WhatsAppMessage> getWhatsAppMessage(@RequestBody Request<WhatsAppMessageRequestObject> whatsAppMessageRequestObject) {
		GenricResponse<WhatsAppMessage> response = new GenricResponse<WhatsAppMessage>();
		try {
			List<WhatsAppMessage> messageList = whatsAppMessageServices.getWhatsAppMessage(whatsAppMessageRequestObject);
			return response.createListResponse(messageList, Constant.SUCCESS_CODE, String.valueOf(messageList.size()));
		} catch (BizException e) {
			return response.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
    

    
   
}