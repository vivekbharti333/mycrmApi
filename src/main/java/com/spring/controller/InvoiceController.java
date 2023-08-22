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
import com.spring.entities.InvoiceDetails;
import com.spring.entities.InvoiceNumber;
import com.spring.exceptions.BizException;
import com.spring.object.request.InvoiceRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.InvoiceService;


@CrossOrigin(origins = "*")
@RestController
public class InvoiceController {
	
	@Autowired
	InvoiceService invoiceService;
	
	
	@RequestMapping(path = "addInvoiceHeader", method = RequestMethod.POST)
	public Response<InvoiceRequestObject>addInvoiceHeader(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject, HttpServletRequest request)
	{
		GenricResponse<InvoiceRequestObject> responseObj = new GenricResponse<InvoiceRequestObject>();
		try {
			InvoiceRequestObject responce =  invoiceService.addInvoiceHeader(invoiceRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	

	@RequestMapping(path = "generateInvoice", method = RequestMethod.POST)
	public Response<InvoiceRequestObject>addCategory(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject, HttpServletRequest request)
	{
		GenricResponse<InvoiceRequestObject> responseObj = new GenricResponse<InvoiceRequestObject>();
		try {
			InvoiceRequestObject responce =  invoiceService.generateInvoice(invoiceRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getInvoiceNumberList", method = RequestMethod.POST)
	public Response<InvoiceNumber> getInvoiceNumber(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject) {
		GenricResponse<InvoiceNumber> response = new GenricResponse<InvoiceNumber>();
		try {
			List<InvoiceNumber> invoiceNumber = invoiceService.getInvoiceNumberList(invoiceRequestObject);
			return response.createListResponse(invoiceNumber, Constant.SUCCESS_CODE);
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getInvoiceDetailsList", method = RequestMethod.POST)
	public Response<InvoiceDetails> getInvoiceDetailsList(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject) {
		GenricResponse<InvoiceDetails> response = new GenricResponse<InvoiceDetails>();
		try {
			List<InvoiceDetails> invoiceDetails = invoiceService.getInvoiceDetailsList(invoiceRequestObject);
			return response.createListResponse(invoiceDetails, Constant.SUCCESS_CODE);
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
}
