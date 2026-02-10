package com.ngo.helper;

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

import com.common.constant.Constant;
import com.common.enums.Status;
import com.common.exceptions.BizException;
import com.ngo.dao.WhatsAppDetailsDao;
import com.ngo.entities.DonationDetails;
import com.ngo.entities.WhatsAppDetails;
import com.ngo.object.request.SmsTemplateRequestObject;
import com.ngo.object.request.WhatsAppRequestObject;

@Component
public class WhatsAppHelper {

//	@Autowired
//	private CustomerDetailsDao customerDetailsDao;
	
	@Autowired
	private WhatsAppDetailsDao whatsAppDetailsDao;
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
	private DonationHelper donationHelper;
	
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
	    	 String url = "";

	    	if (donationDetails.getProgramName().equalsIgnoreCase("Sale")) {
	    		url = "https://demo.digitalsms.biz/api?apikey="+whatsAppDetails.getApiKey()
                + "&mobile=" + donationDetails.getMobileNumber()+ "&msg=" + "We have received Rs " + donationDetails.getAmount() + " through receipt no "+ donationDetails.getInvoiceNumber()+ " For Receipt mail on help@mydonation.in - Mydonation ";
	    	}else {
	    		url = "https://demo.digitalsms.biz/api?apikey=" + whatsAppDetails.getApiKey() + "&mobile=" + donationDetails.getMobileNumber() + "&msg=" + "Thank You for Your kind Donation. This is Your Donation Receipt" + "&pdf=https://datfuslab.in/drmapinew/getPdfreceipt/" + donationDetails.getReceiptNumber() + ".pdf";
	    	}
	    	
	        // Send the GET request using RestTemplate
	        RestTemplate restTemplate = new RestTemplate();
	        String response = restTemplate.getForObject(url, String.class);
	        
	        
	        // JSON string
//	        String response = "{\"status\":0,\"errormsg\":\"Invalid API Key\",\"statuscode\":403}";

	        // Parse JSON
//	        JSONObject jsonObject = new JSONObject(response);
//
//	        // Extract values
//	        int status = jsonObject.getInt("status");
//	        int statusCode = jsonObject.getInt("statuscode");
//	        
//	        if(statusCode == 200) {
//	        	donationDetails.setInvoiceDownloadStatus("YES");
//				donationHelper.updateDonationDetails(donationDetails);
//	        } else {
//	        	donationDetails.setInvoiceDownloadStatus("NO");
//				donationHelper.updateDonationDetails(donationDetails);
//	        }

//	        System.out.println("Status: " + status);
//	        System.out.println("StatusCode: " + statusCode);
	        
            

	        // Log the response
	        System.out.println("Response : "+response);

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
			whatsAppDetails.setUpdatedAt(new Date());
			return whatsAppDetails;
		}

	@Transactional
	public WhatsAppDetails updateWhatsAppDetails(WhatsAppDetails whatsAppDetails) {
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
