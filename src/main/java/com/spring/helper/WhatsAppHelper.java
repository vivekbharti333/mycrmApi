package com.spring.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.spring.constant.Constant;
import com.spring.dao.CustomerDetailsDao;
import com.spring.dao.SmsTemplateDetailsDao;
import com.spring.dao.WhatsAppDetailsDao;
import com.spring.entities.CustomerDetails;
import com.spring.entities.DonationDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.entities.SmsTemplateDetails;
import com.spring.entities.UserDetails;
import com.spring.entities.WhatsAppDetails;
import com.spring.enums.RoleType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.object.request.CustomerRequestObject;
import com.spring.object.request.SmsTemplateRequestObject;
import com.spring.object.request.UserRequestObject;
import com.spring.object.request.WhatsAppRequestObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class WhatsAppHelper {

//	@Autowired
//	private CustomerDetailsDao customerDetailsDao;
	
	@Autowired
	private WhatsAppDetailsDao whatsAppDetailsDao;
	
	@Autowired
    private RestTemplate restTemplate;
	
	public void validateSmsTemplateRequest(WhatsAppRequestObject whatsAppRequestObject) throws BizException {
		if (whatsAppRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}
	
	
//	public String sendWhatsAppMessage(DonationDetails donationDetails, WhatsAppDetails whatsAppDetails) {
//        try {
//            // Construct the message
////            String message = "We have received a donation of Rs " + donationDetails.getAmount() + ". Please click to download your receipt " 
////                    + whatsAppDetails.getReceiptDownloadUrl() + " " + donationDetails.getReceiptNumber();
//        	
//        	String message = "We have received a donation of Rs " + donationDetails.getAmount() + "."
//        			+ " Please click below url to download your receipt. https://mydonation.in/#/thank-you/letter/" + donationDetails.getReceiptNumber();
//        	
////        	String message = "We have received a donation of Rs " + donationDetails.getAmount() + "."
////        			+ " Please click below url to download your receipt. "+whatsAppDetails.getReceiptDownloadUrl() + donationDetails.getReceiptNumber();
//
//            // URL encode the message
//            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
//
//            // Construct the URL
////            String url = "https://demo.digitalsms.biz/api?apikey=ca505488be2a82c0853eeaf2bfb5026c&mobile="+donationDetails.getMobileNumber()+"&msg=" + encodedMessage;
//            String url = whatsAppDetails.getWhatsappUrl()+whatsAppDetails.getApiKey()+"&mobile="+donationDetails.getMobileNumber()+"&msg=" + encodedMessage;
//
//            // Send the request using RestTemplate (GET request)
//            String response = restTemplate.getForObject(url, String.class);
//
//            // Log the response
//            System.out.println(response);
//
//            return response;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return "Error encoding message: " + e.getMessage();
//        }
//    }
	
	public String sendWhatsAppMessage(DonationDetails donationDetails, WhatsAppDetails whatsAppDetails) {
	    try {
	        // Construct the plain message
	        String message = "We have received a donation of Rs " + donationDetails.getAmount() + ". "
	                + "Please click below URL to download your receipt: https://mydonation.in/#/thank-you/letter/" 
	                + donationDetails.getReceiptNumber();

	        // URL encode only the message text (excluding the preformatted URL)
//	        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());

	        // Construct the full API URL
	        String url = "https://demo.digitalsms.biz/api?apikey=ca505488be2a82c0853eeaf2bfb5026c"
	                + "&mobile=" + donationDetails.getMobileNumber()
	                + "&msg=" + message;

	        // Send the GET request using RestTemplate
	        RestTemplate restTemplate = new RestTemplate();
	        String response = restTemplate.getForObject(url, String.class);

	        // Log the response
	        System.out.println(response);

	        return response;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error sending WhatsApp message: " + e.getMessage();
	    }
	}
	
	@Transactional
	public WhatsAppDetails getWhatsAppBySuperadminId(String superadminId) {

		CriteriaBuilder criteriaBuilder = whatsAppDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<WhatsAppDetails> criteriaQuery = criteriaBuilder.createQuery(WhatsAppDetails.class);
		Root<WhatsAppDetails> root = criteriaQuery.from(WhatsAppDetails.class);
		Predicate restriction = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction);
		WhatsAppDetails smsDetails = whatsAppDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return smsDetails;
	}


	
	public WhatsAppDetails getWhatsAppDetailsByReqObj(WhatsAppRequestObject whatsAppRequest) {

		WhatsAppDetails whatsAppDetails = new WhatsAppDetails();

		whatsAppDetails.setWhatsappUrl(whatsAppRequest.getWhatsappUrl());
		whatsAppDetails.setApiKey(whatsAppRequest.getApiKey());
		whatsAppDetails.setWhatsAppNumber(whatsAppRequest.getWhatsAppNumber());
		whatsAppDetails.setReceiptDownloadUrl(whatsAppRequest.getReceiptDownloadUrl());
		whatsAppDetails.setStatus(Status.ACTIVE.name());
		whatsAppDetails.setSuperadminId(whatsAppRequest.getSuperadminId());
		whatsAppDetails.setUpdatedBy(whatsAppRequest.getUpdatedBy());
		whatsAppDetails.setCreatedAt(new Date());
		whatsAppDetails.setUpdatedAt(new Date());
		return whatsAppDetails;
	}

	@Transactional
	public WhatsAppDetails saveWhatsAppDetails(WhatsAppDetails whatsAppDetails) {
		whatsAppDetailsDao.persist(whatsAppDetails);
		return whatsAppDetails;
	}
	


		public WhatsAppDetails getUpdatedWhatsAppDetailsByReqObj(WhatsAppRequestObject whatsAppRequest, WhatsAppDetails whatsAppDetails) {

			whatsAppDetails.setWhatsappUrl(whatsAppRequest.getWhatsappUrl());
			whatsAppDetails.setApiKey(whatsAppRequest.getApiKey());
			whatsAppDetails.setWhatsAppNumber(whatsAppRequest.getWhatsAppNumber());
			whatsAppDetails.setStatus(Status.ACTIVE.name());
			whatsAppDetails.setSuperadminId(whatsAppRequest.getSuperadminId());
			whatsAppDetails.setUpdatedBy(whatsAppRequest.getUpdatedBy());
			whatsAppDetails.setCreatedAt(new Date());
			whatsAppDetails.setUpdatedAt(new Date());
			return whatsAppDetails;
		}

	@Transactional
	public WhatsAppDetails UpdateWhatsAppDetails(WhatsAppDetails whatsAppDetails) {
		whatsAppDetailsDao.update(whatsAppDetails);
		return whatsAppDetails;
	}



	@SuppressWarnings("unchecked")
	public List<WhatsAppDetails> getSmsTemplateList(SmsTemplateRequestObject smsTemplateRequest) {
		if(smsTemplateRequest.getRequestedFor().equals("ALL")) {
			String hqlQuery = "SELECT SD FROM WhatsAppDetails SD";
			List<WhatsAppDetails> results = whatsAppDetailsDao.getEntityManager()
					.createQuery(hqlQuery)
					.getResultList();
			return results;
		}else {
			String hqlQuery = "SELECT SD FROM WhatsAppDetails SD";
			List<WhatsAppDetails> results = whatsAppDetailsDao.getEntityManager()
					.createQuery(hqlQuery)
					.getResultList();
			return results;
		}

	}

	

}
