package com.spring.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.common.SendEmailHelper;
import com.spring.common.SmsHelper;
import com.spring.constant.Constant;
import com.spring.dao.DonationDetailsDao;
import com.spring.entities.DonationDetails;
import com.spring.entities.EmailServiceDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.entities.SmsTemplateDetails;
import com.spring.entities.UserDetails;
import com.spring.enums.RoleType;
import com.spring.enums.SmsType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.DonationRequestObject;



@Component
public class DonationHelper {
	
	@Autowired
	private DonationDetailsDao donationDetailsDao;
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private SmsTemplateHelper smsTemplateHelper;
	
	@Autowired
	private SmsHelper smsHelper;
	
	@Autowired
	private EmailHelper emailHelper;
	
	@Autowired
	private SendEmailHelper sendEmailHelper;
	
	
	public void validateDonationRequest(DonationRequestObject donationRequest) throws BizException {
		if (donationRequest == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}
		
			
	
	public void validateDonationRequestFields(DonationRequestObject donationRequest) 
			throws BizException
	{ 
		if(donationRequest.getInvoiceHeaderDetailsId() == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Please Select Receipt Type"); 
		}
		if(donationRequest.getMobileNumber() == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Enter Mobile Number"); 
		}
		if(donationRequest.getAmount() <= 0) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Amount can not be null or Zero"); 
		}
		if(donationRequest.getProgramName() == null || donationRequest.getProgramName().isEmpty() || donationRequest.getProgramName().equals("")) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Please Select Program"); 
		}
		if((donationRequest.getPaymentMode() == null) || donationRequest.getPaymentMode().equalsIgnoreCase(""))  {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Please Select Payment Mode");
		}
	}
	
	
	
	public void sendDonationPaymentLinkSms(DonationDetails donationDetails, InvoiceHeaderDetails invoiceHeader, String paymentLink) {

		SmsTemplateDetails donationSmsTemplate = smsTemplateHelper.getSmsDetailsBySuperadminIdAndHeaderIdAndSmsType(donationDetails.getSuperadminId(), donationDetails.getInvoiceHeaderDetailsId(),SmsType.PAYMENT_GATEWAY.name());
		if (donationSmsTemplate != null && donationSmsTemplate.getStatus().equalsIgnoreCase(Status.ACTIVE.name())) {

			SmsTemplateDetails smsTemplate = smsTemplateHelper.getSmsDetailsBySuperadminIdAndHeaderIdAndSmsType(donationDetails.getSuperadminId(), donationDetails.getInvoiceHeaderDetailsId(), SmsType.DONATION_RECEIPT.name());
	        smsTemplate.setTemplateId("1707170816446072208");
	        
//	        String messageBody = "CE Foundation has requested money. On approving Rs."+donationDetails.getAmount()+" will debited from your account. http://tinyurl.com/{#var#}";
	        String messageBody = "CE Foundation has requested money. On approving Rs."+donationDetails.getAmount()+" will debited from your account. "+paymentLink;
			String response = smsHelper.sendSms(messageBody, smsTemplate, donationDetails);
		    
			System.out.println("Payment Request : "+response);
		}
	}
	
	public void sendDonationInvoiceSms(DonationDetails donationDetails, InvoiceHeaderDetails invoiceHeader) {
		
		System.out.println("Enter 1 "+donationDetails.getSuperadminId()+" , " +donationDetails.getInvoiceHeaderDetailsId());

		SmsTemplateDetails donationSmsTemplate = smsTemplateHelper.getSmsDetailsBySuperadminIdAndHeaderIdAndSmsType(donationDetails.getSuperadminId(), donationDetails.getInvoiceHeaderDetailsId(),SmsType.DONATION_RECEIPT.name());
		System.out.println("Enter 2 "+donationSmsTemplate);
		if (donationSmsTemplate != null && donationSmsTemplate.getStatus().equalsIgnoreCase(Status.ACTIVE.name())) {
			System.out.println("Enter 3");
			String messageBody = " We have received donation of Rs " + donationDetails.getAmount()+ " Click to Download your receipt " + donationSmsTemplate.getInvoiceDomain()+ donationDetails.getReceiptNumber() + " - " + donationSmsTemplate.getCompanyRegards();
			String responce = smsHelper.sendSms(messageBody, donationSmsTemplate, donationDetails);
			
			System.out.println("res : "+responce);
		}

		//Product Sms 
		SmsTemplateDetails productSmsTemplate = smsTemplateHelper.getSmsDetailsBySuperadminIdAndHeaderIdAndSmsType(donationDetails.getSuperadminId(), donationDetails.getInvoiceHeaderDetailsId(),SmsType.PRODUCT_RECEIPT.name());
		if (productSmsTemplate != null && productSmsTemplate.getStatus().equalsIgnoreCase(Status.ACTIVE.name())) {
			String messageBody = " We have received Rs " + donationDetails.getAmount() + " through receipt no "+ donationDetails.getInvoiceNumber() + " For Receipt mail on help@mydonation.in - Mydonation ";
			String responce = smsHelper.sendSms(messageBody, productSmsTemplate, donationDetails);
		}
	}
	
	@SuppressWarnings("static-access")
	public void sendDonationInvoiceEmail(DonationDetails donationDetails, InvoiceHeaderDetails invoiceHeader) throws MessagingException, IOException {
		if(donationDetails.getEmailId() != null && !donationDetails.getEmailId().equalsIgnoreCase("")) {
			EmailServiceDetails emailServiceDetails = emailHelper.getEmailDetailsByEmailTypeAndSuperadinId(SmsType.DONATION_RECEIPT.name(), donationDetails.getSuperadminId());
			if(emailServiceDetails != null && emailServiceDetails.getStatus().equalsIgnoreCase(Status.ACTIVE.name())) {
				
				sendEmailHelper.sendEmailWithInvoice(invoiceHeader,donationDetails, emailServiceDetails);
				
				}
			}
		
	}
	
