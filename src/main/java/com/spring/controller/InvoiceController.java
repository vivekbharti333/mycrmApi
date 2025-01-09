package com.spring.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.spring.common.PdfInvoice;
import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.entities.InvoiceDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.entities.InvoiceNumber;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.helper.DonationHelper;
import com.spring.helper.InvoiceHelper;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.InvoiceRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.DonationService;
import com.spring.services.InvoiceService;


@CrossOrigin(origins = "*")
@RestController
public class InvoiceController {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private PdfInvoice pdfInvoice;
	
	@Autowired
	private DonationHelper donationHelper;
	
	@Autowired
	private InvoiceHelper invoiceHelper;
	
	
//	@RequestMapping(path = "addInvoiceHeaderType", method = RequestMethod.POST)
//	public Response<InvoiceRequestObject>addInvoiceHeaderType(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject, HttpServletRequest request)
//	{
//		GenricResponse<InvoiceRequestObject> responseObj = new GenricResponse<InvoiceRequestObject>();
//		try {
//			InvoiceRequestObject responce =  invoiceService.addInvoiceHeaderType(invoiceRequestObject);
//			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
//		}catch (BizException e) {
//			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
//		} 
// 		catch (Exception e) {e.printStackTrace();
//			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
//		}
//	}
//	
//	@RequestMapping(path = "getInvoiceHeaderTypeList", method = RequestMethod.POST)
//	public Response<InvoiceHeaderType> getInvoiceHeaderTypeList(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject) {
//		GenricResponse<InvoiceHeaderType> response = new GenricResponse<InvoiceHeaderType>();
//		try {
//			List<InvoiceHeaderType> invoiceDetails = invoiceService.getInvoiceHeaderTypeList(invoiceRequestObject);
//			return response.createListResponse(invoiceDetails, Constant.SUCCESS_CODE);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
//		}
//	}
	
	
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
	
//	@RequestMapping(path = "updateInvoiceHeader", method = RequestMethod.POST)
//	public Response<InvoiceRequestObject>updateInvoiceHeader(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject, HttpServletRequest request)
//	{
//		GenricResponse<InvoiceRequestObject> responseObj = new GenricResponse<InvoiceRequestObject>();
//		try {
//			InvoiceRequestObject responce =  invoiceService.updateInvoiceHeader(invoiceRequestObject);
//			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
//		}catch (BizException e) {
//			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
//		} 
// 		catch (Exception e) {e.printStackTrace();
//			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
//		}
//	}
	
	@RequestMapping(path = "getInvoiceHeaderList", method = RequestMethod.POST)
	public Response<InvoiceHeaderDetails> getInvoiceHeaderList(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject) {
		GenricResponse<InvoiceHeaderDetails> response = new GenricResponse<InvoiceHeaderDetails>();
		try {
			List<InvoiceHeaderDetails> invoiceDetails = invoiceService.getInvoiceHeaderList(invoiceRequestObject);
			return response.createListResponse(invoiceDetails, Constant.SUCCESS_CODE, String.valueOf(invoiceDetails.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	

	@RequestMapping(path = "generateInvoice", method = RequestMethod.POST)
	public Response<InvoiceRequestObject>addCategory(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject, HttpServletRequest request)
	{
		GenricResponse<InvoiceRequestObject> responseObj = new GenricResponse<InvoiceRequestObject>();
		try {
			InvoiceRequestObject responce =  invoiceService.generateInvoice1(invoiceRequestObject);
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
			return response.createListResponse(invoiceNumber, Constant.SUCCESS_CODE, String.valueOf(invoiceNumber.size()));
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
			return response.createListResponse(invoiceDetails, Constant.SUCCESS_CODE, String.valueOf(invoiceDetails.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/donationinvoice/{reffNo}", method = RequestMethod.GET)
	public Object donationinvoice(@PathVariable String reffNo) throws IOException {
	    DonationDetails donationDetails = donationHelper.getDonationDetailsByReferenceNo(reffNo);

	    if (donationDetails != null) {
	    	if(!donationDetails.getStatus().equalsIgnoreCase(Status.INACTIVE.name())) {
	    		
	        InvoiceHeaderDetails invoiceHeader = invoiceHelper.getInvoiceHeaderById(donationDetails.getInvoiceHeaderDetailsId());
	        ByteArrayOutputStream pdfStream = pdfInvoice.generatePdfInvoice(donationDetails, invoiceHeader);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	        headers.add("Pragma", "no-cache");
	        headers.add("Expires", "0");

	        headers.setContentLength(pdfStream.size());

	        String fileName = invoiceHeader.getCompanyFirstName()+"-invoice.pdf";
	        headers.setContentDispositionFormData("attachment", fileName);

	        InputStreamResource isr = new InputStreamResource(new ByteArrayInputStream(pdfStream.toByteArray()));

	        return new ResponseEntity<>(isr, headers, HttpStatus.OK);
	    	}else {
	    		ModelAndView modelAndView = new ModelAndView("message");
	 	        modelAndView.addObject("message", "Cancelled request. Please contact admin for details.");
	 	    	modelAndView.setViewName("message"); 
	 	        return modelAndView;
	    	}
	    } else {
	        // Handle the case when donationDetails is null by returning an error view
	        ModelAndView modelAndView = new ModelAndView("message");
	        modelAndView.addObject("message", "Invalid request. Please contact admin for details.");
	    	modelAndView.setViewName("message"); 
	        return modelAndView;
	    }
	}
	
}
