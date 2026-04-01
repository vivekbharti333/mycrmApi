package com.ngo.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.ngo.object.request.WhatsAppRequestObject;
import com.ngo.services.WhatsAppService;

@CrossOrigin(origins = "*")
@RestController
public class WhatsAppController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private WhatsAppService whatsAppService;

	@RequestMapping(path = "addWhatsDetails", method = RequestMethod.POST)
	public Response<WhatsAppRequestObject> addWhatsDetails(@RequestBody Request<WhatsAppRequestObject> whatsAppRequestObject,
			HttpServletRequest request) {
		GenricResponse<WhatsAppRequestObject> responseObj = new GenricResponse<WhatsAppRequestObject>();
		try {
			WhatsAppRequestObject responce = whatsAppService.addWhatsDetails(whatsAppRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
}
