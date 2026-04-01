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
import com.common.entities.InvoiceHeaderDetails;
import com.common.enums.Status;
import com.common.exceptions.BizException;
import com.ngo.controller.LeadDetailsController;
import com.ngo.dao.WhatsAppDetailsDao;
import com.ngo.entities.DonationDetails;
import com.ngo.entities.WhatsAppDetails;
import com.ngo.object.request.SmsTemplateRequestObject;
import com.ngo.object.request.WhatsAppRequestObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONObject;

@Component
public class WhatsAppHelper {

    private final LeadDetailsController leadDetailsController;

//	@Autowired
//	private CustomerDetailsDao customerDetailsDao;
	
	@Autowired
	private WhatsAppDetailsDao whatsAppDetailsDao;
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
	private DonationHelper donationHelper;

    WhatsAppHelper(LeadDetailsController leadDetailsController) {
        this.leadDetailsController = leadDetailsController;
    }
	
	public void validateSmsTemplateRequest(WhatsAppRequestObject whatsAppRequestObject) throws BizException {
		if (whatsAppRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}
	
	


	public String buildWhatsAppPayload(DonationDetails donationDetails, InvoiceHeaderDetails invoiceHeader) {

	    JSONObject root = new JSONObject();
	    root.put("messaging_product", "whatsapp");
	    root.put("to", donationDetails.getMobileNumber());
	    root.put("type", "template");

	    JSONObject template = new JSONObject();
	    template.put("name", "donation_receipt_global");

	    JSONObject language = new JSONObject();
	    language.put("code", "en");
	    template.put("language", language);

	    JSONArray components = new JSONArray();
	    JSONObject body = new JSONObject();
	    body.put("type", "body");

	    JSONArray parameters = new JSONArray();

	    parameters.put(new JSONObject().put("type", "text").put("text", donationDetails.getDonorName()));
	    parameters.put(new JSONObject().put("type", "text").put("text", donationDetails.getAmount()));
	    parameters.put(new JSONObject().put("type", "text").put("text", invoiceHeader.getCompanyFirstName()+" "+invoiceHeader.getCompanyLastName()));
	    parameters.put(new JSONObject().put("type", "text").put("text", invoiceHeader.getReceiptDownloadUrl()));

	    body.put("parameters", parameters);
	    components.put(body);

	    template.put("components", components);
	    root.put("template", template);

	    return root.toString();
	}
	


	public String sendWhatsAppMessage(String payload, WhatsAppDetails whatsAppDetails) throws Exception {

		System.out.println("Payload : "+payload);
		System.out.println(whatsAppDetails.toString());
	    OkHttpClient client = new OkHttpClient();
	    

	    MediaType mediaType = MediaType.parse("application/json");
	    RequestBody body = RequestBody.create( payload , mediaType);

	    Request request = new Request.Builder()
	            .url("https://graph.facebook.com/"+whatsAppDetails.getVersion()+"/"+whatsAppDetails.getPhoneNumberId()+"/messages")
	            .post(body)
	            .addHeader("Content-Type", "application/json")
	            .addHeader("Authorization", "Bearer "+whatsAppDetails.getUserAccessToken())
	            .build();

	    Response response = client.newCall(request).execute();

	    System.out.println("Response : "+response);
	    return response.body() != null ? response.body().string() : null;
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

		whatsAppDetails.setServiceProvider(whatsAppRequest.getServiceProvider());
		whatsAppDetails.setType(whatsAppRequest.getType());
		whatsAppDetails.setPhoneNumberId(whatsAppRequest.getPhoneNumberId());
		whatsAppDetails.setVersion(whatsAppRequest.getVersion());
		whatsAppDetails.setUserAccessToken(whatsAppRequest.getUserAccessToken());
		
		whatsAppDetails.setWhatsAppNumber(whatsAppRequest.getWhatsAppNumber());
		whatsAppDetails.setReceiptDownloadUrl(whatsAppRequest.getReceiptDownloadUrl());
		whatsAppDetails.setStatus(Status.ACTIVE.name());
		whatsAppDetails.setSuperadminId(whatsAppRequest.getSuperadminId());
		whatsAppDetails.setCreatedBy(whatsAppRequest.getCreatedBy());
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

			whatsAppDetails.setServiceProvider(whatsAppRequest.getServiceProvider());
			whatsAppDetails.setType(whatsAppRequest.getType());
			
			whatsAppDetails.setPhoneNumberId(whatsAppRequest.getPhoneNumberId());
			whatsAppDetails.setVersion(whatsAppRequest.getVersion());
			whatsAppDetails.setUserAccessToken(whatsAppRequest.getUserAccessToken());
			
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
