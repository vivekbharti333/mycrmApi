package com.spring.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.spring.exceptions.BizException;
import com.spring.helper.DonationHelper;
import com.spring.helper.InvoiceHelper;
import com.spring.object.request.InvoiceRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
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
	
	@RequestMapping(path = "updateInvoiceHeader", method = RequestMethod.POST)
	public Response<InvoiceRequestObject>updateInvoiceHeader(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject, HttpServletRequest request)
	{
		GenricResponse<InvoiceRequestObject> responseObj = new GenricResponse<InvoiceRequestObject>();
		try {
			InvoiceRequestObject responce =  invoiceService.updateInvoiceHeader(invoiceRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getInvoiceHeaderList", method = RequestMethod.POST)
	public Response<InvoiceHeaderDetails> getInvoiceHeaderList(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject) {
		GenricResponse<InvoiceHeaderDetails> response = new GenricResponse<InvoiceHeaderDetails>();
		try {
			List<InvoiceHeaderDetails> invoiceDetails = invoiceService.getInvoiceHeaderList(invoiceRequestObject);
			return response.createListResponse(invoiceDetails, Constant.SUCCESS_CODE);
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
	
	
	@RequestMapping(value = "/invoice/{reffNo}",  method = RequestMethod.GET)
	public ModelAndView viewPdf1(@PathVariable String reffNo, HttpServletResponse response) throws IOException {
	    ModelAndView modelAndView = new ModelAndView();
	    
	    DonationDetails donationDetails = donationHelper.getDonationDetailsByReferenceNo(reffNo);
	    if (donationDetails != null) {
//	    	if(donationDetails.getInvoiceDownloadStatus().equalsIgnoreCase("NO")) {
	    		 InvoiceHeaderDetails invoiceHeader = invoiceHelper.getInvoiceHeaderBySuperAdminId(donationDetails.getSuperadminId());

	 	        ByteArrayOutputStream pdfStream = pdfInvoice.generatePdfInvoice(donationDetails, invoiceHeader);

	 	        // Set the response headers for PDF content
	 	        response.setContentType("application/pdf");
	 	        response.setHeader("Content-Disposition", "inline; filename=donation-invoice.pdf");

	 	        // Copy the PDF content from the ByteArrayOutputStream to the response's output stream
	 	        response.getOutputStream().write(pdfStream.toByteArray());
	 	        response.flushBuffer();
//	    	}else {
//	    		modelAndView.addObject("message", "Invoice Already Downloaded. Please contact admin for details.");
//		    	modelAndView.setViewName("message"); 
//	    	}
	       
	    } else {
	        modelAndView.addObject("message", "Invalid request. Please contact admin for details.");
	    	modelAndView.setViewName("message"); 
	    }
	    
	    return modelAndView;
	}
	
	
	@RequestMapping(value = "viewPdf1", method = RequestMethod.GET)
	public void viewPdf(HttpServletResponse response) throws IOException {
		System.out.println("Enter hai1");

		DonationDetails donationDetails = donationHelper.getDonationDetailsByReferenceNo("1234");
		if (donationDetails != null) {
			InvoiceHeaderDetails invoiceHeader = invoiceHelper.getInvoiceHeaderBySuperAdminId(donationDetails.getSuperadminId());
			ByteArrayOutputStream pdfStream = pdfInvoice.generatePdfInvoice(donationDetails, invoiceHeader);
			// Set the response headers for PDF content
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline; filename=my-document.pdf");
			
			System.out.println("Enter hai");

			// Copy the PDF content from the ByteArrayOutputStream to the response's output
			// stream
			response.getOutputStream().write(pdfStream.toByteArray());
			response.flushBuffer();
		}
	}
	
	
	
	 
}
