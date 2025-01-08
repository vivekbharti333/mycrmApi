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
import com.spring.dao.LeadDetailsDao;
import com.spring.entities.DonationDetails;
import com.spring.entities.LeadDetails;
import com.spring.enums.RoleType;
import com.spring.exceptions.BizException;
import com.spring.object.request.LeadRequestObject;

@Component
public class LeaddetailsHelper {

	@Autowired
	private LeadDetailsDao leadDetailsDao;
	
	
	public void validateEnquiryRequest(LeadRequestObject leadRequestObject) throws BizException {
		if (leadRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}

	}

	@Transactional
	public LeadDetails getLeadDetailsById(Long id) {

		CriteriaBuilder criteriaBuilder = leadDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<LeadDetails> criteriaQuery = criteriaBuilder.createQuery(LeadDetails.class);
		Root<LeadDetails> root = criteriaQuery.from(LeadDetails.class);
		Predicate restriction = criteriaBuilder.equal(root.get("id"), id);
		criteriaQuery.where(restriction);
		LeadDetails leadDetails = leadDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return leadDetails;
	}
	

	public LeadDetails getLeadDetailsByReqObj(LeadRequestObject leadRequest) {

		LeadDetails leadDetails = new LeadDetails();

		leadDetails.setDonorName(leadRequest.getDonorName());
		leadDetails.setMobileNumber(leadRequest.getMobileNumber());
		leadDetails.setEmailId(leadRequest.getEmailId());
		leadDetails.setStatus(leadRequest.getStatus());
		leadDetails.setFollowupDate(leadRequest.getFollowupDate());
		leadDetails.setNotes(leadRequest.getNotes());
		leadDetails.setCreatedBy(leadRequest.getCreatedBy());
		leadDetails.setCreatedbyName(leadRequest.getCreatedbyName());
		leadDetails.setTeamLeaderId(leadRequest.getTeamLeaderId());
		leadDetails.setSuperadminId(leadRequest.getSuperadminId());
		
		leadDetails.setCreatedAt(new Date());
		leadDetails.setUpdatedAt(new Date());

		return leadDetails;
	}

	@Transactional
	public LeadDetails saveLeadDetails(LeadDetails leadDetails) {
		leadDetailsDao.persist(leadDetails);
		return leadDetails;
	}
	
	public LeadDetails getUpdatedLeadDetailsByReqObj(LeadRequestObject leadRequest, LeadDetails leadDetails) {
		
		if(leadRequest.getStatus().equalsIgnoreCase("FOLLOWUP")) {
			leadDetails.setFollowupDate(leadRequest.getFollowupDate());
		} else {
			leadDetails.setFollowupDate(null);
		}

		leadDetails.setDonorName(leadRequest.getDonorName());
//		leadDetails.setMobileNumber(leadRequest.getMobileNumber());
		leadDetails.setEmailId(leadRequest.getEmailId());
		leadDetails.setStatus(leadRequest.getStatus());
		
		leadDetails.setNotes(leadRequest.getNotes());
		
		leadDetails.setUpdatedAt(new Date());

		return leadDetails;
	}
	
	@Transactional
	public LeadDetails updateLeadDetails(LeadDetails leadDetails) {
		leadDetailsDao.update(leadDetails);
		return leadDetails;
	}
	
	
	public Long getLeadCountByStatus(LeadRequestObject leadRequest, Date firstDate, Date secondDate, String status) {
		Long count = 0L;
		
		if (leadRequest.getRoleType().equals(RoleType.SUPERADMIN.name())) { 
			
			if (status == "FOLLOWUP") {
				count = (Long) leadDetailsDao.getEntityManager().createQuery(
						"SELECT COUNT(id) FROM LeadDetails DD WHERE DD.followupDate BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.status = :status")
						.setParameter("firstDate", firstDate, TemporalType.DATE)
						.setParameter("lastDate", secondDate, TemporalType.DATE)
						.setParameter("superadminId", leadRequest.getSuperadminId())
						.setParameter("status", status)
						.getSingleResult();
			} else {
				count = (Long) leadDetailsDao.getEntityManager().createQuery(
						"SELECT COUNT(id) FROM LeadDetails DD WHERE DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.status = :status")
						.setParameter("firstDate", firstDate, TemporalType.DATE)
						.setParameter("lastDate", secondDate, TemporalType.DATE)
						.setParameter("superadminId", leadRequest.getSuperadminId())
						.setParameter("status", status)
						.getSingleResult();
			}

		} else if (leadRequest.getRoleType().equals(RoleType.TEAM_LEADER.name())) {

			if (status == "FOLLOWUP") {
				count = (Long) leadDetailsDao.getEntityManager().createQuery(
						"SELECT COUNT(id) FROM LeadDetails DD WHERE DD.followupDate BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.teamLeaderId = :teamLeaderId AND DD.status = :status")
						.setParameter("firstDate", firstDate, TemporalType.DATE)
						.setParameter("lastDate", secondDate, TemporalType.DATE)
						.setParameter("superadminId", leadRequest.getSuperadminId())
						.setParameter("teamLeaderId", leadRequest.getCreatedBy())
						.setParameter("status", status)
						.getSingleResult();
			} else {
				count = (Long) leadDetailsDao.getEntityManager().createQuery(
						"SELECT COUNT(id) FROM LeadDetails DD WHERE DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.teamLeaderId = :teamLeaderId AND DD.status = :status")
						.setParameter("firstDate", firstDate, TemporalType.DATE)
						.setParameter("lastDate", secondDate, TemporalType.DATE)
						.setParameter("superadminId", leadRequest.getSuperadminId())
						.setParameter("teamLeaderId", leadRequest.getCreatedBy())
						.setParameter("status", status)
						.getSingleResult();
			}
			
		} else {
			
			if (status == "FOLLOWUP") {
				count = (Long) leadDetailsDao.getEntityManager().createQuery(
						"SELECT COUNT(id) FROM LeadDetails DD WHERE DD.followupDate BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.createdBy = :createdBy AND DD.status = :status")
						.setParameter("firstDate", firstDate, TemporalType.DATE)
						.setParameter("lastDate", secondDate, TemporalType.DATE)
						.setParameter("superadminId", leadRequest.getSuperadminId())
						.setParameter("createdBy", leadRequest.getCreatedBy()).setParameter("status", status)
						.getSingleResult();
			} else {
				count = (Long) leadDetailsDao.getEntityManager().createQuery(
						"SELECT COUNT(id) FROM LeadDetails DD WHERE DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.createdBy = :createdBy AND DD.status = :status")
						.setParameter("firstDate", firstDate, TemporalType.DATE)
						.setParameter("lastDate", secondDate, TemporalType.DATE)
						.setParameter("superadminId", leadRequest.getSuperadminId())
						.setParameter("createdBy", leadRequest.getCreatedBy()).setParameter("status", status)
						.getSingleResult();
			}
		}
		return count;
	}
	
