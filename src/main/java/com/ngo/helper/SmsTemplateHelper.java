package com.ngo.helper;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.constant.Constant;
import com.common.entities.UserDetails;
import com.common.enums.RoleType;
import com.common.enums.Status;
import com.common.exceptions.BizException;
import com.common.object.request.UserRequestObject;
import com.ngo.dao.CustomerDetailsDao;
import com.ngo.dao.SmsTemplateDetailsDao;
import com.ngo.entities.CustomerDetails;
import com.ngo.entities.SmsTemplateDetails;
import com.ngo.object.request.CustomerRequestObject;
import com.ngo.object.request.SmsTemplateRequestObject;

@Component
public class SmsTemplateHelper {

//	@Autowired
//	private CustomerDetailsDao customerDetailsDao;
	
	@Autowired
	private SmsTemplateDetailsDao smsDetailsTemplateDao;
	
	public void validateSmsTemplateRequest(SmsTemplateRequestObject smsTemplateRequestObject) throws BizException {
		if (smsTemplateRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}
	
	@Transactional
	public SmsTemplateDetails getSmsDetailsBySuperadminIdAndSmsType(String superadminId, String smsType) {

		CriteriaBuilder criteriaBuilder = smsDetailsTemplateDao.getSession().getCriteriaBuilder();
		CriteriaQuery<SmsTemplateDetails> criteriaQuery = criteriaBuilder.createQuery(SmsTemplateDetails.class);
		Root<SmsTemplateDetails> root = criteriaQuery.from(SmsTemplateDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		Predicate restriction2 = criteriaBuilder.equal(root.get("smsType"), smsType);
//		Predicate restriction3 = criteriaBuilder.equal(root.get("status"), status);
		criteriaQuery.where(restriction1,restriction2);
		SmsTemplateDetails smsDetails = smsDetailsTemplateDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return smsDetails;
	}


	@Transactional
	public SmsTemplateDetails getSmsDetailsBySuperadminIdAndHeaderIdAndSmsType(String superadminId, Long invoiceHeaderId, String smsType) {

		CriteriaBuilder criteriaBuilder = smsDetailsTemplateDao.getSession().getCriteriaBuilder();
		CriteriaQuery<SmsTemplateDetails> criteriaQuery = criteriaBuilder.createQuery(SmsTemplateDetails.class);
		Root<SmsTemplateDetails> root = criteriaQuery.from(SmsTemplateDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		Predicate restriction2 = criteriaBuilder.equal(root.get("invoiceHeaderId"), invoiceHeaderId);
//		Predicate restriction3 = criteriaBuilder.equal(root.get("status"), status);
		criteriaQuery.where(restriction1,restriction2);
		SmsTemplateDetails smsDetails = smsDetailsTemplateDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return smsDetails;
	}
	public SmsTemplateDetails getSmsTemplateDetailsByReqObj(SmsTemplateRequestObject smsTemplateRequest) {

		SmsTemplateDetails smsTemplateDetails = new SmsTemplateDetails();

		smsTemplateDetails.setSmsUserId(smsTemplateRequest.getSmsUserId());
		smsTemplateDetails.setSmsPassword(smsTemplateRequest.getSmsPassword());
		smsTemplateDetails.setService(smsTemplateRequest.getService());
		smsTemplateDetails.setSmsUrl(smsTemplateRequest.getSmsUrl());
		smsTemplateDetails.setSmsSender(smsTemplateRequest.getSmsSender());
		smsTemplateDetails.setSmsType(smsTemplateRequest.getSmsType());
		smsTemplateDetails.setStatus(Status.ACTIVE.name());
		smsTemplateDetails.setTemplateId(smsTemplateRequest.getTemplateId());
		smsTemplateDetails.setEntityId(smsTemplateRequest.getEntityId());
		smsTemplateDetails.setCompanyName(smsTemplateRequest.getCompanyName());
		smsTemplateDetails.setInvoiceDomain(smsTemplateRequest.getInvoiceDomain());
		smsTemplateDetails.setCompanyRegards(smsTemplateRequest.getCompanyRegards());
		smsTemplateDetails.setSuperadminId(smsTemplateRequest.getSuperadminId());
		smsTemplateDetails.setUpdatedBy(smsTemplateRequest.getUpdatedBy());
		smsTemplateDetails.setCreatedAt(new Date());
		smsTemplateDetails.setUpdatedAt(new Date());
		return smsTemplateDetails;
	}

	@Transactional
	public SmsTemplateDetails saveSmsTemplateDetails(SmsTemplateDetails smsTemplateDetails) {
		smsDetailsTemplateDao.persist(smsTemplateDetails);
		return smsTemplateDetails;
	}
	

	public SmsTemplateDetails getUpdatedSmsTemplateDetailsByReqObj(SmsTemplateRequestObject smsTemplateRequest, SmsTemplateDetails smsTemplateDetails) {

		smsTemplateDetails.setSmsUserId(smsTemplateRequest.getSmsUserId());
		smsTemplateDetails.setSmsPassword(smsTemplateRequest.getSmsPassword());
		smsTemplateDetails.setService(smsTemplateRequest.getService());
		smsTemplateDetails.setSmsUrl(smsTemplateRequest.getSmsUrl());
		smsTemplateDetails.setSmsSender(smsTemplateRequest.getSmsSender());
		smsTemplateDetails.setSmsType(smsTemplateRequest.getSmsType());
		smsTemplateDetails.setTemplateId(smsTemplateRequest.getTemplateId());
		smsTemplateDetails.setEntityId(smsTemplateRequest.getEntityId());
		smsTemplateDetails.setCompanyName(smsTemplateRequest.getCompanyName());
		smsTemplateDetails.setInvoiceDomain(smsTemplateRequest.getInvoiceDomain());
		smsTemplateDetails.setCompanyRegards(smsTemplateRequest.getCompanyRegards());
		smsTemplateDetails.setUpdatedBy(smsTemplateRequest.getUpdatedBy());
		smsTemplateDetails.setUpdatedAt(new Date());

		return smsTemplateDetails;
	}

	@Transactional
	public SmsTemplateDetails UpdateSmsTemplateDetails(SmsTemplateDetails smsTemplateDetails) {
		smsDetailsTemplateDao.update(smsTemplateDetails);
		return smsTemplateDetails;
	}



	@SuppressWarnings("unchecked")
	public List<SmsTemplateDetails> getSmsTemplateList(SmsTemplateRequestObject smsTemplateRequest) {
		if(smsTemplateRequest.getRequestedFor().equals("ALL")) {
			String hqlQuery = "SELECT SD FROM SmsTemplateDetails SD";
			List<SmsTemplateDetails> results = smsDetailsTemplateDao.getEntityManager()
					.createQuery(hqlQuery)
					.getResultList();
			return results;
		}else {
			String hqlQuery = "SELECT SD FROM SmsTemplateDetails SD";
			List<SmsTemplateDetails> results = smsDetailsTemplateDao.getEntityManager()
					.createQuery(hqlQuery)
					.getResultList();
			return results;
		}

	}

	

}
