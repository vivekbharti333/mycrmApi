package com.common.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.mail.MessagingException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.entities.EmailServiceDetails;
import com.common.entities.InvoiceHeaderDetails;
import com.common.entities.UserDetails;
import com.ngo.dao.SmsTemplateDetailsDao;
import com.ngo.email.template.DatfuslabWelcome;
import com.ngo.email.template.DonationThankYou;
import com.ngo.entities.DonationDetails;
import com.ngo.entities.SmsTemplateDetails;
import com.ngo.helper.DonationHelper;
import com.spring.common.PdfInvoice;

@Component
public class ZeptoEmail {


	@Autowired
	private DonationThankYou donationThankYou;
	
	@Autowired
	private DatfuslabWelcome datfuslabWelcome;

	
	public String setJsonParameter(EmailServiceDetails emailService, DonationDetails donationDetails, InvoiceHeaderDetails invoiceHeader) {
		
		String subject = emailService.getSubject();
		if("GLOBAL".equalsIgnoreCase(invoiceHeader.getInvoiceEmail())) {
			subject = "Your Contribution Receipt - "+invoiceHeader.getCompanyFirstName()+" "+invoiceHeader.getCompanyLastName();
		}
		
        JSONObject from = new JSONObject();
        from.put("address", emailService.getEmailFrom());
        from.put("name", emailService.getRegards());

        JSONObject toEmailAddress = new JSONObject();
        toEmailAddress.put("address", donationDetails.getEmailId());

        JSONObject toObj = new JSONObject();
        toObj.put("email_address", toEmailAddress);

        JSONArray toArray = new JSONArray();
        toArray.put(toObj);

        JSONObject emailJson = new JSONObject();
        emailJson.put("from", from);
        emailJson.put("to", toArray);
        emailJson.put("subject", subject);
        emailJson.put("htmlbody", donationThankYou.getDonationThankYouTemplate(invoiceHeader, donationDetails));

		return emailJson.toString();
    }


	@SuppressWarnings("deprecation")
	public void sendZeptoEmail(EmailServiceDetails emailService, String jsonParameter) throws MessagingException, IOException {

//	        String postUrl = "https://api.zeptomail.in/v1.1/email";
	        BufferedReader br = null;
	        HttpURLConnection conn = null;
	        String output = null;
	        StringBuffer sb = new StringBuffer();
	        try {
	            URL url = new URL(emailService.getHost());
	            conn = (HttpURLConnection) url.openConnection();
	            conn.setDoOutput(true);
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setRequestProperty("Accept", "application/json");
	            conn.setRequestProperty("Authorization", "Zoho-enczapikey "+emailService.getEmailPassword());
//	            JSONObject object = new JSONObject(this.setParameter());
	            OutputStream os = conn.getOutputStream();
	            os.write(jsonParameter.toString().getBytes());
	            os.flush();
	            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	            while ((output = br.readLine()) != null) {
	                sb.append(output);
	            }
	            System.out.println(sb.toString());
	        } catch (Exception e) {
	            br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
	            while ((output = br.readLine()) != null) {
	                sb.append(output);
	            }
	            System.out.println(sb.toString());
	        } finally {
	            try {
	                if (br != null) {
	                    br.close();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            try {
	                if (conn != null) {
	                    conn.disconnect();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();

	            }
	        }
	    }

}
