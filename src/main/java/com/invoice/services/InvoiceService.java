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
	
	


	@Transactional
	public InvoiceRequestObject generateInvoice(Request<InvoiceRequestObject> invoiceRequestObject)
			throws BizException, Exception {

		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
		invoiceHelper.validateInvoiceRequest(invoiceRequest);

		InvoiceHeaderDetails invoiceHeader = invoiceHeaderHelper
				.getInvoiceHeaderBySuperAdminId(invoiceRequest.getSuperadminId());
		if (invoiceHeader != null) {

			InvoiceNumber invoice = invoiceHelper.getInvoiceByInvoiceNumber(invoiceRequest.getInvoiceNumber());
			if (invoice == null) {
				InvoiceNumber invoiceNumber = invoiceHelper.getInvoiceNumberByReqObj(invoiceRequest);
				invoiceNumber = invoiceHelper.saveInvoiceNumber(invoiceNumber);

				List<InvoiceDetails> invoiceDetails = invoiceHelper.getInvoiceDetailsByReqObj(invoiceRequest);
				invoiceHelper.saveInvoiceDetails(invoiceDetails);
				
				// increase serial number by 1
				invoiceHeader.setSerialNumber(invoiceHeader.getSerialNumber() + 1);
				invoiceHeaderHelper.updateInvoiceHeaderDetails(invoiceHeader);

				invoiceRequest.setRespCode(Constant.SUCCESS_CODE);
				invoiceRequest.setRespMesg(Constant.INVOICE_GEN_SUCCESS);
				return invoiceRequest;
			} else {
				invoiceRequest.setRespCode(404);
				invoiceRequest.setRespMesg("Invoice Number Already Exists");
				return invoiceRequest;
			}
		}
		invoiceRequest.setRespCode(404);
		invoiceRequest.setRespMesg("Invoice Header Not Found");
		return invoiceRequest;
	}

//	@SuppressWarnings("static-access")
//	public List<InvoiceRequestObject> getInvoiceWithDetails(Request<InvoiceRequestObject> invoiceRequestObject)
//			throws Exception {
//
//		InvoiceRequestObject request = invoiceRequestObject.getPayload();
//		List<InvoiceRequestObject> invoiceList = invoiceHelper.getInvoiceWithDetails(request);
//
//		InvoiceHeaderDetails invoiceHeaderDetails = invoiceHeaderHelper.getInvoiceHeaderById(invoiceList.get(0).getCompanyId());
//
//		// ensure directory exists
//		String basePath = "C://invoices/";
//		new File(basePath).mkdirs();
//
//		// generate PDF for each invoice
//		for (InvoiceRequestObject invoice : invoiceList) {
//
//			String path = basePath + invoice.getInvoiceNumber() + ".pdf";
//
//			invoiceReceipt.generateInvoicePdf(invoice, path, invoiceHeaderDetails);
//		}
//		return invoiceList;
//	}

	
}
