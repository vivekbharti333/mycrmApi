package com.spring.helper;

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
import com.spring.entities.UserDetails;
import com.spring.enums.RoleType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.DonationRequestObject;



@Component
public class DonationHelper {
	
	@Autowired
	private DonationDetailsDao donationDetailsDao;
	
	@Autowired
	private UserHelper userHelper;
	
	
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
	
	@Transactional
	public DonationDetails getDonationDetailsByReferenceNo(String receiptNumber) {

		CriteriaBuilder criteriaBuilder = donationDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<DonationDetails> criteriaQuery = criteriaBuilder.createQuery(DonationDetails.class);
		Root<DonationDetails> root = criteriaQuery.from(DonationDetails.class);
		Predicate restriction = criteriaBuilder.equal(root.get("receiptNumber"), receiptNumber);
		criteriaQuery.where(restriction);
		DonationDetails donationDetails = donationDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return donationDetails;
	}
	

	public DonationDetails getDonationDetailsByReqObj(DonationRequestObject donationRequest) {
		
		DonationDetails donationDetails = new DonationDetails();
		
		UserDetails userDetails = userHelper.getUserDetailsByLoginIdAndSuperadminId(donationRequest.getLoginId(), donationRequest.getSuperadminId());
		if(userDetails != null) {
			donationDetails.setCreatedbyName(userDetails.getFirstName()+" "+userDetails.getLastName());
			
			if(userDetails.getRoleType().equalsIgnoreCase(RoleType.SUPERADMIN.name()) 
					|| userDetails.getRoleType().equalsIgnoreCase(RoleType.ADMIN.name()) 
					|| userDetails.getRoleType().equalsIgnoreCase(RoleType.TEAM_LEADER.name())) 
			{
				donationDetails.setTeamLeaderId(donationRequest.getLoginId());
			}else {
				donationDetails.setTeamLeaderId(userDetails.getCreatedBy());
			}
			
//			donationDetails.setTeamLeaderId(userDetails.getCreatedBy());
		}
		
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
		donationDetails.setInvoiceDownloadStatus(Status.NO.name());
		donationDetails.setStatus(Status.ACTIVE.name());
		donationDetails.setNotes(donationRequest.getNotes());
		donationDetails.setLoginId(donationRequest.getLoginId());
		donationDetails.setInvoiceHeaderDetailsId(donationRequest.getInvoiceHeaderDetailsId());
		donationDetails.setInvoiceHeaderName(donationRequest.getInvoiceHeaderName());
		
//		if(donationRequest.getCreatedBy().isEmpty()) 
			donationDetails.setCreatedBy(donationRequest.getLoginId());
//		else 
			donationDetails.setCreatedBy(donationRequest.getLoginId());
		
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
	public List<DonationDetails> getDonationListByTeamLeaderId(DonationRequestObject donationRequest) {

		List<DonationDetails> results = new ArrayList<>();
		results = donationDetailsDao.getEntityManager().createQuery(
				"SELECT DD FROM DonationDetails DD WHERE DD.teamLeaderId =:teamLeaderId AND DD.createdAt BETWEEN :firstDate AND :lastDate ORDER BY DD.id DESC")
				.setParameter("teamLeaderId", donationRequest.getCreatedBy())
				.setParameter("firstDate", donationRequest.getFirstDate(), TemporalType.DATE)
				.setParameter("lastDate", donationRequest.getLastDate(), TemporalType.DATE)
				.getResultList();
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<DonationDetails> getDonationListCreatedBy(DonationRequestObject donationRequest) {

		List<DonationDetails> results = new ArrayList<>();
		results = donationDetailsDao.getEntityManager().createQuery(
				"SELECT DD FROM DonationDetails DD WHERE DD.createdBy = :createdBy AND DD.superadminId = :superadminId AND DD.createdAt BETWEEN :firstDate AND :lastDate ORDER BY DD.id DESC")
				.setParameter("createdBy", donationRequest.getCreatedBy())
				.setParameter("superadminId", donationRequest.getSuperadminId())
				.setParameter("firstDate", donationRequest.getFirstDate(), TemporalType.DATE)
				.setParameter("lastDate", donationRequest.getLastDate(), TemporalType.DATE)
				.getResultList();
		return results;
	}


	public Object[] getCountAndSum(DonationRequestObject donationRequest, Date firstDate, Date secondDate) {
		Object[] count = new Object[] {};
		if (donationRequest.getRoleType().equals(RoleType.SUPERADMIN.name())) {
			count = (Object[]) donationDetailsDao.getEntityManager().createQuery(
					"SELECT COUNT(id) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.status =:status")
					.setParameter("firstDate", firstDate, TemporalType.DATE)
					.setParameter("lastDate", secondDate, TemporalType.DATE)
					.setParameter("superadminId", donationRequest.getSuperadminId())
					.setParameter("status", Status.ACTIVE.name())
					.getSingleResult();
			return count;
		} else if (donationRequest.getRoleType().equals(RoleType.TEAM_LEADER.name())) {
			count = (Object[]) donationDetailsDao.getEntityManager().createQuery(
					"SELECT COUNT(id) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.teamLeaderId =:teamLeaderId AND DD.status =:status")
					.setParameter("firstDate", firstDate, TemporalType.DATE)
					.setParameter("lastDate", secondDate, TemporalType.DATE)
					.setParameter("superadminId", donationRequest.getSuperadminId())
					.setParameter("teamLeaderId", donationRequest.getCreatedBy())
					.setParameter("status", Status.ACTIVE.name())
					.getSingleResult();
			return count;
		}
		 else {
				count = (Object[]) donationDetailsDao.getEntityManager().createQuery(
						"SELECT COUNT(id) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.createdBy =:createdBy AND DD.status =:status ")
						.setParameter("firstDate", firstDate, TemporalType.DATE)
						.setParameter("lastDate", secondDate, TemporalType.DATE)
						.setParameter("superadminId", donationRequest.getSuperadminId())
						.setParameter("createdBy", donationRequest.getCreatedBy())
						.setParameter("status", Status.ACTIVE.name())
						.getSingleResult();
				return count;
			}
	}

	@SuppressWarnings("unchecked")
	public List<DonationDetails> getDonationListByReceiptNumber(String receiptNumber) {
		List<DonationDetails> results = new ArrayList<>();
		results = donationDetailsDao.getEntityManager().createQuery(
				"SELECT DD FROM DonationDetails DD WHERE DD.receiptNumber =:receiptNumber")
				.setParameter("receiptNumber", receiptNumber)
				.getResultList();
		return results;
	}
}
