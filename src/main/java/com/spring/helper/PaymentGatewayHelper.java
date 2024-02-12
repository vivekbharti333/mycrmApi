package com.spring.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.constant.Constant;
import com.spring.dao.PaymentGatewayResponseDetailsDao;
import com.spring.dao.PaymentGatewayDetailsDao;
import com.spring.dao.PaymentModeBySuperadminDao;
import com.spring.dao.PaymentModeDetailsDao;
import com.spring.entities.DonationDetails;
import com.spring.entities.PaymentGatewayResponseDetails;
import com.spring.entities.PaymentGatewayDetails;
import com.spring.entities.PaymentModeBySuperadmin;
import com.spring.entities.PaymentModeMaster;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.PaymentRequestObject;

@Component
public class PaymentGatewayHelper {

	@Autowired
	private PaymentGatewayDetailsDao paymentGatewayDetailsDao;
	
	@Autowired
	private PaymentGatewayResponseDetailsDao paymentGatewayResponseDetailsDao;
	


	public void validatePaymentModeRequest(PaymentRequestObject optionRequestObject) throws BizException {
		if (optionRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}
	@Transactional
	public PaymentGatewayResponseDetails getPaymentGatewayResponseDetailsBySuperadminId(String merchantId, String merchantTransactionId) {

		CriteriaBuilder criteriaBuilder = paymentGatewayResponseDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<PaymentGatewayResponseDetails> criteriaQuery = criteriaBuilder.createQuery(PaymentGatewayResponseDetails.class);
		Root<PaymentGatewayResponseDetails> root = criteriaQuery.from(PaymentGatewayResponseDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("merchantId"), merchantId);
		Predicate restriction2 = criteriaBuilder.equal(root.get("invoiceNumber"), merchantTransactionId);
//		criteriaQuery.where(restriction1, restriction2);
		PaymentGatewayResponseDetails paymentGatewayResponseDetails = paymentGatewayResponseDetailsDao.getSession().createQuery(criteriaQuery)
				.uniqueResult();
		return paymentGatewayResponseDetails;
	}


	@Transactional
	public PaymentGatewayDetails getPaymentGatewayDetailsBySuperadminId(String superadminId, String pgProvider) {

		CriteriaBuilder criteriaBuilder = paymentGatewayDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<PaymentGatewayDetails> criteriaQuery = criteriaBuilder.createQuery(PaymentGatewayDetails.class);
		Root<PaymentGatewayDetails> root = criteriaQuery.from(PaymentGatewayDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		Predicate restriction2 = criteriaBuilder.equal(root.get("pgProvider"), pgProvider);
		criteriaQuery.where(restriction1, restriction2);
		PaymentGatewayDetails paymentGatewayDetails = paymentGatewayDetailsDao.getSession().createQuery(criteriaQuery)
				.uniqueResult();
		return paymentGatewayDetails;
	}

	public PaymentGatewayDetails getPaymentGatewayByReqObj(PaymentRequestObject paymentGatewayRequest) {

		PaymentGatewayDetails paymentGatewayDetails = new PaymentGatewayDetails();

		paymentGatewayDetails.setPgProvider(paymentGatewayRequest.getPgProvider());
		paymentGatewayDetails.setMerchantId(paymentGatewayRequest.getMerchantId());
		paymentGatewayDetails.setSaltIndex(paymentGatewayRequest.getSaltIndex());
		paymentGatewayDetails.setSaltKey(paymentGatewayRequest.getSaltKey());
		paymentGatewayDetails.setSuperadminId(paymentGatewayRequest.getSuperadminId());
		paymentGatewayDetails.setStatus(Status.ACTIVE.name());
		paymentGatewayDetails.setCreatedAt(new Date());

		return paymentGatewayDetails;
	}

	@Transactional
	public PaymentGatewayDetails savePaymentGatewayDetails(PaymentGatewayDetails paymentGatewayDetails) {
		paymentGatewayDetailsDao.persist(paymentGatewayDetails);
		return paymentGatewayDetails;
	}

	@SuppressWarnings("unchecked")
	public List<PaymentGatewayDetails> getPaymentGatewayDetailsList(PaymentRequestObject optionRequest) {
		List<PaymentGatewayDetails> results = new ArrayList<>();
		results = paymentGatewayDetailsDao.getEntityManager().createQuery(
				"SELECT PM FROM PaymentGatewayDetails PM WHERE PM.superadminId =:superadminId")
				.setParameter("superadminId", optionRequest.getSuperadminId()).getResultList();
		return results;
	}
	
	
	public PaymentGatewayResponseDetails getPaymentDetailsByReqObj(DonationDetails donationDetails, DonationRequestObject donationRequest) {

		PaymentGatewayResponseDetails paymentGatewayResponseDetails = new PaymentGatewayResponseDetails();

		paymentGatewayResponseDetails.setPgProvider(donationRequest.getPgProvider());
		paymentGatewayResponseDetails.setMerchantId(donationRequest.getMerchantId());
		paymentGatewayResponseDetails.setDonorName(donationDetails.getDonorName());
		paymentGatewayResponseDetails.setMobileNumber(donationDetails.getMobileNumber());
		paymentGatewayResponseDetails.setAmount(donationDetails.getAmount());
		paymentGatewayResponseDetails.setTransactionId(donationDetails.getTransactionId());
		paymentGatewayResponseDetails.setInvoiceNumber(donationDetails.getInvoiceNumber());
		paymentGatewayResponseDetails.setCreatedBy(donationDetails.getCreatedBy());
		paymentGatewayResponseDetails.setSuperadminId(donationDetails.getSuperadminId());
		paymentGatewayResponseDetails.setStatus(Status.INIT.name());
		paymentGatewayResponseDetails.setCreatedAt(new Date());
		paymentGatewayResponseDetails.setUpdatedAt(new Date());

		return paymentGatewayResponseDetails;
	}
	
	@Transactional
	public PaymentGatewayResponseDetails savePaymentDetails(PaymentGatewayResponseDetails paymentGatewayResponseDetails) {
		paymentGatewayResponseDetailsDao.persist(paymentGatewayResponseDetails);
		return paymentGatewayResponseDetails;
	}
	
	public PaymentGatewayResponseDetails getUpdatedPaymentDetailsByReqObj(PaymentGatewayResponseDetails paymentGatewayResponseDetails, PaymentRequestObject paymentGatewayRequest) {

		paymentGatewayResponseDetails.setTransactionId(paymentGatewayRequest.getTransactionId());
		paymentGatewayResponseDetails.setResponseCode(paymentGatewayRequest.getResponseCode());

		paymentGatewayResponseDetails.setStatus(paymentGatewayRequest.getStatus());
		paymentGatewayResponseDetails.setUpdatedAt(new Date());

		return paymentGatewayResponseDetails;
	}
	
	@Transactional
	public PaymentGatewayResponseDetails UpdatePaymentGatewayResponseDetails(PaymentGatewayResponseDetails paymentGatewayResponseDetails) {
		paymentGatewayResponseDetailsDao.update(paymentGatewayResponseDetails);
		return paymentGatewayResponseDetails;
	}
	
}
