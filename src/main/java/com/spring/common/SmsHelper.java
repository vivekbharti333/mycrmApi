package com.spring.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spring.dao.SmsTemplateDetailsDao;
import com.spring.entities.DonationDetails;
import com.spring.entities.SmsTemplateDetails;



@Component
public class SmsHelper {
	
	@Autowired
	private SmsTemplateDetailsDao smsDetailsDao;
	
	
	@Transactional
	public SmsTemplateDetails getSmsDetailsBySuperadminId(String superadminId, String smsType) {

		CriteriaBuilder criteriaBuilder = smsDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<SmsTemplateDetails> criteriaQuery = criteriaBuilder.createQuery(SmsTemplateDetails.class);
		Root<SmsTemplateDetails> root = criteriaQuery.from(SmsTemplateDetails.class);
		Predicate restriction = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaBuilder.equal(root.get("smsType"), smsType);
		criteriaQuery.where(restriction);
		SmsTemplateDetails smsDetails = smsDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return smsDetails;
	}
	

	public String sendSms(String messageBody, SmsTemplateDetails smsDetails, DonationDetails donationDetails) {
		try {
			
//			smsDetails.setSmsUrl("http://nimbusit.biz/api/SmsApi/SendSingleApi?");
//			smsDetails.setSmsUserId("aarinefoundation");
//			smsDetails.setSmsPassword("eqpu2598EQ");
//			smsDetails.setSmsSender("AARINE");
//			smsDetails.setEntityId("1201161467507592526");
//			smsDetails.setTemplateId("1207167835475038639");
			
//			String messageBody = "Thank you for donating Rs. 1000 at CEF INDIA. Click to download Receipt within 10 days. https://datafusionlab.co.in:8080/mycrm/donationinvoice/1234 CE FOUNDATION";
			
//			smsDetails.setSmsUrl("http://nimbusit.biz/api/SmsApi/SendSingleApi?");
//			smsDetails.setSmsUserId("cefoundation");
//			smsDetails.setSmsPassword("qlmo6657QL");
//			smsDetails.setSmsSender("CEFOUN");
//			smsDetails.setEntityId("1701169743366929967");
//			smsDetails.setTemplateId("1707169770557920168");
			
			String providerUrl = smsDetails.getSmsUrl();
			String username = URLEncoder.encode(smsDetails.getSmsUserId(), "UTF-8");
			String password = URLEncoder.encode(smsDetails.getSmsPassword(), "UTF-8");
			String sender = URLEncoder.encode(smsDetails.getSmsSender(), "UTF-8");
			String sendto = URLEncoder.encode(donationDetails.getMobileNumber(), "UTF-8");
			String message = URLEncoder.encode(messageBody, "UTF-8");
			String entityid = smsDetails.getEntityId();
			String templateId = smsDetails.getTemplateId();
			
			String testUrl = providerUrl+"UserID="+ username +"&Password="+password+"&SenderID="+sender+"&Phno="+sendto+"&Msg="+message+"&EntityID="+entityid+"&TemplateID="+templateId;	
			
			System.out.println(testUrl);
			
			URL url = new URL(testUrl);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			String sResult = "";
			while ((line = rd.readLine()) != null) {
				// Process line...
				sResult = sResult + line + " ";
			}
			rd.close();
			return sResult;
		} catch (Exception e) {
			return "Error " + e;
		}
	}
}
