package com.common.helper;

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
import com.common.dao.InvoiceHeaderDetailsDao;
import com.common.entities.InvoiceHeaderDetails;
import com.common.enums.Status;
import com.common.exceptions.BizException;

import com.ngo.object.request.InvoiceHeaderRequestObject;

@Component
public class InvoiceHeaderHelper {

	@Autowired
	private InvoiceHeaderDetailsDao invoiceHeaderDetailsDao;


	public void validateInvoiceRequest(InvoiceHeaderRequestObject invoiceRequestObject) throws BizException {
		if (invoiceRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

//	@Transactional
//	public InvoiceNumber getInvoiceNumberByInvoiceNo(InvoiceRequestObject invoiceRequest) {
//
//		CriteriaBuilder criteriaBuilder = invoiceNumberDao.getSession().getCriteriaBuilder();
//		CriteriaQuery<InvoiceNumber> criteriaQuery = criteriaBuilder.createQuery(InvoiceNumber.class);
//		Root<InvoiceNumber> root = criteriaQuery.from(InvoiceNumber.class);
//		Predicate restriction = criteriaBuilder.equal(root.get("invoiceNo"), invoiceRequest.getInvoiceNo());
//		restriction = criteriaBuilder.equal(root.get("superadminId"), invoiceRequest.getSuperadminId());
//		criteriaQuery.where(restriction);
//		InvoiceNumber invoiceNumber = invoiceNumberDao.getSession().createQuery(criteriaQuery).uniqueResult();
//		return invoiceNumber;
//	}
	
//	@Transactional
//	public InvoiceHeaderType getInvoiceHeaderTypeBySuperAdminIdAndName(String superadminId, String invoiceHeaderName) {
//
//		CriteriaBuilder criteriaBuilder = invoiceHeaderTypeDao.getSession().getCriteriaBuilder();
//		CriteriaQuery<InvoiceHeaderType> criteriaQuery = criteriaBuilder.createQuery(InvoiceHeaderType.class);
//		Root<InvoiceHeaderType> root = criteriaQuery.from(InvoiceHeaderType.class);
//		Predicate restriction1 = criteriaBuilder.equal(root.get("superadminId"), superadminId);
//		Predicate restriction2 = criteriaBuilder.equal(root.get("invoiceHeaderName"), invoiceHeaderName);
//		criteriaQuery.where(restriction1, restriction2);
//		InvoiceHeaderType invoiceHeader = invoiceHeaderTypeDao.getSession().createQuery(criteriaQuery).uniqueResult();
//		return invoiceHeader;
//	}
//	
//	public InvoiceHeaderType getInvoiceHeaderTypeByReqObj(InvoiceRequestObject invoiceRequest) {
//		
//		InvoiceHeaderType invoiceHeaderType = new InvoiceHeaderType();
//		invoiceHeaderType.setInvoiceHeaderDetailsId(invoiceRequest.getInvoiceHeaderDetailsId());
//		invoiceHeaderType.setInvoiceHeaderName(invoiceRequest.getInvoiceHeaderName());
//		invoiceHeaderType.setCreatedBy(invoiceRequest.getCreatedBy());
//		invoiceHeaderType.setSuperadminId(invoiceRequest.getSuperadminId());
//		
//		invoiceHeaderType.setCreatedAt(new Date());
//		invoiceHeaderType.setUpdatedAt(new Date());
//		
//		return invoiceHeaderType;
//	}
//	
//	@Transactional
//	public InvoiceHeaderType saveInvoiceHeaderType(InvoiceHeaderType invoiceHeaderType) {
//		invoiceHeaderTypeDao.persist(invoiceHeaderType);
//		return invoiceHeaderType;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<InvoiceHeaderType> getInvoiceHeaderTypeList(InvoiceRequestObject invoiceRequest) {
//		if(invoiceRequest.getRequestFor().equalsIgnoreCase("DROPDOWN")) {
//			List<InvoiceHeaderType> results = invoiceHeaderTypeDao.getEntityManager()
//					.createQuery("SELECT IY FROM InvoiceHeaderType IY WHERE IY.superadminId =:superadminId")
//					.setParameter("superadminId", invoiceRequest.getSuperadminId())
//					.getResultList();
//			return results;
//		}else if(invoiceRequest.getRequestFor().equalsIgnoreCase("BYID")) {
//			List<InvoiceHeaderType> results = invoiceHeaderTypeDao.getEntityManager()
//					.createQuery("SELECT IY FROM InvoiceHeaderType IY WHERE IY.superadminId =:superadminId")
//					.setParameter("superadminId", invoiceRequest.getSuperadminId())
//					.getResultList();
//			return results;
//		}else {
//			return null;
//		}
//		
//	}

	@Transactional
	public InvoiceHeaderDetails getInvoiceHeaderBySuperAdminId(String superadminId) {

		CriteriaBuilder criteriaBuilder = invoiceHeaderDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<InvoiceHeaderDetails> criteriaQuery = criteriaBuilder.createQuery(InvoiceHeaderDetails.class);
		Root<InvoiceHeaderDetails> root = criteriaQuery.from(InvoiceHeaderDetails.class);
		Predicate restriction = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction);
		InvoiceHeaderDetails invoiceHeader = invoiceHeaderDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return invoiceHeader;
	}
	
	@Transactional
	public InvoiceHeaderDetails getInvoiceHeaderByNameAndSuperAdminId(String companyFirstName, String companyLastName, String superadminId) {

		CriteriaBuilder criteriaBuilder = invoiceHeaderDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<InvoiceHeaderDetails> criteriaQuery = criteriaBuilder.createQuery(InvoiceHeaderDetails.class);
		Root<InvoiceHeaderDetails> root = criteriaQuery.from(InvoiceHeaderDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("companyFirstName"), companyFirstName);
		Predicate restriction2 = criteriaBuilder.equal(root.get("companyLastName"), companyLastName);
		Predicate restriction3 = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction1, restriction2, restriction3);
		InvoiceHeaderDetails invoiceHeader = invoiceHeaderDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return invoiceHeader;
	}
	
