package com.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.constant.Constant;
import com.common.entities.UsageLimitConsumption;
import com.common.exceptions.BizException;
import com.ngo.entities.DonationDetails;
import com.ngo.object.request.DonationRequestObject;
import com.common.object.request.Request;
import com.common.object.response.GenricResponse;
import com.common.object.response.Response;
import com.common.services.UsageLimitConsumptionService;


@CrossOrigin(origins = "*")
@RestController
public class UsageLimitConsumptionController {
	
	@Autowired
	private UsageLimitConsumptionService usageLimitConsumptionService;

	
	@RequestMapping(path = "addUsageLimitConsumption", method = RequestMethod.POST)
	public Response<DonationRequestObject>addUsageLimitConsumption(@RequestBody Request<DonationRequestObject> donationRequestObject, HttpServletRequest request)
	{
		GenricResponse<DonationRequestObject> responseObj = new GenricResponse<DonationRequestObject>();
		try {
			DonationRequestObject responce =  usageLimitConsumptionService.addUsageLimitConsumption(donationRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
	
	@RequestMapping(path = "getUsageLimitConsumptionListBySuperadmin", method = RequestMethod.POST)
	public Response<UsageLimitConsumption> getUsageLimitConsumptionListBySuperadmin(@RequestBody Request<DonationRequestObject> donationRequestObject) {
		GenricResponse<UsageLimitConsumption> response = new GenricResponse<UsageLimitConsumption>();
		try {
			List<UsageLimitConsumption> UsageLimitConsumptionList = usageLimitConsumptionService.getUsageLimitConsumptionListBySuperadmin(donationRequestObject);
			return response.createListResponse(UsageLimitConsumptionList, Constant.SUCCESS_CODE, String.valueOf(UsageLimitConsumptionList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	

	
}
