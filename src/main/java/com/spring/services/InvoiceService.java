package com.spring.services;

import java.time.LocalDate;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.common.PdfInvoice;
import com.spring.constant.Constant;
import com.spring.entities.AddressDetails;
import com.spring.entities.CustomerDetails;
import com.spring.entities.DonationDetails;
import com.spring.entities.InvoiceDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.entities.InvoiceNumber;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.helper.AddressHelper;
import com.spring.helper.CustomerHelper;
import com.spring.helper.DonationHelper;
import com.spring.helper.InvoiceHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.AddressRequestObject;
import com.spring.object.request.InvoiceRequestObject;
import com.spring.object.request.Request;


@Service
public class InvoiceService {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private InvoiceHelper invoiceHelper;
	
	@Autowired
	private PdfInvoice pdfInvoice;
	
	@Autowired
	private DonationHelper donationHelper;
	
	@Autowired
	private CustomerHelper customerHelper;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	
	public String generateInvoiceNumber(InvoiceRequestObject invoiceRequest, InvoiceHeaderDetails invoiceHeader) {

		LocalDate currentdate = LocalDate.now();

		invoiceRequest.setSerialNumber(invoiceHeader.getSerialNumber() + 1);

		String invoiceNo = invoiceHeader.getInvoiceInitial()+"/" + currentdate.getYear()+"/" + currentdate.getMonthValue()+"00"+invoiceRequest.getSerialNumber();
		invoiceRequest.setInvoiceNo(invoiceNo);

		return invoiceNo;

	}

	
	public InvoiceRequestObject addInvoiceHeader(Request<InvoiceRequestObject> invoiceRequestObject) 
			throws BizException, Exception {
		
		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
		invoiceHelper.validateInvoiceRequest(invoiceRequest);
		
		Boolean isValid = jwtTokenUtil.validateJwtToken(invoiceRequest.getCreatedBy(), invoiceRequest.getToken());
		if (!isValid) {
			
			InvoiceHeaderDetails existsInvoiceHeader = invoiceHelper.getInvoiceHeaderBySuperAdminId(invoiceRequest.getSuperadminId());
			if(existsInvoiceHeader == null) {
				
				InvoiceHeaderDetails invoiceHeaderDetails = invoiceHelper.getInvoiceHeaderDetailsByReqObj(invoiceRequest);
				invoiceHeaderDetails = invoiceHelper.saveInvoiceHeaderDetails(invoiceHeaderDetails);
				
				invoiceRequest.setRespCode(Constant.SUCCESS_CODE);
				invoiceRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
				return invoiceRequest;
				
			}else {
				existsInvoiceHeader = invoiceHelper.getUpdatedInvoiceHeaderDetailsByReqObj(invoiceRequest, existsInvoiceHeader);
				invoiceHelper.updateInvoiceHeaderDetails(existsInvoiceHeader);
				
				invoiceRequest.setRespCode(Constant.SUCCESS_CODE);
				invoiceRequest.setRespMesg(Constant.UPDATED_SUCCESS);
				return invoiceRequest;
			}
		} else {
			invoiceRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
			invoiceRequest.setRespMesg(Constant.INVALID_TOKEN);
			return invoiceRequest;
		}
	}
	
	public InvoiceRequestObject updateInvoiceHeader(Request<InvoiceRequestObject> invoiceRequestObject) 
		throws BizException, Exception {
			
			InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
			invoiceHelper.validateInvoiceRequest(invoiceRequest);
			
			Boolean isValid = jwtTokenUtil.validateJwtToken(invoiceRequest.getCreatedBy(), invoiceRequest.getToken());
			if (isValid) {
				
				InvoiceHeaderDetails existsInvoiceHeader = invoiceHelper.getInvoiceHeaderBySuperAdminId(invoiceRequest.getSuperadminId());
				if(existsInvoiceHeader != null) {
					
					InvoiceHeaderDetails invoiceHeaderDetails = invoiceHelper.getInvoiceHeaderDetailsByReqObj(invoiceRequest);
					invoiceHeaderDetails = invoiceHelper.saveInvoiceHeaderDetails(invoiceHeaderDetails);
					
					invoiceRequest.setRespCode(Constant.SUCCESS_CODE);
					invoiceRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
					return invoiceRequest;
					
				}else {
					invoiceRequest.setRespCode(Constant.ALREADY_EXISTS);
					invoiceRequest.setRespMesg("EXISTS");
					return invoiceRequest;
				}
			} else {
				invoiceRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
				invoiceRequest.setRespMesg(Constant.INVALID_TOKEN);
				return invoiceRequest;
			}
		}
	
	
	@SuppressWarnings("unused")
	public InvoiceRequestObject generateInvoice1(Request<InvoiceRequestObject> invoiceRequestObject)
			throws BizException, Exception {

		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
		invoiceHelper.validateInvoiceRequest(invoiceRequest);

		Boolean isValid = jwtTokenUtil.validateJwtToken(invoiceRequest.getCreatedBy(), invoiceRequest.getToken());
		logger.info("Invoice generate Request Is valid? : " + invoiceRequest.getCreatedBy() + " is " + isValid);


		if (!isValid) {

			DonationDetails donationDetails = donationHelper.getDonationDetailsByIdAndSuperadminId(invoiceRequest.getId(), invoiceRequest.getSuperadminId());
			
			if(donationDetails != null) {
				
				InvoiceHeaderDetails invoiceHeader = invoiceHelper.getInvoiceHeaderBySuperAdminId(invoiceRequest.getSuperadminId());
				if (invoiceHeader != null) {
					String invoiceSrNo = this.generateInvoiceNumber(invoiceRequest, invoiceHeader);

					donationDetails.setInvoiceNumber(invoiceSrNo);
					donationHelper.updateDonationDetails(donationDetails);
					
					pdfInvoice.generatePdfInvoice(donationDetails, invoiceHeader);

					// Invoice Number
//					InvoiceNumber invoiceNumber = invoiceHelper.getInvoiceNumberByReqObj(invoiceRequest);
//					invoiceNumber = invoiceHelper.saveInvoiceNumber(invoiceNumber);   

					invoiceRequest.setRespCode(Constant.SUCCESS_CODE);
					invoiceRequest.setRespMesg(Constant.INVOICE_GEN_SUCCESS);
					return invoiceRequest;

				} else {
					invoiceRequest.setRespCode(Constant.BAD_REQUEST_CODE);
					invoiceRequest.setRespMesg("Please add invoice header first");
					return invoiceRequest;
				}
				
			}else {
				// not found
			}
			
			
		} else {
			invoiceRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
			invoiceRequest.setRespMesg(Constant.INVALID_TOKEN);
			return invoiceRequest;
		}
		return invoiceRequest;
	}
	
	
	