	@Transactional
	public InvoiceHeaderDetails getInvoiceHeaderById(Long id) {

		CriteriaBuilder criteriaBuilder = invoiceHeaderDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<InvoiceHeaderDetails> criteriaQuery = criteriaBuilder.createQuery(InvoiceHeaderDetails.class);
		Root<InvoiceHeaderDetails> root = criteriaQuery.from(InvoiceHeaderDetails.class);
		Predicate restriction = criteriaBuilder.equal(root.get("id"), id);
		criteriaQuery.where(restriction);
		InvoiceHeaderDetails invoiceHeader = invoiceHeaderDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return invoiceHeader;
	}

	public InvoiceHeaderDetails getInvoiceHeaderDetailsByReqObj(InvoiceHeaderRequestObject invoiceRequest) {

		InvoiceHeaderDetails invoiceHeaderDetails = new InvoiceHeaderDetails();

		invoiceHeaderDetails.setLoginId(invoiceRequest.getCreatedBy());
		invoiceHeaderDetails.setStatus(Status.ACTIVE.name());
//		invoiceHeaderDetails.setReceiptDownloadUrl(invoiceRequest.getReceiptDownloadUrl());
		invoiceHeaderDetails.setInvoiceInitial(invoiceRequest.getInvoiceInitial());
		invoiceHeaderDetails.setSerialNumber(0L);
		invoiceHeaderDetails.setCompanyLogo(invoiceRequest.getCompanyLogo());
		invoiceHeaderDetails.setCompanyStamp(invoiceRequest.getCompanyStamp());
		invoiceHeaderDetails.setCompanyFirstName(invoiceRequest.getCompanyFirstName());
		invoiceHeaderDetails.setCompanyFirstNameColor(invoiceRequest.getCompanyFirstNameColor());
		invoiceHeaderDetails.setCompanyLastName(invoiceRequest.getCompanyLastName());
		invoiceHeaderDetails.setCompanyLastNameColor(invoiceRequest.getCompanyLastNameColor());
		invoiceHeaderDetails.setBackgroundColor(invoiceRequest.getBackgroundColor());
		invoiceHeaderDetails.setRegAddress(invoiceRequest.getRegAddress());
		invoiceHeaderDetails.setOfficeAddress(invoiceRequest.getOfficeAddress());
		invoiceHeaderDetails.setMobileNo(invoiceRequest.getMobileNo());
		invoiceHeaderDetails.setAlternateMobile(invoiceRequest.getMobileNo());
		invoiceHeaderDetails.setEmailId(invoiceRequest.getEmailId());
		invoiceHeaderDetails.setWebsite(invoiceRequest.getWebsite());
		invoiceHeaderDetails.setThankYouNote(invoiceRequest.getThankYouNote());
		invoiceHeaderDetails.setFooter(invoiceRequest.getFooter());
		
		invoiceHeaderDetails.setGstNumber(invoiceRequest.getGstNumber());
		invoiceHeaderDetails.setPanNumber(invoiceRequest.getPanNumber());
		invoiceHeaderDetails.setAccountHolderName(invoiceRequest.getAccountHolderName());
		invoiceHeaderDetails.setAccountNumber(invoiceRequest.getAccountNumber());
		invoiceHeaderDetails.setIfscCode(invoiceRequest.getIfscCode());
		invoiceHeaderDetails.setBankName(invoiceRequest.getBankName());
		invoiceHeaderDetails.setBranchName(invoiceRequest.getBranchName());

		invoiceHeaderDetails.setCreatedAt(new Date());
		invoiceHeaderDetails.setCreatedBy(invoiceRequest.getCreatedBy());
		invoiceHeaderDetails.setSuperadminId(invoiceRequest.getSuperadminId());

		return invoiceHeaderDetails;
	}