//	@SuppressWarnings("static-access")
//	public DonationRequestObject generateReceiptNumber(DonationRequestObject donationRequest) {
//		String rendomNumber = userHelper.generateRandomChars("ABCD145pqrs678abcdef90EF9GHxyzIJKL5MNOPQRghijS1234560TUVWXYlmnoZ1234567tuvw890", 4);
//		String receiptNumber = donationRequest.getSuperadminId().substring(0, 4)+rendomNumber+donationRequest.getMobileNumber().substring(7, 10);
//		donationRequest.setReceiptNumber(receiptNumber);
//		return donationRequest;
//	}
	
	
	public DonationRequestObject getTeamLeaderIdOfDonation(DonationRequestObject donationRequest) {
		
		UserDetails userDetails = userHelper.getUserDetailsByLoginIdAndSuperadminId(donationRequest.getLoginId(), donationRequest.getSuperadminId());
		if(userDetails != null) {
			donationRequest.setCreatedbyName(userDetails.getFirstName()+" "+userDetails.getLastName());
			
			if(userDetails.getRoleType().equalsIgnoreCase(RoleType.SUPERADMIN.name()) 
					|| userDetails.getRoleType().equalsIgnoreCase(RoleType.ADMIN.name()) 
					|| userDetails.getRoleType().equalsIgnoreCase(RoleType.TEAM_LEADER.name())) 
			{
				donationRequest.setTeamLeaderId(donationRequest.getLoginId());
			}else {
				donationRequest.setTeamLeaderId(userDetails.getCreatedBy());
			}
		}
		return donationRequest;
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
	
public DonationDetails getUpdatedDonationDetailsByReqObj(DonationRequestObject donationRequest, DonationDetails donationDetails) {
		
//		donationDetails.setDonorName(donationRequest.getDonorName());
		donationDetails.setMobileNumber(donationRequest.getMobileNumber());
		donationDetails.setEmailId(donationRequest.getEmailId());
		donationDetails.setPanNumber(donationRequest.getPanNumber());
		donationDetails.setAddress(donationRequest.getAddress());
//		donationDetails.setProgramName(donationRequest.getProgramName());
//		donationDetails.setAmount(donationRequest.getAmount());
//		donationDetails.setReceiptNumber(donationRequest.getReceiptNumber());
		donationDetails.setTransactionId(donationRequest.getTransactionId());
		donationDetails.setPaymentMode(donationRequest.getPaymentMode());
		donationDetails.setPaymentType(donationRequest.getPaymentType());
//		donationDetails.setInvoiceDownloadStatus(Status.NO.name());
		donationDetails.setStatus(Status.ACTIVE.name());
		donationDetails.setNotes(donationRequest.getNotes());
//		donationDetails.setLoginId(donationRequest.getLoginId());
//		donationDetails.setInvoiceHeaderDetailsId(donationRequest.getInvoiceHeaderDetailsId());
//		donationDetails.setInvoiceHeaderName(donationRequest.getInvoiceHeaderName());
//		donationDetails.setCreatedBy(donationRequest.getLoginId());
//		donationDetails.setCreatedbyName(donationRequest.getCreatedbyName());
//		donationDetails.setTeamLeaderId(donationRequest.getTeamLeaderId());
//		donationDetails.setSuperadminId(donationRequest.getSuperadminId());
//		donationDetails.setCreatedAt(new Date());
		donationDetails.setUpdatedAt(new Date());
		
		
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
				"SELECT DD.createdbyName, COUNT(DD.id) AS count, SUM(DD.amount) AS amount, UD.userPicture, "
				+ "(SELECT d1.firstName FROM UserDetails d1 WHERE d1.loginId = DD.teamLeaderId) AS teamLeaderName FROM DonationDetails DD "
				+ "INNER JOIN UserDetails UD ON DD.loginId = UD.loginId WHERE DD.createdAt BETWEEN :firstDate AND :lastDate "
				+ "AND DD.superadminId = :superadminId AND DD.status = :status GROUP BY DD.teamLeaderId, DD.createdbyName, UD.userPicture")
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
