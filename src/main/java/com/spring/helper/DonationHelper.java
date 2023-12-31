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
		
		donationDetails.setInvoiceNumber(donationRequest.getInvoiceNumber());	
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
		donationDetails.setCreatedBy(donationRequest.getLoginId());
		donationDetails.setCreatedbyName(donationRequest.getCreatedbyName());
		donationDetails.setTeamLeaderId(donationRequest.getTeamLeaderId());
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
	
	
	@SuppressWarnings("unchecked")
	public List<DonationDetails> getDonationListBySearchKey(DonationRequestObject donationRequest) {

	    List<DonationDetails> results = new ArrayList<>();
	    if (donationRequest.getRoleType().equalsIgnoreCase(RoleType.SUPERADMIN.name())) {
	        results = donationDetailsDao.getEntityManager().createQuery(
	                "SELECT DD FROM DonationDetails DD WHERE DD.superadminId = :superadminId "
	                        + "AND (DD.donorName LIKE :searchParam "
	                        + "OR DD.mobileNumber LIKE :searchParam OR DD.panNumber LIKE :searchParam "
	                        + "OR DD.programName LIKE :searchParam OR DD.amount LIKE :searchParam "
	                        + "OR DD.transactionId LIKE :searchParam OR DD.paymentMode LIKE :searchParam "
	                        + "OR DD.receiptNumber LIKE :searchParam OR DD.invoiceHeaderName LIKE :searchParam "
	                        + "OR DD.invoiceNumber LIKE :searchParam OR DD.invoiceDownloadStatus LIKE :searchParam "
	                        + "OR DD.createdbyName LIKE :searchParam) ORDER BY DD.id DESC")
	                .setParameter("superadminId", donationRequest.getSuperadminId())
	                .setParameter("searchParam", donationRequest.getSearchParam())
	                .getResultList();
	        return results;
	    } else if (donationRequest.getRoleType().equalsIgnoreCase(RoleType.TEAM_LEADER.name())) {
	        results = donationDetailsDao.getEntityManager().createQuery(
	                "SELECT DD FROM DonationDetails DD WHERE DD.teamLeaderId = :teamLeaderId "
	                        + "AND DD.superadminId = :superadminId "
	                        + "AND (DD.donorName LIKE :searchParam "
	                        + "OR DD.mobileNumber LIKE :searchParam OR DD.panNumber LIKE :searchParam "
	                        + "OR DD.programName LIKE :searchParam OR DD.amount LIKE :searchParam "
	                        + "OR DD.transactionId LIKE :searchParam OR DD.paymentMode LIKE :searchParam "
	                        + "OR DD.receiptNumber LIKE :searchParam OR DD.invoiceHeaderName LIKE :searchParam "
	                        + "OR DD.invoiceNumber LIKE :searchParam OR DD.invoiceDownloadStatus LIKE :searchParam "
	                        + "OR DD.createdbyName LIKE :searchParam) ORDER BY DD.id DESC")
	                .setParameter("teamLeaderId", donationRequest.getCreatedBy())
	                .setParameter("superadminId", donationRequest.getSuperadminId())
	                .setParameter("searchParam", donationRequest.getSearchParam())
	                .getResultList();
	        return results;
	    } else if (donationRequest.getRoleType().equalsIgnoreCase(RoleType.FUNDRAISING_OFFICER.name())) {
	        results = donationDetailsDao.getEntityManager().createQuery(
	                "SELECT DD FROM DonationDetails DD WHERE DD.createdBy = :createdBy "
	                        + "AND DD.superadminId = :superadminId "
	                        + "AND (DD.donorName LIKE :searchParam "
	                        + "OR DD.mobileNumber LIKE :searchParam OR DD.panNumber LIKE :searchParam "
	                        + "OR DD.programName LIKE :searchParam OR DD.amount LIKE :searchParam "
	                        + "OR DD.transactionId LIKE :searchParam OR DD.paymentMode LIKE :searchParam "
	                        + "OR DD.receiptNumber LIKE :searchParam OR DD.invoiceHeaderName LIKE :searchParam "
	                        + "OR DD.invoiceNumber LIKE :searchParam OR DD.invoiceDownloadStatus LIKE :searchParam "
	                        + "OR DD.createdbyName LIKE :searchParam) ORDER BY DD.id DESC")
	                .setParameter("createdBy", donationRequest.getCreatedBy())
	                .setParameter("superadminId", donationRequest.getSuperadminId())
	                .setParameter("searchParam", donationRequest.getSearchParam())
	                .getResultList();
	        return results;
	    }
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
		} else {
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

	@SuppressWarnings("unchecked")
	public List<DonationDetails> getDonationCountAndAmountGroupByName(DonationRequestObject donationRequest, Date firstDate, Date secondDate) {
		List<DonationDetails> results = new ArrayList<>();
		if (donationRequest.getRoleType().equals(RoleType.SUPERADMIN.name())) {
			results = donationDetailsDao.getEntityManager().createQuery(
//					"SELECT DD.createdbyName, COUNT(id) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.status =:status GROUP BY DD.createdbyName")
				"SELECT DD.createdbyName, COUNT(DD.id) AS count, SUM(DD.amount) AS amount, UD.userPicture FROM DonationDetails DD "
				+ "INNER JOIN UserDetails UD ON DD.loginId = UD.loginId WHERE DD.createdAt BETWEEN :firstDate AND :lastDate "
				+ "AND DD.superadminId = :superadminId AND DD.status = :status GROUP BY DD.createdbyName, UD.userPicture")
					.setParameter("firstDate", firstDate, TemporalType.DATE)
					.setParameter("lastDate", secondDate, TemporalType.DATE)
					.setParameter("superadminId", donationRequest.getSuperadminId())
					.setParameter("status", Status.ACTIVE.name())
					.getResultList();
			return results;
		} else if (donationRequest.getRoleType().equals(RoleType.TEAM_LEADER.name())) {
			results = donationDetailsDao.getEntityManager().createQuery(
//					"SELECT DD.createdbyName, COUNT(id) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.teamLeaderId = :teamLeaderId AND DD.status =:status GROUP BY DD.createdbyName")
					"SELECT DD.createdbyName, COUNT(DD.id) AS count, SUM(DD.amount) AS amount, UD.userPicture FROM DonationDetails DD "
					+ "INNER JOIN UserDetails UD ON DD.loginId = UD.loginId WHERE DD.createdAt BETWEEN :firstDate AND :lastDate "
					+ "AND DD.teamLeaderId = :teamLeaderId AND DD.status = :status GROUP BY DD.createdbyName, UD.userPicture")
					.setParameter("firstDate", firstDate, TemporalType.DATE)
					.setParameter("lastDate", secondDate, TemporalType.DATE)
					.setParameter("teamLeaderId", donationRequest.getTeamLeaderId())
					.setParameter("status", Status.ACTIVE.name())
					.getResultList();
			return results;
		}else {
			
		}
		return results;
	}

		@SuppressWarnings("unchecked")
		public List<DonationDetails> getDonationPaymentModeCountAndAmountGroupByName(DonationRequestObject donationRequest, Date firstDate, Date secondDate) {
			List<DonationDetails> results = new ArrayList<>();
			if (donationRequest.getRoleType().equals(RoleType.SUPERADMIN.name())) {
				results = donationDetailsDao.getEntityManager().createQuery(
						"SELECT DD.paymentMode, COUNT(id) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.status =:status GROUP BY DD.paymentMode")
						.setParameter("firstDate", firstDate, TemporalType.DATE)
						.setParameter("lastDate", secondDate, TemporalType.DATE)
						.setParameter("superadminId", donationRequest.getSuperadminId())
						.setParameter("status", Status.ACTIVE.name())
						.getResultList();
				return results;
			} else if (donationRequest.getRoleType().equals(RoleType.TEAM_LEADER.name())) {
				results = donationDetailsDao.getEntityManager().createQuery(
						"SELECT DD.paymentMode, COUNT(id) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.teamLeaderId = :teamLeaderId AND DD.status =:status GROUP BY DD.paymentMode")
						.setParameter("firstDate", firstDate, TemporalType.DATE)
						.setParameter("lastDate", secondDate, TemporalType.DATE)
						.setParameter("teamLeaderId", donationRequest.getTeamLeaderId())
						.setParameter("status", Status.ACTIVE.name())
						.getResultList();
				return results;
			}else {
				
			}
			return results;
		}

		@SuppressWarnings("unchecked")
		public List<DonationDetails> getDonationProgramNameCountAndAmountGroupByName(DonationRequestObject donationRequest, Date firstDate, Date secondDate) {
			List<DonationDetails> results = new ArrayList<>();
			if (donationRequest.getRoleType().equals(RoleType.SUPERADMIN.name())) {
				results = donationDetailsDao.getEntityManager().createQuery(
						"SELECT DD.programName, COUNT(id) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.status =:status GROUP BY DD.programName")
						.setParameter("firstDate", firstDate, TemporalType.DATE)
						.setParameter("lastDate", secondDate, TemporalType.DATE)
						.setParameter("superadminId", donationRequest.getSuperadminId())
						.setParameter("status", Status.ACTIVE.name())
						.getResultList();
				return results;
			} else if (donationRequest.getRoleType().equals(RoleType.TEAM_LEADER.name())) {
				results = donationDetailsDao.getEntityManager().createQuery(
						"SELECT DD.programName, COUNT(id) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.teamLeaderId = :teamLeaderId AND DD.status =:status GROUP BY DD.programName")
						.setParameter("firstDate", firstDate, TemporalType.DATE)
						.setParameter("lastDate", secondDate, TemporalType.DATE)
						.setParameter("teamLeaderId", donationRequest.getTeamLeaderId())
						.setParameter("status", Status.ACTIVE.name())
						.getResultList();
				return results;
			}else {
				
			}
			return results;
		}

}
