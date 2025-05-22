package com.spring.helper;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.constant.Constant;
import com.spring.dao.PaymentGatewayResponseDetailsDao;
import com.spring.entities.DonationDetails;
import com.spring.entities.PaymentGatewayResponseDetails;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.PaymentRequestObject;

@Component
public class PhonePePgHelper {

	@Autowired
	private PaymentGatewayResponseDetailsDao paymentGatewayResponseDetailsDao;
	


	public void validatePaymentModeRequest(PaymentRequestObject optionRequestObject) throws BizException {
		if (optionRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}
	
//	@Transactional
//	public PaymentGatewayResponseDetails getPaymentGatewayResponseDetailsBySuperadminId(String merchantId, String merchantTransactionId) {
//
//		CriteriaBuilder criteriaBuilder = paymentGatewayResponseDetailsDao.getSession().getCriteriaBuilder();
//		CriteriaQuery<PaymentGatewayResponseDetails> criteriaQuery = criteriaBuilder.createQuery(PaymentGatewayResponseDetails.class);
//		Root<PaymentGatewayResponseDetails> root = criteriaQuery.from(PaymentGatewayResponseDetails.class);
//		Predicate restriction1 = criteriaBuilder.equal(root.get("merchantId"), merchantId);
//		Predicate restriction2 = criteriaBuilder.equal(root.get("invoiceNumber"), merchantTransactionId);
//		criteriaQuery.where(restriction1, restriction2);
//		PaymentGatewayResponseDetails paymentGatewayResponseDetails = paymentGatewayResponseDetailsDao.getSession().createQuery(criteriaQuery)
//				.uniqueResult();
//		return paymentGatewayResponseDetails;
//	}
//	
//	public PaymentGatewayResponseDetails getPaymentDetailsByReqObj(DonationDetails donationDetails, DonationRequestObject donationRequest) {
//
//		PaymentGatewayResponseDetails paymentGatewayResponseDetails = new PaymentGatewayResponseDetails();
//
//		paymentGatewayResponseDetails.setPgProvider(donationRequest.getPgProvider());
//		paymentGatewayResponseDetails.setMerchantId(donationRequest.getMerchantId());
//		paymentGatewayResponseDetails.setDonorName(donationDetails.getDonorName());
//		paymentGatewayResponseDetails.setMobileNumber(donationDetails.getMobileNumber());
//		paymentGatewayResponseDetails.setAmount(donationDetails.getAmount());
//		paymentGatewayResponseDetails.setTransactionId(donationDetails.getTransactionId());
////		paymentGatewayResponseDetails.setInvoiceNumber(donationDetails.getInvoiceNumber());
//		paymentGatewayResponseDetails.setReceiptNumber(donationDetails.getReceiptNumber());
//		
//		paymentGatewayResponseDetails.setCreatedBy(donationDetails.getCreatedBy());
//		paymentGatewayResponseDetails.setSuperadminId(donationDetails.getSuperadminId());
//		paymentGatewayResponseDetails.setStatus(Status.INIT.name());
//		paymentGatewayResponseDetails.setCreatedAt(new Date());
//		paymentGatewayResponseDetails.setUpdatedAt(new Date());
//
//		return paymentGatewayResponseDetails;
//	}
//
//	
//	@Transactional
//	public PaymentGatewayResponseDetails savePaymentDetails(PaymentGatewayResponseDetails paymentGatewayResponseDetails) {
//		paymentGatewayResponseDetailsDao.persist(paymentGatewayResponseDetails);
//		return paymentGatewayResponseDetails;
//	}
//	
//	public PaymentGatewayResponseDetails getUpdatedPaymentDetailsByReqObj(PaymentGatewayResponseDetails paymentGatewayResponseDetails, PaymentRequestObject paymentGatewayRequest) {
//
//		paymentGatewayResponseDetails.setTransactionId(paymentGatewayRequest.getTransactionId());
//		paymentGatewayResponseDetails.setResponseCode(paymentGatewayRequest.getResponseCode());
//
//		paymentGatewayResponseDetails.setStatus(paymentGatewayRequest.getStatus());
//		paymentGatewayResponseDetails.setUpdatedAt(new Date());
//
//		return paymentGatewayResponseDetails;
//	}
//	
//	@Transactional
//	public PaymentGatewayResponseDetails UpdatePaymentGatewayResponseDetails(PaymentGatewayResponseDetails paymentGatewayResponseDetails) {
//		paymentGatewayResponseDetailsDao.update(paymentGatewayResponseDetails);
//		return paymentGatewayResponseDetails;
//	}
//	
}
