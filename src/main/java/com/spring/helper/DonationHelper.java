package com.spring.helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
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
import com.spring.entities.DonationDetails;
import com.spring.entities.OptionTypeDetails;
import com.spring.enums.RequestFor;
import com.spring.enums.RoleType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.DonationRequestObject;



@Component
public class DonationHelper {
	
	@Autowired
	private DonationDetailsDao donationDetailsDao;
	
	
	public void validateDonationRequest(DonationRequestObject donationRequestObject) 
			throws BizException
	{ 
		if(donationRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null"); 
		}
	}
	
	@Transactional
	public DonationDetails getDonationDetailsByIdAndSuperadminId(Long id, String superadminId) {

		CriteriaBuilder criteriaBuilder = donationDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<DonationDetails> criteriaQuery = criteriaBuilder.createQuery(DonationDetails.class);
		Root<DonationDetails> root = criteriaQuery.from(DonationDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("id"), id);
		Predicate restriction2 = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction1,restriction2);
		DonationDetails donationDetails = donationDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return donationDetails;
	}
	

	public DonationDetails getDonationDetailsByReqObj(DonationRequestObject donationRequest) {
		
		DonationDetails donationDetails = new DonationDetails();
		
		donationDetails.setDonorName(donationRequest.getDonorName());
		donationDetails.setMobileNumber(donationRequest.getMobileNumber());
		donationDetails.setEmailId(donationRequest.getEmailId());
		donationDetails.setPanNumber(donationRequest.getPanNumber());
		donationDetails.setAddress(donationRequest.getAddress());
		donationDetails.setProgramName(donationRequest.getProgramName());
		donationDetails.setAmount(donationRequest.getAmount());
		donationDetails.setReceiptNumber(donationRequest.getReceiptNumber());
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
	
	@Transactional
	public DonationDetails updateDonationDetails(DonationDetails donationDetails) { 
		donationDetailsDao.update(donationDetails);
		return donationDetails;
	}
	
	@SuppressWarnings("unchecked")
	public List<DonationDetails> getDonationListBySuperadmin(DonationRequestObject donationRequest) {

		List<DonationDetails> results = new ArrayList<>();
		results = donationDetailsDao.getEntityManager().createQuery(
				"SELECT DD FROM DonationDetails DD WHERE DD.superadminId =:superadminId AND DD.createdAt BETWEEN :firstDate AND :lastDate ORDER BY DD.id DESC")
				.setParameter("superadminId", donationRequest.getSuperadminId())
				.setParameter("firstDate", donationRequest.getFirstDate(), TemporalType.DATE)
				.setParameter("lastDate", donationRequest.getLastDate(), TemporalType.DATE)
				.getResultList();
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<DonationDetails> getDonationListCreatedBy(DonationRequestObject donationRequest) {

		List<DonationDetails> results = new ArrayList<>();
		results = donationDetailsDao.getEntityManager().createQuery(
				"SELECT DD FROM DonationDetails DD WHERE DD.createdBy =:createdBy DD.superadminId =:superadminId AND DD.createdAt BETWEEN :firstDate AND :lastDate ORDER BY DD.id DESC")
				.setParameter("createdBy", donationRequest.getCreatedBy())
				.setParameter("superadminId", donationRequest.getSuperadminId())
				.setParameter("firstDate", donationRequest.getFirstDate(), TemporalType.DATE)
				.setParameter("lastDate", donationRequest.getLastDate(), TemporalType.DATE)
				.getResultList();
		return results;
	}

//	@SuppressWarnings("unchecked")
//	public List<DonationDetails> getDonationListBySuperadmin(DonationRequestObject donationRequest) {
//		
//		List<DonationDetails> results = new ArrayList<>();
//		if (donationRequest.getRequestedFor().equals(RequestFor.ALL.name())) {
//			results = donationDetailsDao.getEntityManager().createQuery(
//					"SELECT DD FROM DonationDetails DD WHERE DD.superadminId =:superadminId ORDER BY DD.id DESC")
//					.setParameter("superadminId", donationRequest.getSuperadminId())
//					.getResultList();
//			return results;
//		}
//		if (donationRequest.getRequestedFor().equals(RequestFor.BYDATE.name())) {
//			results = donationDetailsDao.getEntityManager().createQuery(
//					"SELECT DD FROM DonationDetails DD WHERE DD.superadminId =:superadminId AND DD.createdAt BETWEEN :firstDate AND :lastDate ORDER BY DD.id DESC")
//					.setParameter("superadminId", donationRequest.getSuperadminId())
//					.setParameter("firstDate", donationRequest.getFirstDate(), TemporalType.DATE)
//					.setParameter("lastDate", donationRequest.getLastDate(), TemporalType.DATE)
//					.getResultList();
//			return results;
//		}
//		if (donationRequest.getRequestedFor().equals(RequestFor.CREATEDBY.name())) {
//			 results = donationDetailsDao.getEntityManager().createQuery(
//					"SELECT DD FROM DonationDetails DD WHERE DD.superadminId =:superadminId AND DD.createdBy =:createdBy AND DD.createdAt BETWEEN :firstDate AND :lastDate ORDER BY DD.id DESC")
//					.setParameter("superadminId", donationRequest.getSuperadminId())
//					.setParameter("createdBy", donationRequest.getCreatedBy())
//					.setParameter("firstDate", donationRequest.getFirstDate(), TemporalType.DATE)
//					.setParameter("lastDate", donationRequest.getLastDate(), TemporalType.DATE)
//					.getResultList();
//			return results;
//		}
//		return results;
//	}
	

	public Object[] getCountAndSum(DonationRequestObject donationRequest, Date firstDate, Date secondDate) {
		Object[] count = new Object[] {};
		if (donationRequest.getRoleType().equals(RoleType.SUPERADMIN.name())) {
			count = (Object[]) donationDetailsDao.getEntityManager().createQuery(
					"SELECT COUNT(*) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId")
					.setParameter("firstDate", firstDate, TemporalType.DATE)
					.setParameter("lastDate", secondDate, TemporalType.DATE)
					.setParameter("superadminId", donationRequest.getSuperadminId())
					.getSingleResult();
			return count;
		} else {
			count = (Object[]) donationDetailsDao.getEntityManager().createQuery(
					"SELECT COUNT(*) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.createdBy =:createdBy")
					.setParameter("firstDate", firstDate, TemporalType.DATE)
					.setParameter("lastDate", secondDate, TemporalType.DATE)
					.setParameter("superadminId", donationRequest.getSuperadminId())
					.setParameter("createdBy", donationRequest.getCreatedBy())
					.getSingleResult();
			return count;
		}
	}
}
