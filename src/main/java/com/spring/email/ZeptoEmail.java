package com.spring.email;

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

import com.spring.common.PdfInvoice;
import com.spring.dao.SmsTemplateDetailsDao;
import com.spring.entities.SmsTemplateDetails;
import com.spring.helper.DonationHelper;
import com.spring.helper.InvoiceHelper;
import com.spring.services.InvoiceService;

@Component
public class ZeptoEmail {


	@Autowired
	private SmsTemplateDetailsDao smsDetailsDao;

	
//	StringUtils.substring(RandomStringUtils.random(64, false, true), 0,6)
	
	public JSONObject setParameter() {
        JSONObject from = new JSONObject();
        from.put("address", "noreply@datfuslab.com");
        from.put("name", "Datfuslab Technologies Pvt. Ltd.");

        JSONObject toEmailAddress = new JSONObject();
        toEmailAddress.put("address", "vivekbharti333@gmail.com");
        toEmailAddress.put("name", "Datfuslab");

        JSONObject toObj = new JSONObject();
        toObj.put("email_address", toEmailAddress);

        JSONArray toArray = new JSONArray();
        toArray.put(toObj);

        JSONObject emailJson = new JSONObject();
        emailJson.put("from", from);
        emailJson.put("to", toArray);
        emailJson.put("subject", "This is Test Email");
        emailJson.put("htmlbody", "<div><b> Test email sent successfully.  </b></div>");

        System.out.println(emailJson.toString(2)); // Pretty print
		return emailJson;
    }


	public void sendEmailWithAttachments() throws MessagingException, IOException {

	        String postUrl = "https://api.zeptomail.in/v1.1/email";
	        BufferedReader br = null;
	        HttpURLConnection conn = null;
	        String output = null;
	        StringBuffer sb = new StringBuffer();
	        try {
	            URL url = new URL(postUrl);
	            conn = (HttpURLConnection) url.openConnection();
	            conn.setDoOutput(true);
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setRequestProperty("Accept", "application/json");
	            conn.setRequestProperty("Authorization", "Zoho-enczapikey PHtE6r1cQb+4jjMto0MA5/bpFMH1NY4q+LlhJQkRs4tKDvJXHU1Vr9svlze0qBYvUfhLHKXPwdpos+mf5rmHd2zpM2cdWGqyqK3sx/VYSPOZsbq6x00etF0bdk3cUoPqc9Jr0y3WvNfdNA==");
//	            JSONObject object = new JSONObject(this.setParameter());
	            OutputStream os = conn.getOutputStream();
	            os.write(this.setParameter().toString().getBytes());
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