	@Transactional
	public InvoiceHeaderDetails saveInvoiceHeaderDetails(InvoiceHeaderDetails invoiceHeaderDetails) {
		invoiceHeaderDetailsDao.persist(invoiceHeaderDetails);
		return invoiceHeaderDetails;
	}
	
	
	public InvoiceHeaderDetails getUpdatedInvoiceHeaderDetailsByReqObj(InvoiceHeaderRequestObject invoiceRequest, InvoiceHeaderDetails invoiceHeaderDetails) {

		invoiceHeaderDetails.setStatus(Status.ACTIVE.name());
		invoiceHeaderDetails.setInvoiceInitial(invoiceRequest.getInvoiceInitial());
//		invoiceHeaderDetails.setReceiptDownloadUrl(invoiceRequest.getReceiptDownloadUrl());
		invoiceHeaderDetails.setCompanyLogo(invoiceRequest.getCompanyLogo());
//		invoiceHeaderDetails.setCompanyStamp(invoiceRequest.getCompanyStamp());
		invoiceHeaderDetails.setCompanyFirstName(invoiceRequest.getCompanyFirstName());
		invoiceHeaderDetails.setCompanyFirstNameColor(invoiceRequest.getCompanyFirstNameColor());
		invoiceHeaderDetails.setCompanyLastName(invoiceRequest.getCompanyLastName());
		invoiceHeaderDetails.setCompanyLastNameColor(invoiceRequest.getCompanyLastNameColor());
		invoiceHeaderDetails.setBackgroundColor(invoiceRequest.getBackgroundColor());
		invoiceHeaderDetails.setRegAddress(invoiceRequest.getRegAddress());
		invoiceHeaderDetails.setOfficeAddress(invoiceRequest.getOfficeAddress());
		invoiceHeaderDetails.setMobileNo(invoiceRequest.getMobileNo());
		invoiceHeaderDetails.setAlternateMobile(invoiceRequest.getMobileNo());
		invoiceHeaderDetails.setEmailId(invoiceRequest.getEmailId());
		invoiceHeaderDetails.setWebsite(invoiceRequest.getWebsite());
		invoiceHeaderDetails.setThankYouNote(invoiceRequest.getThankYouNote());
		invoiceHeaderDetails.setFooter(invoiceRequest.getFooter());
		
		invoiceHeaderDetails.setGstNumber(invoiceRequest.getGstNumber());
		invoiceHeaderDetails.setPanNumber(invoiceRequest.getPanNumber());
		invoiceHeaderDetails.setAccountHolderName(invoiceRequest.getAccountHolderName());
		invoiceHeaderDetails.setAccountNumber(invoiceRequest.getAccountNumber());
		invoiceHeaderDetails.setIfscCode(invoiceRequest.getIfscCode());
		invoiceHeaderDetails.setBankName(invoiceRequest.getBankName());
		invoiceHeaderDetails.setBranchName(invoiceRequest.getBranchName());

		invoiceHeaderDetails.setUpdatedAt(new Date());
//		invoiceHeaderDetails.setCreatedBy(invoiceRequest.getCreatedBy());
//		invoiceHeaderDetails.setSuperadminId(invoiceRequest.getSuperadminId());

		return invoiceHeaderDetails;
	}

	
	@Transactional
	public InvoiceHeaderDetails updateInvoiceHeaderDetails(InvoiceHeaderDetails invoiceHeaderDetails) {
		invoiceHeaderDetailsDao.update(invoiceHeaderDetails);
		return invoiceHeaderDetails;
	}
	
	@SuppressWarnings("unchecked")
	public List<InvoiceHeaderDetails> getInvoiceHeaderList(InvoiceHeaderRequestObject invoiceRequest) {
		if(invoiceRequest.getRequestFor().equalsIgnoreCase("BYSUPERADMINID")) {
			List<InvoiceHeaderDetails> results = invoiceHeaderDetailsDao.getEntityManager()
					.createQuery("SELECT IH FROM InvoiceHeaderDetails IH WHERE IH.superadminId =:superadminId AND status =:status")
					.setParameter("superadminId", invoiceRequest.getSuperadminId())
					.setParameter("status", Status.ACTIVE.name())
					.getResultList();
			return results;
			
		}else if(invoiceRequest.getRequestFor().equalsIgnoreCase("BYID")) {
			List<InvoiceHeaderDetails> results = invoiceHeaderDetailsDao.getEntityManager()
					.createQuery("SELECT IH FROM InvoiceHeaderDetails IH WHERE IH.id =:id")
					.setParameter("id", invoiceRequest.getId())
					.getResultList();
			return results;
		}
		return null;
	}
	

	

	

	

}
