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
import com.spring.dao.PaymentModeBySuperadminDao;
import com.spring.dao.PaymentModeMasterDao;
import com.spring.entities.PaymentModeBySuperadmin;
import com.spring.entities.PaymentModeMaster;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.PaymentRequestObject;

@Component
public class PaymentModeHelper {

	@Autowired
	private PaymentModeMasterDao paymentModeMasterDao;
	
	@Autowired
	private PaymentModeBySuperadminDao paymentModeBySuperadminDao;

	public void validatePaymentModeRequest(PaymentRequestObject optionRequestObject) throws BizException {
		if (optionRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}
	
	@Transactional
	public PaymentModeMaster getPaymentModeMasterById(Long id) {

		CriteriaBuilder criteriaBuilder = paymentModeMasterDao.getSession().getCriteriaBuilder();
		CriteriaQuery<PaymentModeMaster> criteriaQuery = criteriaBuilder.createQuery(PaymentModeMaster.class);
		Root<PaymentModeMaster> root = criteriaQuery.from(PaymentModeMaster.class);
		Predicate restriction = criteriaBuilder.equal(root.get("id"), id);
		criteriaQuery.where(restriction);
		PaymentModeMaster optionTypeDetails = paymentModeMasterDao.getSession().createQuery(criteriaQuery)
				.uniqueResult();
		return optionTypeDetails;
	}

	@Transactional
	public PaymentModeMaster getPaymentModeMasterByPaymentMode(String paymentMode) {

		CriteriaBuilder criteriaBuilder = paymentModeMasterDao.getSession().getCriteriaBuilder();
		CriteriaQuery<PaymentModeMaster> criteriaQuery = criteriaBuilder.createQuery(PaymentModeMaster.class);
		Root<PaymentModeMaster> root = criteriaQuery.from(PaymentModeMaster.class);
		Predicate restriction = criteriaBuilder.equal(root.get("paymentMode"), paymentMode);
		criteriaQuery.where(restriction);
		PaymentModeMaster optionTypeDetails = paymentModeMasterDao.getSession().createQuery(criteriaQuery)
				.uniqueResult();
		return optionTypeDetails;
	}

	public PaymentModeMaster getPaymentModeByReqObj(PaymentRequestObject optionRequest) {

		PaymentModeMaster optionTypeDetails = new PaymentModeMaster();

		optionTypeDetails.setPaymentMode(optionRequest.getPaymentMode());
		optionTypeDetails.setStatus(Status.ACTIVE.name());
		optionTypeDetails.setCreatedAt(new Date());

		return optionTypeDetails;
	}

	@Transactional
	public PaymentModeMaster savePaymentModeMaster(PaymentModeMaster optionTypeDetails) {
		paymentModeMasterDao.persist(optionTypeDetails);
		return optionTypeDetails;
	}
	
	@Transactional
	public PaymentModeMaster updatePaymentModeMaster(PaymentModeMaster optionTypeDetails) {
		paymentModeMasterDao.update(optionTypeDetails);
		return optionTypeDetails;
	}


	@SuppressWarnings("unchecked")
	public List<PaymentModeMaster> getMasterPaymentModeList(PaymentRequestObject optionRequest) {
		List<PaymentModeMaster> results = new ArrayList<>();
		results = paymentModeMasterDao.getEntityManager().createQuery(
				"SELECT PM FROM PaymentModeMaster PM ORDER BY PM.paymentMode ASC")
				.getResultList();
		return results;
	}
	
//	@SuppressWarnings("unchecked")
//	public List<PaymentModeMaster> getMasterPaymentModeList(PaymentRequestObject optionRequest) {
//		List<PaymentModeMaster> results = new ArrayList<>();
//		if(optionRequest.getRoleType().equals(RoleType.MAINADMIN.name())) {
//			results = paymentModeMasterDao.getEntityManager().createQuery(
//					"SELECT PM FROM PaymentModeMaster PM ORDER BY PM.paymentMode ASC")
//					.getResultList();
//			return results;
//		}else {
//			results = paymentModeMasterDao.getEntityManager().createQuery(
//					"SELECT PM FROM PaymentModeMaster WHERE PM.status =:status PM ORDER BY PM.paymentMode ASC")
//					.setParameter("status", Status.ACTIVE.name())
//					.getResultList();
//			return results;
//		}
//	}
	
	
	@Transactional
	public PaymentModeBySuperadmin getPaymentModeBySuperadminId(String superadminId) {

		CriteriaBuilder criteriaBuilder = paymentModeBySuperadminDao.getSession().getCriteriaBuilder();
		CriteriaQuery<PaymentModeBySuperadmin> criteriaQuery = criteriaBuilder.createQuery(PaymentModeBySuperadmin.class);
		Root<PaymentModeBySuperadmin> root = criteriaQuery.from(PaymentModeBySuperadmin.class);
		Predicate restriction = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction);
		PaymentModeBySuperadmin optionTypeDetails = paymentModeMasterDao.getSession().createQuery(criteriaQuery)
				.uniqueResult();
		return optionTypeDetails;
	}

	public PaymentModeBySuperadmin getPaymentModeBySuperadminIdByReqObj(PaymentRequestObject paymentModeRequest) {

		PaymentModeBySuperadmin paymentModeBySuperadmin = new PaymentModeBySuperadmin();

		paymentModeBySuperadmin.setPaymentModeIds(paymentModeRequest.getPaymentModeIds());
		paymentModeBySuperadmin.setSuperadminId(paymentModeRequest.getSuperadminId());
		return paymentModeBySuperadmin;
	}

	@Transactional
	public PaymentModeBySuperadmin savePaymentModeBySuperadmin(PaymentModeBySuperadmin paymentModeBySuperadmin) {
		paymentModeBySuperadminDao.persist(paymentModeBySuperadmin);
		return paymentModeBySuperadmin;
	}
	
	
	public PaymentModeBySuperadmin getUpdatePaymentModeBySuperadminIdByReqObj(PaymentRequestObject paymentModeRequest, 
			PaymentModeBySuperadmin paymentModeBySuperadmin) {

		paymentModeBySuperadmin.setPaymentModeIds(paymentModeRequest.getPaymentModeIds());
		return paymentModeBySuperadmin;
	}
	
	@Transactional
	public PaymentModeBySuperadmin updatePaymentModeBySuperadmin(PaymentModeBySuperadmin paymentModeBySuperadmin) {
		paymentModeBySuperadminDao.update(paymentModeBySuperadmin);
		return paymentModeBySuperadmin;
	}
	

	public List<PaymentModeBySuperadmin> getPaymentModeListBySuperadminId1(PaymentRequestObject paymentModeRequest) {
	    String queryString = "SELECT ps FROM PaymentModeBySuperadmin ps WHERE ps.superadminId = :superadminId";
	    List<PaymentModeBySuperadmin> results = paymentModeBySuperadminDao.getEntityManager()
	            .createQuery(queryString, PaymentModeBySuperadmin.class)
	            .setParameter("superadminId", paymentModeRequest.getSuperadminId())
	            .getResultList();

	    return results;
	}
	
	public List<PaymentModeMaster> getPaymentModeListBySuperadminId(PaymentRequestObject paymentModeRequest, String paymentModeIds) {
	    List<PaymentModeMaster> results = new ArrayList<>();
//	    String paymentModeIds = "1,2,3";
	    String queryString = "SELECT pd FROM PaymentModeMaster pd WHERE pd.id IN ("+paymentModeIds+")";
	    results = paymentModeMasterDao.getEntityManager()
	            .createQuery(queryString, PaymentModeMaster.class)
	            .getResultList();
	    
	    return results;
	}
	

}
