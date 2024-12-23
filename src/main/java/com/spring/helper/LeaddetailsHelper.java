package com.spring.helper;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spring.constant.Constant;
import com.spring.dao.CustomerDetailsDao;
import com.spring.dao.LeadDetailsDao;
import com.spring.entities.CustomerDetails;
import com.spring.entities.EnquiryDetails;
import com.spring.entities.LeadDetails;
import com.spring.entities.UserDetails;
import com.spring.enums.RoleType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.CustomerRequestObject;
import com.spring.object.request.DonationRequestObject;
import com.spring.object.request.LeadRequestObject;
import com.spring.object.request.UserRequestObject;

@Component
public class LeaddetailsHelper {

	@Autowired
	private LeadDetailsDao leadDetailsDao;
	

	
	public void validateEnquiryRequest(LeadRequestObject leadRequestObject) throws BizException {
		if (leadRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
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
	
	public Object[] getCountByStatus(LeadRequestObject leadRequest, Date firstDate, Date secondDate) {
		Object[] count = new Object[] {};
		if (leadRequest.getRoleType().equals(RoleType.SUPERADMIN.name())) {
			 count =  (Object[]) leadDetailsDao.getEntityManager().createQuery(
					"SELECT COUNT(DD.id) AS count FROM LeadDetails DD")
//					.setParameter("firstDate", firstDate, TemporalType.DATE)
//					.setParameter("lastDate", secondDate, TemporalType.DATE)
//					.setParameter("superadminId", leadRequest.getSuperadminId())
					.getSingleResult();
			return count;
//		} else if (leadRequest.getRoleType().equals(RoleType.TEAM_LEADER.name())) {
//			count = (Long) leadDetailsDao.getEntityManager().createQuery(
//					"SELECT COUNT(id) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.teamLeaderId =:teamLeaderId AND DD.status =:status")
//					.setParameter("firstDate", firstDate, TemporalType.DATE)
//					.setParameter("lastDate", secondDate, TemporalType.DATE)
//					.setParameter("superadminId", leadRequest.getSuperadminId())
//					.setParameter("teamLeaderId", leadRequest.getCreatedBy())
//					.setParameter("status", Status.ACTIVE.name())
//					.getSingleResult();
//			return count;
//		} else {
//			
//			System.out.println("firstDate : "+firstDate);
//			System.out.println("secondDate : "+secondDate);
//			System.out.println("firstDate : "+firstDate);
//			count = (Long) leadDetailsDao.getEntityManager().createQuery(
//					"SELECT COUNT(id) AS count, DD.status FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId AND DD.createdBy =:createdBy group by DD.status")
//					.setParameter("firstDate", firstDate, TemporalType.DATE)
//					.setParameter("lastDate", secondDate, TemporalType.DATE)
//					.setParameter("superadminId", leadRequest.getSuperadminId())
//					.setParameter("createdBy", leadRequest.getCreatedBy())
//					.getSingleResult();
//			return count;
//			}
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
