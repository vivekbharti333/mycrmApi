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
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.common.object.response.GenricResponse;
import com.common.object.response.Response;
import com.whatsapp.object.request.TemplateRequestObject;
import com.whatsapp.response.SendWaMessageResponse;
import com.whatsapp.services.TemplateServices;


@CrossOrigin(origins = "*")
@RestController
public class WhatsAppTemplateController {
	
	@Autowired
	private TemplateServices templateServices;
	
	@RequestMapping(path = "sendTextMessage", method = RequestMethod.POST)
	public Response<SendWaMessageResponse> sendTextMessage(@RequestBody Request<TemplateRequestObject> templateRequestObject,
			HttpServletRequest request) {
		GenricResponse<SendWaMessageResponse> responseObj = new GenricResponse<SendWaMessageResponse>();
		try {
			SendWaMessageResponse response = templateServices.sendTextMessage(templateRequestObject);
			return responseObj.createSuccessResponse(response, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
    
    @RequestMapping(path = "getWhatsAppTemplate", method = RequestMethod.POST)
	public Response<TemplateRequestObject> getWhatsAppTemplate(@RequestBody Request<TemplateRequestObject> templateRequestObject) {
		GenricResponse<TemplateRequestObject> response = new GenricResponse<TemplateRequestObject>();
		try {
			List<TemplateRequestObject> donationList = templateServices.getWhatsAppTemplate(templateRequestObject);
			return response.createListResponse(donationList, Constant.SUCCESS_CODE, String.valueOf(donationList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
    
   
}