	@SuppressWarnings("unused")
	public InvoiceRequestObject generateInvoice(Request<InvoiceRequestObject> invoiceRequestObject)
			throws BizException, Exception {

		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
		invoiceHelper.validateInvoiceRequest(invoiceRequest);

		Boolean isValid = jwtTokenUtil.validateJwtToken(invoiceRequest.getCreatedBy(), invoiceRequest.getToken());
		logger.info("Invoice generate Request Is valid? : " + invoiceRequest.getCreatedBy() + " is " + isValid);

		if (isValid) {

			InvoiceHeaderDetails invoiceHeader = invoiceHelper.getInvoiceHeaderBySuperAdminId(invoiceRequest.getSuperadminId());
			if (invoiceHeader != null) {
				String invoiceSrNo = this.generateInvoiceNumber(invoiceRequest, invoiceHeader);

//				if (invoiceRequest.getRequestFor().equals(Status.NEW.name())) {
//
//					CustomerDetails customerDetails = customerHelper.getCustomerDetailsByReqObj(invoiceRequest.getCustomerRequestObject());
//					customerHelper.saveCustomerDetails(customerDetails);
//
//					AddressDetails addressDetails = addressService.saveAddressDetailsByRequest(invoiceRequest.getAddressRequestObject(), customerDetails.getId(), customerDetails.getSuperadminId());
//					
//				}

				// Invoice Number
				InvoiceNumber invoiceNumber = invoiceHelper.getInvoiceNumberByReqObj(invoiceRequest);
				invoiceNumber = invoiceHelper.saveInvoiceNumber(invoiceNumber);

				// Invoice Details
				int totalItem = 0;
				int totalAmount = 0;
				for (InvoiceDetails invoice : invoiceRequest.getItemDetails()) {

					invoice.setAmount(invoice.getRate() * invoice.getQuantity());

					InvoiceDetails invoiceDetails = invoiceHelper.getInvoiceDetailsByReqObj(invoice, invoiceRequest);
					invoiceDetails = invoiceHelper.saveInvoiceDetails(invoiceDetails);

					totalItem += 1;
					totalAmount += invoice.getAmount();
				}

				// Update InvoiceNumber
				invoiceNumber.setTotalItem(totalItem);
				invoiceNumber.setTotalAmount(totalAmount);
				invoiceHelper.updateInvoiceNumber(invoiceNumber);

				// Update SrNo
				invoiceHeader.setSerialNumber(invoiceRequest.getSerialNumber());
				invoiceHelper.updateInvoiceHeaderDetails(invoiceHeader);

				invoiceRequest.setRespCode(Constant.SUCCESS_CODE);
				invoiceRequest.setRespMesg(Constant.INVOICE_GEN_SUCCESS);
				return invoiceRequest;

			} else {
				invoiceRequest.setRespCode(Constant.BAD_REQUEST_CODE);
				invoiceRequest.setRespMesg("Please add invoice header first");
				return invoiceRequest;
			}
		} else {
			invoiceRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
			invoiceRequest.setRespMesg(Constant.INVALID_TOKEN);
			return invoiceRequest;
		}
	}
	

	public List<InvoiceNumber> getInvoiceNumberList(Request<InvoiceRequestObject> invoiceRequestObject) {
		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
		List<InvoiceNumber> invoiceNumList = invoiceHelper.getInvoiceNumberList(invoiceRequest);
		return invoiceNumList;
	}


	public List<InvoiceDetails> getInvoiceDetailsList(Request<InvoiceRequestObject> invoiceRequestObject) {
		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
		List<InvoiceDetails> invoiceDetails = invoiceHelper.getInvoiceDetailsList(invoiceRequest);
		return invoiceDetails;
	}


	public List<InvoiceHeaderDetails> getInvoiceHeaderList(Request<InvoiceRequestObject> invoiceRequestObject) {
		InvoiceRequestObject invoiceRequest = invoiceRequestObject.getPayload();
		List<InvoiceHeaderDetails> invoiceHeader = invoiceHelper.getInvoiceHeaderList(invoiceRequest);
		return invoiceHeader;
	}


}

