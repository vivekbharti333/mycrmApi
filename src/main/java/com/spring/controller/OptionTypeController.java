package com.spring.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.spring.constant.Constant;
import com.spring.entities.CustomerDetails;
import com.spring.entities.DonationType;
import com.spring.entities.OptionTypeDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.CustomerRequestObject;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.OptionRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.DonationTypeService;
import com.spring.services.OptionTypeService;


@CrossOrigin(origins = "*")
@RestController
public class OptionTypeController {
	
	@Autowired
	OptionTypeService optionTypeService;
	
	
	@RequestMapping(path = "addOptionType", method = RequestMethod.POST)
	public Response<OptionRequestObject>addOptionType(@RequestBody Request<OptionRequestObject> optionRequestObject, HttpServletRequest request)
	{
		GenricResponse<OptionRequestObject> responseObj = new GenricResponse<OptionRequestObject>();
		try {
			OptionRequestObject responce =  optionTypeService.addOptionType(optionRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getOptionTypeDetailsList", method = RequestMethod.POST)
	public Response<OptionTypeDetails> getOptionTypeDetailsList(@RequestBody Request<OptionRequestObject> optionRequestObject) {
		GenricResponse<OptionTypeDetails> response = new GenricResponse<OptionTypeDetails>();
		try {
			List<OptionTypeDetails> optionList = optionTypeService.getOptionTypeDetailsList(optionRequestObject);
			return response.createListResponse(optionList, 200);
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(400, e.getMessage());
		}
	}
	
}
