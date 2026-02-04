package com.invoice.services;

import java.io.File;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.constant.Constant;
import com.common.entities.InvoiceHeaderDetails;
import com.common.exceptions.BizException;
import com.common.helper.InvoiceHeaderHelper;
import com.common.object.request.Request;
import com.invoice.entities.InvoiceDetails;
import com.invoice.entities.InvoiceNumber;
import com.invoice.helper.InvoiceHelper;
import com.invoice.object.request.InvoiceRequestObject;
import com.invoice.pdf.InvoiceReceipt;

//@Transactional(rollbackOn = Exception.class)s
@Service
public class InvoiceService {

	@Autowired
	private InvoiceHelper invoiceHelper;

	@Autowired
	private InvoiceHeaderHelper invoiceHeaderHelper;

	@Autowired
	private InvoiceReceipt invoiceReceipt;

	@Transactional
	public InvoiceRequestObject generateInvoice(Request<InvoiceRequestObject> invoiceRequestObject)
			throws BizException, Exception {

		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
		invoiceHelper.validateInvoiceRequest(invoiceRequest);

		InvoiceHeaderDetails invoiceHeader = invoiceHeaderHelper.getInvoiceHeaderBySuperAdminId(invoiceRequest.getSuperadminId());
		if (invoiceHeader != null) {

			InvoiceNumber invoiceNumber = invoiceHelper.getInvoiceNumberByReqObj(invoiceRequest);
			invoiceNumber = invoiceHelper.saveInvoiceNumber(invoiceNumber);

			List<InvoiceDetails> invoiceDetails = invoiceHelper.getInvoiceDetailsByReqObj(invoiceRequest);
			invoiceHelper.saveInvoiceDetails(invoiceDetails);

			invoiceRequest.setRespCode(Constant.SUCCESS_CODE);
			invoiceRequest.setRespMesg(Constant.INVOICE_GEN_SUCCESS);
			return invoiceRequest;
		}
		invoiceRequest.setRespCode(404);
		invoiceRequest.setRespMesg("Invoice Header Not Found");
		return invoiceRequest;
	}

//	public List<InvoiceNumber> getInvoiceNumberList(Request<InvoiceRequestObject> invoiceRequestObject) {
//		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
//		List<InvoiceNumber> invoiceNumList = invoiceHelper.getInvoiceNumberList(invoiceRequest);
//		return invoiceNumList;
//	}

//	public List<InvoiceDetails> getInvoiceDetailsList(Request<InvoiceRequestObject> invoiceRequestObject) {
//		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
//		List<InvoiceDetails> invoiceDetails = invoiceHelper.getInvoiceDetailsList(invoiceRequest);
//		return invoiceDetails;
//	}

//	public List<Object[]> getInvoiceWithDetails(Request<InvoiceRequestObject> invoiceRequestObject) throws BizException, Exception {
//		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
//		List<Object[]> rows= invoiceHelper.getInvoiceWithDetails(invoiceRequest);
//		System.out.println("Row : "+rows);
//		for (Object[] row : rows) {
//		    InvoiceNumber invoiceNumber = (InvoiceNumber) row[0];
//		    InvoiceDetails invoiceDetails = (InvoiceDetails) row[1];
//
//		    System.out.println(invoiceNumber.getInvoiceNumber());
//		    System.out.println(invoiceDetails.getProductName());
//		}
//		return rows;
//	}

//	public List<InvoiceDetails> getInvoiceDetailsList(Request<InvoiceRequestObject> invoiceRequestObject) {
//		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
//		List<InvoiceDetails> invoiceDetails = invoiceHelper.getInvoiceDetailsList(invoiceRequest);
//		return invoiceDetails;
//	}

//	public List<InvoiceRequestObject> getInvoiceWithDetails(Request<InvoiceRequestObject> invoiceRequestObject) {
//		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
//		List<InvoiceRequestObject> invoiceRequestList = invoiceHelper.getInvoiceDetailsNumber(invoiceRequest);
//		for(invoiceRequest.getItems() item : invoiceRequestList) {
//			List<InvoiceRequestObject> invoicDetailsList = invoiceHelper.getInvoiceDetails(invoiceRequest);
//		}
//		List<InvoiceRequestObject> invoicDetailsList = invoiceHelper.getInvoiceDetails(invoiceRequest);
//		invoiceRequestList.add(invoicDetailsList);
//		return invoiceRequestList;
//	}

//	public List<InvoiceRequestObject> getInvoiceWithDetails(Request<InvoiceRequestObject> invoiceRequestObject) {
//	    InvoiceRequestObject request = invoiceRequestObject.getPayload();
//	    List<InvoiceRequestObject> invoiceList = invoiceHelper.getInvoiceWithDetails(request);
//	    
//	    String path = "C://invoices/abcde.pdf";
//
//	    invoiceReceipt.generateInvoicePdf(invoiceList, path);
//	    return invoiceList;
//	}

	@SuppressWarnings("static-access")
	public List<InvoiceRequestObject> getInvoiceWithDetails(Request<InvoiceRequestObject> invoiceRequestObject)
			throws Exception {

		InvoiceRequestObject request = invoiceRequestObject.getPayload();
		List<InvoiceRequestObject> invoiceList = invoiceHelper.getInvoiceWithDetails(request);

		InvoiceHeaderDetails invoiceHeaderDetails = invoiceHeaderHelper.getInvoiceHeaderById(invoiceList.get(0).getCompanyId());

		// ensure directory exists
		String basePath = "C://invoices/";
		new File(basePath).mkdirs();

		// generate PDF for each invoice
		for (InvoiceRequestObject invoice : invoiceList) {

			String path = basePath + invoice.getInvoiceNumber() + ".pdf";

			invoiceReceipt.generateInvoicePdf(invoice, path, invoiceHeaderDetails);
		}
		return invoiceList;
	}

	public List<InvoiceRequestObject> downloadInvoice(Request<InvoiceRequestObject> invoiceRequestObject)
			throws Exception {
		InvoiceRequestObject request = invoiceRequestObject.getPayload();
		
				return null;
		
	}
}
