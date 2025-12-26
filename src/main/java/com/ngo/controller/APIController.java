package com.ngo.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.ngo.object.request.APIRequestObject;
import com.ngo.services.APIService;
import com.common.object.request.Request;
import com.common.object.response.GenricResponse;
import com.common.object.response.Response;


@CrossOrigin(origins = "*")
@RestController
public class APIController {
	
	@Autowired
	APIService apiService;
	
	
	@RequestMapping(path = "addApiDetails", method = RequestMethod.POST)
	public Response<APIRequestObject>addApiDetails(@RequestBody Request<APIRequestObject> apiRequestObject, HttpServletRequest request)
	{
		GenricResponse<APIRequestObject> responseObj = new GenricResponse<APIRequestObject>();
		try {
			APIRequestObject responce =  apiService.addApiDetails(apiRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
	
}
