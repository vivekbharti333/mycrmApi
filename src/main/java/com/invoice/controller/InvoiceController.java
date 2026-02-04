package com.invoice.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.entities.InvoiceHeaderDetails;
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.common.object.response.GenricResponse;
import com.common.object.response.Response;
import com.common.pdf.InvoiceGenerator;
import com.invoice.entities.InvoiceNumber;
import com.invoice.object.request.InvoiceRequestObject;
import com.invoice.services.InvoiceService;

@CrossOrigin(origins = { "*" })
@RestController
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;
	
    @Autowired
    private InvoiceGenerator invoiceGenerator;

	
	@RequestMapping(path = "generateInvoice", method = RequestMethod.POST)
	public Response<InvoiceRequestObject> generateInvoice(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject) {
	    GenricResponse<InvoiceRequestObject> responseObj = new GenricResponse<>();
	    try {
	        InvoiceRequestObject response = invoiceService.generateInvoice(invoiceRequestObject);
	        return responseObj.createSuccessResponse(response, 200);
	    } catch (BizException e) {
	        return responseObj.createErrorResponse(400, e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        return responseObj.createErrorResponse(500, "Internal Server Error");
	    }
	}

//	@PostMapping("getInvoiceWithDetails")
//	public Response<InvoiceRequestObject> getInvoiceWithDetails(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject) {
//
//	    GenricResponse<List<InvoiceRequestObject[]>> responseObj = new GenricResponse<>();
//	    try {
//	        List<InvoiceRequestObject> result = invoiceService.getInvoiceWithDetails(invoiceRequestObject);
//
//	        return response.createListResponse(invoiceNumber, 200, String.valueOf(invoiceNumber.size()));
//
//	    } catch (BizException e) {
//	        return responseObj.createErrorResponse(400, e.getMessage());
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return responseObj.createErrorResponse(500, "Internal Server Error");
//	    }
//	}


	@RequestMapping(path = { "getInvoiceWithDetails" }, method = { RequestMethod.POST })
	public Response<InvoiceRequestObject> getInvoiceNumber(@RequestBody Request<InvoiceRequestObject> invoiceRequestObject) {
		GenricResponse<InvoiceRequestObject> response = new GenricResponse<InvoiceRequestObject>();

		try {
			List<InvoiceRequestObject> invoiceNumber = this.invoiceService.getInvoiceWithDetails(invoiceRequestObject);
			return response.createListResponse(invoiceNumber, 200, String.valueOf(invoiceNumber.size()));
		} catch (Exception var4) {
			var4.printStackTrace();
			return response.createErrorResponse(500, var4.getMessage());
		}
	}
//
//	@RequestMapping(path = { "getInvoiceDetailsList" }, method = { RequestMethod.POST })
//	public Response<InvoiceDetails> getInvoiceDetailsList(
//			@RequestBody Request<InvoiceRequestObject> invoiceRequestObject) {
//		GenricResponse<InvoiceDetails> response = new GenricResponse<InvoiceDetails>();
//
//		try {
//			List<InvoiceDetails> invoiceDetails = this.invoiceService.getInvoiceDetailsList(invoiceRequestObject);
//			return response.createListResponse(invoiceDetails, 200, String.valueOf(invoiceDetails.size()));
//		} catch (Exception var4) {
//			var4.printStackTrace();
//			return response.createErrorResponse(500, var4.getMessage());
//		}
//	}
	
    @GetMapping("/download/invoice")
    public ResponseEntity<byte[]> downloadInvoice() throws IOException {
    	
    	InvoiceHeaderDetails invoiceHeaderDetails = new InvoiceHeaderDetails();
    	InvoiceNumber invoiceNumber = new InvoiceNumber();
    	
        byte[] pdf = invoiceGenerator.generateInvoice(invoiceHeaderDetails, invoiceNumber);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Invoice_DEF-10_002.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
