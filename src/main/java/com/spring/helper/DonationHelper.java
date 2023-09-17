package com.spring.helper;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spring.constant.Constant;
import com.spring.dao.DonationDetailsDao;
import com.spring.dao.LeadDetailsDao;
import com.spring.entities.DonationDetails;
import com.spring.entities.LeadDetails;
import com.spring.enums.RequestFor;
import com.spring.enums.RoleType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.ArticleRequestObject;
import com.spring.object.request.DonationRequestObject;



@Component
public class DonationHelper {
	
	@Autowired
	private DonationDetailsDao donationDetailsDao;
	
	public void validateLeadRequest(DonationRequestObject donationRequestObject) 
			throws BizException
	{ 
		if(donationRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null"); 
		}
	}
	
	
//	@Transactional
//	public LeadDetails getLeadDetailsByCustomerMobile(String customerMobile) {
//		
//	    CriteriaBuilder criteriaBuilder = leadDetailsDao.getSession().getCriteriaBuilder();
//	    CriteriaQuery<LeadDetails> criteriaQuery = criteriaBuilder.createQuery(LeadDetails.class);
//	    Root<LeadDetails> root = criteriaQuery.from(LeadDetails.class);
//	    Predicate restriction = criteriaBuilder.equal(root.get("customerMobile"), customerMobile);
//	    criteriaQuery.where(restriction);
//	    LeadDetails leadDetails = leadDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
//	    return leadDetails;
//	}


	public DonationDetails getDonationDetailsByReqObj(DonationRequestObject donationRequest) {
		
		DonationDetails donationDetails = new DonationDetails();
		
		donationDetails.setDonorName(donationRequest.getDonorName());
		donationDetails.setMobileNumber(donationRequest.getMobileNumber());
		donationDetails.setEmailId(donationRequest.getEmailId());
		donationDetails.setPanNumber(donationRequest.getPanNumber());
		donationDetails.setAddress(donationRequest.getAddress());
		donationDetails.setAmount(donationRequest.getAmount());
		donationDetails.setTransactionId(donationRequest.getTransactionId());
		donationDetails.setPaymentMode(donationRequest.getPaymentMode());
		donationDetails.setPaymentType(donationRequest.getPaymentType());
		donationDetails.setInvoiceDownloadStatus(Status.ACTIVE.name());
		donationDetails.setStatus(Status.ACTIVE.name());
		donationDetails.setNotes(donationRequest.getNotes());
		donationDetails.setLoginId(donationRequest.getLoginId());
		
		if(donationRequest.getCreatedBy().isEmpty()) 
			donationDetails.setCreatedBy(donationRequest.getLoginId());
		else 
			donationDetails.setCreatedBy(donationRequest.getCreatedBy());
		
		
		donationDetails.setSuperadminId(donationRequest.getSuperadminId());
		donationDetails.setCreatedAt(new Date());
		donationDetails.setUpdatedAt(new Date());
		
		return donationDetails;
	}
	
	@Transactional
	public DonationDetails saveDonationDetails(DonationDetails donationDetails) { 
		donationDetailsDao.persist(donationDetails);
		return donationDetails;
	}


	@SuppressWarnings("unchecked")
	public List<DonationDetails> getDonationListBySuperadmin(DonationRequestObject donationRequest) {
		if (donationRequest.getRequestedFor().equals(RequestFor.ALL.name())) {
			List<DonationDetails> results = donationDetailsDao.getEntityManager().createQuery(
//					"SELECT DD FROM DonationDetails DD WHERE DD.superadminId =:superadminId ORDER BY DD.id DESC LIMIT 0,200")
//					.setParameter("superadminId", donationRequest.getSuperadminId()).getResultList();
					"SELECT DD FROM DonationDetails DD  ORDER BY DD.id DESC")
					.getResultList();
			return results;
		}
		if (donationRequest.getRequestedFor().equals(RequestFor.BYDATE.name())) {
			List<DonationDetails> results = donationDetailsDao.getEntityManager().createQuery(
					"SELECT DD FROM DonationDetails DD WHERE DD.superadminId =:superadminId AND DD.createdAt BETWEEN :firstDate AND :lastDate ORDER BY DD.id DESC")
					.setParameter("superadminId", donationRequest.getSuperadminId())
					.setParameter("firstDate", donationRequest.getFirstDate(), TemporalType.DATE)
					.setParameter("lastDate", donationRequest.getLastDate(), TemporalType.DATE)
					.getResultList();
			return results;
		}
		if (donationRequest.getRequestedFor().equals(RequestFor.CREATEDBY.name())) {
			List<DonationDetails> results = donationDetailsDao.getEntityManager().createQuery(
					"SELECT DD FROM DonationDetails DD WHERE DD.superadminId =:superadminId AND DD.createdBy =:createdBy AND DD.createdAt BETWEEN :firstDate AND :lastDate ORDER BY DD.id DESC")
					.setParameter("superadminId", donationRequest.getSuperadminId())
					.setParameter("createdBy", donationRequest.getCreatedBy())
					.setParameter("firstDate", donationRequest.getFirstDate(), TemporalType.DATE)
					.setParameter("lastDate", donationRequest.getLastDate(), TemporalType.DATE)
					.getResultList();
			return results;
		}
		return null;

	}
	
	
	@SuppressWarnings("unchecked")
	public List<DonationDetails> countDonnation(DonationRequestObject donationRequest) {
		if(donationRequest.getRoleType().equals(RoleType.SUPERADMIN.name())) {
  
		}
		
		
		
		
		
		if (donationRequest.getRequestedFor().equals(RequestFor.ALL.name())) {
			List<DonationDetails> results = donationDetailsDao.getEntityManager().createQuery(
//					"SELECT DD FROM DonationDetails DD WHERE DD.superadminId =:superadminId ORDER BY DD.id DESC LIMIT 0,200")
//					.setParameter("superadminId", donationRequest.getSuperadminId()).getResultList();
					"SELECT DD FROM DonationDetails DD  ORDER BY DD.id DESC")
					.getResultList();
			return results;
		}
		if (donationRequest.getRequestedFor().equals(RequestFor.BYDATE.name())) {
			List<DonationDetails> results = donationDetailsDao.getEntityManager().createQuery(
					"SELECT DD FROM DonationDetails DD WHERE DD.superadminId =:superadminId AND DD.createdAt BETWEEN :firstDate AND :lastDate ORDER BY DD.id DESC")
					.setParameter("superadminId", donationRequest.getSuperadminId())
					.setParameter("firstDate", donationRequest.getFirstDate(), TemporalType.DATE)
					.setParameter("lastDate", donationRequest.getLastDate(), TemporalType.DATE)
					.getResultList();
			return results;
		}
		if (donationRequest.getRequestedFor().equals(RequestFor.CREATEDBY.name())) {
			List<DonationDetails> results = donationDetailsDao.getEntityManager().createQuery(
					"SELECT DD FROM DonationDetails DD WHERE DD.superadminId =:superadminId AND DD.createdBy =:createdBy AND DD.createdAt BETWEEN :firstDate AND :lastDate ORDER BY DD.id DESC")
					.setParameter("superadminId", donationRequest.getSuperadminId())
					.setParameter("createdBy", donationRequest.getCreatedBy())
					.setParameter("firstDate", donationRequest.getFirstDate(), TemporalType.DATE)
					.setParameter("lastDate", donationRequest.getLastDate(), TemporalType.DATE)
					.getResultList();
			return results;
		}
		return null;

	}

}
