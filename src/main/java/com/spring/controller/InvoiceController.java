package com.spring.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


//import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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
	
	@RequestMapping(value = "/invoiceFromStorage/{reffNo:.+}", method = RequestMethod.GET)
	public ResponseEntity<?> invoiceFromStorage(@PathVariable String reffNo) throws IOException {
	    // Remove ".pdf" if it exists
//	    if (reffNo.endsWith(".pdf")) {
//	        reffNo = reffNo.substring(0, reffNo.length() - 4); // Remove the last 4 characters
//	    }

	    // Split the reffNo by the dot to get the actual reference number (without the extension)
	    String[] parts = reffNo.split("\\.");

	    // Print the result (optional, for debugging)
//	    if (parts.length > 0) {
//	        System.out.println("Reference number (without .pdf): " + parts[0]);
//	    } else {
//	        System.out.println("No valid reference number found!");
//	    }

	    // Fetch donation details using the reference number without the .pdf extension
	    DonationDetails donationDetails = donationHelper.getDonationDetailsByReferenceNo(parts[0]);
	    
	    System.out.println("donationDetails : "+donationDetails);
	    System.out.println("Parts : "+parts[0]);

	    if (donationDetails != null) {
	        // Check if the donation is active
	        if (!donationDetails.getStatus().equalsIgnoreCase(Status.INACTIVE.name())) {
	            // Fetch invoice header details
	            InvoiceHeaderDetails invoiceHeader = invoiceHelper.getInvoiceHeaderById(donationDetails.getInvoiceHeaderDetailsId());

	            // Define the path where your PDF is stored (ensure the file path includes .pdf)
	            String pdfFilePath = "D://" + donationDetails.getReceiptNumber() + ".pdf";
	            File pdfFile = new File(pdfFilePath);

	            System.out.println("Pdf Path: " + pdfFilePath);
	            if (pdfFile.exists()) {
	                // Prepare response headers for the PDF file
	                HttpHeaders headers = new HttpHeaders();
	                headers.setContentType(MediaType.APPLICATION_PDF);
	                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	                headers.add("Pragma", "no-cache");
	                headers.add("Expires", "0");

	                headers.setContentLength(pdfFile.length());

	                // Set the content disposition to trigger download with a custom file name
	                String fileName = invoiceHeader.getCompanyFirstName() + "-invoice.pdf";
	                headers.setContentDispositionFormData("attachment", fileName);

	                // Use a FileInputStream to stream the PDF file content
	                try (FileInputStream fis = new FileInputStream(pdfFile)) {
	                    InputStreamResource isr = new InputStreamResource(fis);
	                    // Return the file as a response entity with the necessary headers
	                    return new ResponseEntity<>(isr, headers, HttpStatus.OK);
	                } catch (IOException e) {
	                    // Log the error and return a 500 if something goes wrong
	                    e.printStackTrace();
	                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	                }
	            }
	            // Return 404 if the PDF file is not found
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	        // Return 400 for inactive donation
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    }
	    // Return 400 for invalid donation details
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@RequestMapping(path = "/invoiceFromStorage/{reffNo:.+}", produces="application/pdf" ,method=RequestMethod.GET)
	public ResponseEntity<InputStreamResource> finalReceipt(@PathVariable String reffNo, HttpServletRequest request) throws Exception 
	{
		String[] parts = reffNo.split("\\.");
		
		 DonationDetails donationDetails = donationHelper.getDonationDetailsByReferenceNo(parts[0]);
		 
		 System.out.println("Parts : "+parts[0]);
		 System.out.println("Donation details : "+donationDetails);

//	    if (donationDetails != null) {
  
		String path = "D://" + donationDetails.getReceiptNumber() + ".pdf";
		
	 		File file = new File(path.toString());
	 	    InputStreamResource isResource = new InputStreamResource(new FileInputStream(file));
	 	    FileSystemResource fileSystemResource = new FileSystemResource(file);
//	 	    String fileName = FilenameUtils.getName(file.getAbsolutePath());
	 	   String fileName=new String(file.toString().getBytes("UTF-8"),"iso-8859-1");
	 	    HttpHeaders headers = new HttpHeaders();
	 	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	 	    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	 	    headers.add("Pragma", "no-cache");
	 	    headers.add("Expires", "0");
	 	    headers.setContentLength(fileSystemResource.contentLength());
	 	    headers.setContentDispositionFormData("attachment", fileName);
	 	    System.out.println("fileName : "+fileName);
	 	    return new ResponseEntity<InputStreamResource>(isResource, headers, HttpStatus.OK); 
	}



	
}
