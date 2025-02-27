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
import com.spring.entities.DonationType;
import com.spring.entities.DonationTypeAmount;
import com.spring.exceptions.BizException;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.DonationTypeService;


@CrossOrigin(origins = "*")
@RestController
public class DonationTypeController {
	
	@Autowired
	DonationTypeService donationService;
	
	
	@RequestMapping(path = "addDonationType", method = RequestMethod.POST)
	public Response<DonationRequestObject>addDonationType(@RequestBody Request<DonationRequestObject> donationRequestObject)
	{
		GenricResponse<DonationRequestObject> responseObj = new GenricResponse<DonationRequestObject>();
		try {
			DonationRequestObject responce =  donationService.addDonationType(donationRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "updateDonationType", method = RequestMethod.POST)
	public Response<DonationRequestObject>updateDonationType(@RequestBody Request<DonationRequestObject> donationRequestObject)
	{
		GenricResponse<DonationRequestObject> responseObj = new GenricResponse<DonationRequestObject>();
		try {
			DonationRequestObject responce =  donationService.updateDonationType(donationRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "changeDonationTypeStatus", method = RequestMethod.POST)
	public Response<DonationRequestObject>changeDonationTypeStatus(@RequestBody Request<DonationRequestObject> donationRequestObject)
	{
		GenricResponse<DonationRequestObject> responseObj = new GenricResponse<DonationRequestObject>();
		try {
			DonationRequestObject responce =  donationService.changeDonationTypeStatus(donationRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getDonationTypeListBySuperadminId", method = RequestMethod.POST)
	public Response<DonationType> getDonationTypeListBySuperadminId(@RequestBody Request<DonationRequestObject> donationRequestObject) {
		GenricResponse<DonationType> response = new GenricResponse<DonationType>();
		try {
			List<DonationType> donationList = donationService.getDonationTypeListBySuperadminId(donationRequestObject);
			return response.createListResponse(donationList, Constant.SUCCESS_CODE, String.valueOf(donationList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
	@RequestMapping(path = "addDonationTypeAmount", method = RequestMethod.POST)
	public Response<DonationRequestObject>addDonationTypeAmount(@RequestBody Request<DonationRequestObject> donationRequestObject)
	{
		GenricResponse<DonationRequestObject> responseObj = new GenricResponse<DonationRequestObject>();
		try {
			DonationRequestObject responce =  donationService.addDonationTypeAmount(donationRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "updateDonationTypeAmount", method = RequestMethod.POST)
	public Response<DonationRequestObject>updateDonationTypeAmount(@RequestBody Request<DonationRequestObject> donationRequestObject)
	{
		GenricResponse<DonationRequestObject> responseObj = new GenricResponse<DonationRequestObject>();
		try {
			DonationRequestObject responce =  donationService.updateDonationTypeAmount(donationRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getDonationTypeAmountListBySuperadminId", method = RequestMethod.POST)
	public Response<DonationTypeAmount> getDonationTypeAmountListBySuperadminId(@RequestBody Request<DonationRequestObject> donationRequestObject) {
		GenricResponse<DonationTypeAmount> response = new GenricResponse<DonationTypeAmount>();
		try {
			List<DonationTypeAmount> donationTypeAmountList = donationService.getDonationTypeAmountListBySuperadminId(donationRequestObject);
			return response.createListResponse(donationTypeAmountList, Constant.SUCCESS_CODE, String.valueOf(donationTypeAmountList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
}
