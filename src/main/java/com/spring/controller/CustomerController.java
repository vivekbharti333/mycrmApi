package com.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.constant.Constant;
import com.spring.entities.CustomerDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.CustomerRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.CustomerService;

@CrossOrigin(origins = "*")
@RestController
public class CustomerController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	CustomerService customerService;

	@RequestMapping(path = "customerRegistration", method = RequestMethod.POST)
	public Response<CustomerRequestObject> customerRegistration(@RequestBody Request<CustomerRequestObject> customerRequestObject,
			HttpServletRequest request) {
		GenricResponse<CustomerRequestObject> responseObj = new GenricResponse<CustomerRequestObject>();
		try {
			CustomerRequestObject responce = customerService.customerRegistration(customerRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}



	@RequestMapping(path = "getCustomerList", method = RequestMethod.POST)
	public Response<CustomerDetails> getCustomerList(@RequestBody Request<CustomerRequestObject> customerRequestObject) {
		GenricResponse<CustomerDetails> response = new GenricResponse<CustomerDetails>();
		try {
			List<CustomerDetails> customerList = customerService.getCustomerList(customerRequestObject);
			return response.createListResponse(customerList, 200, String.valueOf(customerList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(400, e.getMessage());
		}
	}
}