	public Long getTotalLeadCount(LeadRequestObject leadRequest, Date firstDate, Date secondDate) {
		Long count = 0L;
		if (leadRequest.getRoleType().equals(RoleType.SUPERADMIN.name())) {
			count = (Long) leadDetailsDao.getEntityManager().createQuery(
					"SELECT COUNT(id) FROM LeadDetails DD WHERE DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId")
					.setParameter("firstDate", firstDate, TemporalType.DATE)
					.setParameter("lastDate", secondDate, TemporalType.DATE)
					.setParameter("superadminId", leadRequest.getSuperadminId())
					.getSingleResult();
			
		} else if (leadRequest.getRoleType().equals(RoleType.TEAM_LEADER.name())) {
			count = (Long) leadDetailsDao.getEntityManager().createQuery(
					"SELECT COUNT(id) FROM LeadDetails DD WHERE DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.teamLeaderId = :teamLeaderId")
					.setParameter("firstDate", firstDate, TemporalType.DATE)
					.setParameter("lastDate", secondDate, TemporalType.DATE)
					.setParameter("superadminId", leadRequest.getSuperadminId())
					.setParameter("teamLeaderId", leadRequest.getCreatedBy())
					.getSingleResult();
		} else {
				count = (Long) leadDetailsDao.getEntityManager().createQuery(
						"SELECT COUNT(id) FROM LeadDetails DD WHERE DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.createdBy = :createdBy")
						.setParameter("firstDate", firstDate, TemporalType.DATE)
						.setParameter("lastDate", secondDate, TemporalType.DATE)
						.setParameter("superadminId", leadRequest.getSuperadminId())
						.setParameter("createdBy", leadRequest.getCreatedBy())
						.getSingleResult();
			}
		return count;
	}

	

	@SuppressWarnings("unchecked")
	public List<LeadDetails> getLeadList(LeadRequestObject leadRequest, Date firstDate, Date secondDate) {		
		if (leadRequest.getRoleType().equalsIgnoreCase(RoleType.SUPERADMIN.name())) {
			String hqlQuery = "SELECT LD FROM LeadDetails LD WHERE LD.createdAt BETWEEN :firstDate AND :lastDate AND LD.superadminId =:superadminId ORDER BY LD.id DESC";
			List<LeadDetails> results = leadDetailsDao.getEntityManager().createQuery(hqlQuery)
					.setParameter("firstDate", firstDate, TemporalType.DATE)
					.setParameter("lastDate", secondDate, TemporalType.DATE)
					.setParameter("superadminId", leadRequest.getSuperadminId())
					.getResultList();
			return results;
		} else if (leadRequest.getRoleType().equalsIgnoreCase(RoleType.TEAM_LEADER.name())) {
			String hqlQuery = "SELECT LD FROM LeadDetails LD WHERE LD.superadminId =:superadminId AND LD.createdBy =:createdBy";
			List<LeadDetails> results = leadDetailsDao.getEntityManager().createQuery(hqlQuery)
					.setParameter("firstDate", firstDate, TemporalType.DATE)
					.setParameter("lastDate", secondDate, TemporalType.DATE)
					.setParameter("superadminId", leadRequest.getSuperadminId())
					.setParameter("createdBy", leadRequest.getCreatedBy())
					.getResultList();
			return results;
		}else {
			String hqlQuery = "SELECT LD FROM LeadDetails LD WHERE LD.createdAt BETWEEN :firstDate AND :lastDate AND LD.superadminId =:superadminId AND LD.createdBy =:createdBy";
			List<LeadDetails> results = leadDetailsDao.getEntityManager().createQuery(hqlQuery)
					.setParameter("firstDate", firstDate, TemporalType.DATE)
					.setParameter("lastDate", secondDate, TemporalType.DATE)
					.setParameter("superadminId", leadRequest.getSuperadminId())
					.setParameter("createdBy", leadRequest.getCreatedBy())
					.getResultList();
			return results;
		}
	}
	


}
