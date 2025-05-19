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
import com.spring.dao.PaymentGatewayDetailsDao;
import com.spring.entities.PaymentGatewayDetails;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.PaymentRequestObject;

@Component
public class PaymentGatewayHelper {

	@Autowired
	private PaymentGatewayDetailsDao paymentGatewayDetailsDao;
	

	public void validatePaymentModeRequest(PaymentRequestObject optionRequestObject) throws BizException {
		if (optionRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}
	
	@Transactional
	public PaymentGatewayDetails getPaymentGatewayDetailsBySuperadminIdNStatus(String superadminId, String status) {

		CriteriaBuilder criteriaBuilder = paymentGatewayDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<PaymentGatewayDetails> criteriaQuery = criteriaBuilder.createQuery(PaymentGatewayDetails.class);
		Root<PaymentGatewayDetails> root = criteriaQuery.from(PaymentGatewayDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		Predicate restriction2 = criteriaBuilder.equal(root.get("status"), status);
		criteriaQuery.where(restriction1, restriction2);
		PaymentGatewayDetails paymentGatewayDetails = paymentGatewayDetailsDao.getSession().createQuery(criteriaQuery)
				.uniqueResult();
		return paymentGatewayDetails;
	}

	@Transactional
	public PaymentGatewayDetails getPaymentGatewayDetailsBySuperadminIdNpg(String superadminId, String pgProvider) {

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
	
	
}
