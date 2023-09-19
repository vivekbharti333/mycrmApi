package com.spring.helper;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.transform.Transformers;
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
	
	public void validateDonationRequest(DonationRequestObject donationRequestObject) 
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
	public DonationRequestObject getCountAndSum1(DonationRequestObject donationRequest) {
		
		Date date = new Date();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date firstDayOfWeek = calendar.getTime();
        
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date lastDayOfWeek = calendar.getTime();
		
	
		if (donationRequest.getRequestedFor().equals(RequestFor.TODAY.name())) {				
			List<Object[]> results = donationDetailsDao.getEntityManager().createQuery(
					"SELECT COUNT(amount) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId")
					.setParameter("firstDate", lastDayOfWeek, TemporalType.DATE)
					.setParameter("lastDate", firstDayOfWeek, TemporalType.DATE)
					.setParameter("superadminId", donationRequest.getSuperadminId()).getResultList();
			
			System.out.println("Result : "+results.toString());
			
			for (Object[] result : results) {
				
				System.out.println("Result : "+result[0]+" , "+result[1]);
				
		        Long count = (Long) result[0];
		        BigDecimal sum = (BigDecimal) result[1];
		         System.out.println("Count & Sum : "+count+" , "+sum);
		    }
					   
			return donationRequest;
		}
		return null;
	}
	

	public Object[] getCountAndSum(DonationRequestObject donationRequest) {
		
		Date date = new Date();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomarrow = calendar.getTime();
        
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date firstDayOfWeek = calendar.getTime();
        
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date lastDayOfWeek = calendar.getTime();
        
       
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDayOfMonth = calendar.getTime();
        
        
     // Get tomorrow's date
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = calendar.getTime();
        
        calendar.setTime(date);
        
        // Get yesterday's date
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = calendar.getTime();
        
        System.out.println("Tomorrow's date: " + tomorrow);
        System.out.println("Yesterday's date: " + yesterday);
        
       
        System.out.println("First day of the month: " + firstDayOfMonth);
        System.out.println("Last day of the month: " + lastDayOfMonth);
        
        
        System.out.println("First day of the week: " + firstDayOfWeek);
        System.out.println("Last day of the week: " + lastDayOfWeek);
        
        Date todayDate = new Date();
		Date nextDay = Date.from(todayDate.toInstant().plus(1, ChronoUnit.DAYS));
        
        
        System.out.println(lastDayOfWeek+", "+firstDayOfWeek+"............."+date+" , "+tomarrow);
		
		if(donationRequest.getRoleType().equals(RoleType.SUPERADMIN.name())) {

			if (donationRequest.getRequestedFor().equals(RequestFor.TODAY.name())) {				
				Object[] count = (Object[]) donationDetailsDao.getEntityManager().createQuery(
						"SELECT COUNT(*) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND DD.superadminId = :superadminId")
						.setParameter("firstDate", lastDayOfWeek, TemporalType.DATE)
						.setParameter("lastDate", firstDayOfWeek, TemporalType.DATE)
						.setParameter("superadminId", donationRequest.getSuperadminId()).getSingleResult();
				
				System.out.println("count : "+count[0]+" , "+count[1]);
				
				Object[] count1 = (Object[]) donationDetailsDao.getEntityManager().createQuery(
						"SELECT  COUNT(*) AS count, SUM(DD.amount) AS amount FROM DonationDetails DD WHERE DD.createdAt =:firstDate AND DD.superadminId = :superadminId")
						.setParameter("firstDate", new Date(), TemporalType.DATE)
//						.setParameter("lastDate", nextDay, TemporalType.DATE)
						.setParameter("superadminId", donationRequest.getSuperadminId())
						.getSingleResult();
				
				System.out.println("count1 : "+count1[0]+" , "+count1[1]);
	
				return count;
			}
//			else if (donationRequest.getRequestedFor().equals(RequestFor.YESTERDAY.name())) {
//				int count = donationDetailsDao.getEntityManager()
//						.createQuery("SELECT COUNT(amount) AS count, SUM(amount) AS amount FROM DonationDetails DD where createdAt =:createdAt AND DD.superadminId =:superadminId", Long.class)
//						.setParameter("createdAt", yesterday, TemporalType.DATE)
//						.setParameter("superadminId", donationRequest.getSuperadminId())
//						.getSingleResult()
//					    .intValue();
//				return count;
//			}else if (donationRequest.getRequestedFor().equals(RequestFor.WEEK.name())) {
//				int count = donationDetailsDao.getEntityManager()
//						.createQuery("SELECT COUNT(amount) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND superadminId =:superadminId", Long.class)
//						.setParameter("firstDate", firstDayOfWeek, TemporalType.DATE)
//						.setParameter("lastDate", lastDayOfWeek, TemporalType.DATE)
//						.setParameter("superadminId", donationRequest.getSuperadminId())
//						.getSingleResult()
//					    .intValue();
//				return count;
//			}else if (donationRequest.getRequestedFor().equals(RequestFor.MONTH.name())) {
//				int count = donationDetailsDao.getEntityManager()
//						.createQuery("SELECT COUNT(amount) AS count, SUM(amount) AS amount FROM DonationDetails DD where DD.createdAt BETWEEN :firstDate AND :lastDate AND superadminId =:superadminId", Long.class)
//						.setParameter("firstDate", firstDayOfMonth, TemporalType.DATE)
//						.setParameter("lastDate", lastDayOfMonth, TemporalType.DATE)
//						.setParameter("superadminId", donationRequest.getSuperadminId())
//						.getSingleResult()
//					    .intValue();
//				return count;
//			}
		}
		return null;
		

	}

}
