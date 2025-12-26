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

import com.common.entities.UserDetails;
import com.ngo.dao.SmsTemplateDetailsDao;
import com.ngo.email.template.DatfuslabWelcome;
import com.ngo.email.template.DonationThankYou;
import com.ngo.entities.DonationDetails;
import com.ngo.entities.SmsTemplateDetails;
import com.ngo.helper.DonationHelper;
import com.ngo.helper.InvoiceHelper;
import com.ngo.services.InvoiceService;
import com.spring.common.PdfInvoice;

@Component
public class ZeptoEmail {


	@Autowired
	private DonationThankYou donationThankYou;
	
	@Autowired
	private DatfuslabWelcome datfuslabWelcome;

	
	public JSONObject setParameter(DonationDetails donationDetails) {
        JSONObject from = new JSONObject();
        from.put("address", "noreply@cefinternational.org");
        from.put("name", "Cef International");
//        from.put("address", "noreply@datfuslab.com");
//        from.put("name", "Datfuslab Technologies ");

        JSONObject toEmailAddress = new JSONObject();
        toEmailAddress.put("address", donationDetails.getEmailId());
//        toEmailAddress.put("name", "Datfuslab");

        JSONObject toObj = new JSONObject();
        toObj.put("email_address", toEmailAddress);

        JSONArray toArray = new JSONArray();
        toArray.put(toObj);

        JSONObject emailJson = new JSONObject();
        emailJson.put("from", from);
        emailJson.put("to", toArray);
        emailJson.put("subject", "We're Grateful for Your Support Download Your 80G Receipt");
        emailJson.put("htmlbody", donationThankYou.getDonationThankYouTemplate(donationDetails));

		return emailJson;
    }


	public void sendZeptoEmail(DonationDetails donationDetails) throws MessagingException, IOException {

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
	            conn.setRequestProperty("Authorization", "Zoho-enczapikey PHtE6r1bQ73u2GJ+pxFV7KXuEJOiMt4s/OIyLgYS5NlGWPcLSk1T+t8tx2Dm+Rh4V/dDHaGcwIthubzP5rnRdm/oYD1LVGqyqK3sx/VYSPOZsbq6x00fsFgbf0bZVITpcdBq1yXVv9zYNA==");
//	            conn.setRequestProperty("Authorization", "Zoho-enczapikey PHtE6r1cQb+4jjMto0MA5/bpFMH1NY4q+LlhJQkRs4tKDvJXHU1Vr9svlze0qBYvUfhLHKXPwdpos+mf5rmHd2zpM2cdWGqyqK3sx/VYSPOZsbq6x00etF0bdk3cUoPqc9Jr0y3WvNfdNA==");
//	            JSONObject object = new JSONObject(this.setParameter());
	            OutputStream os = conn.getOutputStream();
	            os.write(this.setParameter(donationDetails).toString().getBytes());
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
