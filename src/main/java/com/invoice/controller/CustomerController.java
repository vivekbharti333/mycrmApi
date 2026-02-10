package com.invoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.constant.Constant;
import com.common.entities.UserDetails;
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.common.object.request.UserRequestObject;
import com.common.object.response.GenricResponse;
import com.common.object.response.Response;
import com.invoice.entities.CustomersDetails;
import com.invoice.object.request.InvoiceRequestObject;
import com.invoice.services.CustomerService;

@CrossOrigin(origins = { "*" })
@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(path = "addCustomerDetails", method = RequestMethod.POST)
	public Response<InvoiceRequestObject> addCustomerDetails(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject) {
	    GenricResponse<InvoiceRequestObject> responseObj = new GenricResponse<>();
	    try {
	        InvoiceRequestObject response = customerService.addCustomerDetails(invoiceRequestObject);
	        return responseObj.createSuccessResponse(response, 200);
	    } catch (BizException e) {
	        return responseObj.createErrorResponse(400, e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        return responseObj.createErrorResponse(500, "Internal Server Error");
	    }
	}
	
	@RequestMapping(path = "getCustomerDetails", method = RequestMethod.POST)
	public Response<CustomersDetails> getCustomerDetails(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject) {
		GenricResponse<CustomersDetails> response = new GenricResponse<CustomersDetails>();
		try {
			List<CustomersDetails> customerList = customerService.getCustomerDetails(invoiceRequestObject);
			return response.createListResponse(customerList, 200, String.valueOf(customerList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		}
	}

}
