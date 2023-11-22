package com.spring.helper;

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
import com.spring.dao.InvoiceDetailsDao;
import com.spring.dao.InvoiceHeaderDetailsDao;
import com.spring.dao.InvoiceNumberDao;
import com.spring.entities.InvoiceDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.entities.InvoiceNumber;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.InvoiceRequestObject;

@Component
public class InvoiceHelper {

	@Autowired
	private InvoiceHeaderDetailsDao invoiceHeaderDetailsDao;

	@Autowired
	private InvoiceNumberDao invoiceNumberDao;

	@Autowired
	private InvoiceDetailsDao invoiceDetailsDao;

	public void validateInvoiceRequest(InvoiceRequestObject invoiceRequestObject) throws BizException {
		if (invoiceRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	@Transactional
	public InvoiceNumber getInvoiceNumberByInvoiceNo(InvoiceRequestObject invoiceRequest) {

		CriteriaBuilder criteriaBuilder = invoiceNumberDao.getSession().getCriteriaBuilder();
		CriteriaQuery<InvoiceNumber> criteriaQuery = criteriaBuilder.createQuery(InvoiceNumber.class);
		Root<InvoiceNumber> root = criteriaQuery.from(InvoiceNumber.class);
		Predicate restriction = criteriaBuilder.equal(root.get("invoiceNo"), invoiceRequest.getInvoiceNo());
		restriction = criteriaBuilder.equal(root.get("superadminId"), invoiceRequest.getSuperadminId());
		criteriaQuery.where(restriction);
		InvoiceNumber invoiceNumber = invoiceNumberDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return invoiceNumber;
	}
	
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

	public InvoiceHeaderDetails getInvoiceHeaderDetailsByReqObj(InvoiceRequestObject invoiceRequest) {

		InvoiceHeaderDetails invoiceHeaderDetails = new InvoiceHeaderDetails();

		invoiceHeaderDetails.setLoginId(invoiceRequest.getCreatedBy());
		invoiceHeaderDetails.setStatus(Status.ACTIVE.name());
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
	
	
	public InvoiceHeaderDetails getUpdatedInvoiceHeaderDetailsByReqObj(InvoiceRequestObject invoiceRequest, InvoiceHeaderDetails invoiceHeaderDetails) {

		invoiceHeaderDetails.setStatus(Status.ACTIVE.name());
		invoiceHeaderDetails.setInvoiceInitial(invoiceRequest.getInvoiceInitial());
		invoiceHeaderDetails.setCompanyLogo(invoiceRequest.getCompanyLogo());
//		invoiceHeaderDetails.setCompanyStamp(invoiceRequest.getCompanyStamp());
		invoiceHeaderDetails.setCompanyFirstName(invoiceRequest.getCompanyFirstName());
		invoiceHeaderDetails.setCompanyFirstNameColor(invoiceRequest.getCompanyFirstNameColor());
		invoiceHeaderDetails.setCompanyLastName(invoiceRequest.getCompanyLastName());
		invoiceHeaderDetails.setCompanyLastNameColor(invoiceRequest.getCompanyLastNameColor());
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
	public List<InvoiceHeaderDetails> getInvoiceHeaderList(InvoiceRequestObject invoiceRequest) {
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
	

	public InvoiceNumber getInvoiceNumberByReqObj(InvoiceRequestObject invoiceRequest) {

		InvoiceNumber invoiceNumber = new InvoiceNumber();

		invoiceNumber.setInvoiceNo(invoiceRequest.getInvoiceNo());
		invoiceNumber.setCustomerId(invoiceRequest.getCustomerId());
		invoiceNumber.setCustomerName(invoiceRequest.getCustomerName());
		invoiceNumber.setTotalItem(invoiceRequest.getTotalItem());
		invoiceNumber.setTotalAmount(invoiceRequest.getTotalAmount());
		invoiceNumber.setStatus(Status.DUE.name());
		invoiceNumber.setPaidBy(invoiceRequest.getPaidBy());
		invoiceNumber.setPaidDate(invoiceRequest.getPaidDate());
		invoiceNumber.setTransactionId(invoiceRequest.getTransactionId());

		invoiceNumber.setCreatedAt(new Date());
		invoiceNumber.setCreatedBy(invoiceRequest.getCreatedBy());
		invoiceNumber.setSuperadminId(invoiceRequest.getSuperadminId());

		return invoiceNumber;
	}

	@Transactional
	public InvoiceNumber saveInvoiceNumber(InvoiceNumber invoiceNumber) {
		invoiceNumberDao.persist(invoiceNumber);
		return invoiceNumber;
	}
	
	@Transactional
	public InvoiceNumber updateInvoiceNumber(InvoiceNumber invoiceNumber) {
		invoiceNumberDao.update(invoiceNumber);
		return invoiceNumber;
	}


	public InvoiceDetails getInvoiceDetailsByReqObj(InvoiceDetails detailsRequest, InvoiceRequestObject invoiceRequest) {

		InvoiceDetails invoiceDetails = new InvoiceDetails();

		invoiceDetails.setInvoiceNo(invoiceRequest.getInvoiceNo());
		invoiceDetails.setItemName(detailsRequest.getItemName());
		invoiceDetails.setRate(detailsRequest.getRate());
		invoiceDetails.setQuantity(detailsRequest.getQuantity());
		invoiceDetails.setAmount(detailsRequest.getAmount());
		invoiceDetails.setStatus(Status.DUE.name());

		invoiceDetails.setCreatedAt(new Date());
		invoiceDetails.setCreatedBy(invoiceRequest.getCreatedBy());
		invoiceDetails.setSuperadminId(invoiceRequest.getSuperadminId());

		return invoiceDetails;
	}

	@Transactional
	public InvoiceDetails saveInvoiceDetails(InvoiceDetails invoiceDetails) {
		invoiceDetailsDao.persist(invoiceDetails);
		return invoiceDetails;
	}
	
	

	@SuppressWarnings("unchecked")
	public List<InvoiceNumber> getInvoiceNumberList(InvoiceRequestObject invoiceRequest) {
		if(invoiceRequest.getRequestFor().equals("SUPALL")) {
			List<InvoiceNumber> results = invoiceNumberDao.getEntityManager()
					.createQuery("SELECT UD FROM InvoiceNumber UD WHERE UD.superadminId =:superadminId ORDER BY UD.id DESC")
					.setParameter("superadminId", invoiceRequest.getSuperadminId())
					.getResultList();
			return results; 
		}else if(invoiceRequest.getRequestFor().equals("LOGINID")) {
			List<InvoiceNumber> results = invoiceNumberDao.getEntityManager()
					.createQuery("SELECT UD FROM InvoiceNumber UD WHERE UD.createdBy =:createdBy AND UD.superadminId =:superadminId OREDER BY UD.id DESC")
					.setParameter("createdBy", invoiceRequest.getCreatedBy())
					.setParameter("superadminId", invoiceRequest.getSuperadminId())
					.getResultList();
			return results; 
		}
		return null;	
	}	

	@SuppressWarnings("unchecked")
	public List<InvoiceDetails> getInvoiceDetailsList(InvoiceRequestObject invoiceRequest) {
		if (invoiceRequest.getRequestFor().equals("SUPALL")) {
			List<InvoiceDetails> results = invoiceDetailsDao.getEntityManager()
					.createQuery("SELECT UD FROM InvoiceDetails UD WHERE UD.invoiceNo =:invoiceNo AND UD.superadminId =:superadminId")
					.setParameter("invoiceNo", invoiceRequest.getInvoiceNo())
					.setParameter("superadminId", invoiceRequest.getSuperadminId())
					.getResultList();
			return results;
		} else if (invoiceRequest.getRequestFor().equals("LOGINID")) {
			List<InvoiceDetails> results = invoiceDetailsDao.getEntityManager()
					.createQuery("SELECT UD FROM InvoiceDetails UD WHERE UD.invoiceNo =:invoiceNo AND UD.createdBy =:createdBy AND UD.superadminId =:superadminId ODER BY UD.id")
					.setParameter("invoiceNo", invoiceRequest.getInvoiceNo())
					.setParameter("createdBy", invoiceRequest.getCreatedBy())
					.setParameter("superadminId", invoiceRequest.getSuperadminId())
					.getResultList();
			return results;
		}
		return null;

	}



	

	

}
