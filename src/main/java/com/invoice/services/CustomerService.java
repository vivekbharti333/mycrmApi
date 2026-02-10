package com.invoice.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.constant.Constant;
import com.common.entities.UserDetails;
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.common.object.request.UserRequestObject;
import com.invoice.entities.CustomersDetails;
import com.invoice.helper.CustomerHelper;
import com.invoice.object.request.InvoiceRequestObject;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerHelper customerHelper;
	
	
	@Transactional
	public InvoiceRequestObject addCustomerDetails(Request<InvoiceRequestObject> invoiceRequestObject)
			throws BizException, Exception {

		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
		customerHelper.validateInvoiceRequest(invoiceRequest);

		CustomersDetails existsCustomerDetails = customerHelper.getCustomerDetailsById(invoiceRequest.getCustomerName(),invoiceRequest.getCompanyId(),invoiceRequest.getSuperadminId());
		if (existsCustomerDetails == null) {

			CustomersDetails customerDetails = customerHelper.getCustomerDetailsByReqObj(invoiceRequest);
			customerDetails = customerHelper.saveCustomerDetails(customerDetails);

			invoiceRequest.setRespCode(Constant.SUCCESS_CODE);
			invoiceRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
			return invoiceRequest;
		}
		invoiceRequest.setRespCode(Constant.ALREADY_EXISTS);
		invoiceRequest.setRespMesg(Constant.ALLREADY_EXISTS_MSG);
		return invoiceRequest;
	}
	
	public List<CustomersDetails> getCustomerDetails(Request<InvoiceRequestObject> invoiceRequestObject) {
		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
		List<CustomersDetails> userList = customerHelper.getCustomerDetails(invoiceRequest);
		return userList;
	}



}
