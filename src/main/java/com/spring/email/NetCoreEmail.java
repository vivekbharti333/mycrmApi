package com.spring.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.common.PdfInvoice;
import com.spring.common.SendEmailHelper;
import com.spring.entities.DonationDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.helper.InvoiceHelper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

@Component
public class NetCoreEmail {
	
    private static PdfInvoice pdfInvoice; // Keep 'static'


	
	public static void sendNetCoreEmail(DonationDetails donationDetails, InvoiceHeaderDetails invoiceHeaderDetails) {
        try {
        	
        	ByteArrayOutputStream pdfContent = pdfInvoice.generatePdfInvoice(donationDetails, invoiceHeaderDetails);
        	
            URL url = new URL("http://trans.nimbusitsolutions.com/api/data/"); // Example JSON API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/4.0");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Prepare POST data
            Map<String, String> postData = new HashMap<>();
            postData.put("APP-ID", "ZpbQ9krjJl2DenhUsIdGTwVFLtOKq0mA");
            postData.put("SECRET-KEY", "5wW01PchK23b6UmCDBpadOTzIRsLXyot");
            postData.put("TEMPLATE_UID", "671a3e4241459");
            postData.put("SENDER_UID", "671a3e244abea");
            postData.put("TO", "vivekbharti333@gmail.com");
            postData.put("PARAMS", "{\"NAME\":\"Vivek\",\"COMPANY_NAME\":\"Datfuslab Technologies Pvt. Ltd.\"}");
            postData.put("DOCUMENT", pdfContent.toString());
//            postData.put("FILE[1]", "http://trans.adsxpedia.com/assets/uploads/document/1714452656_python_tutorial.pdf");

            // Convert postData map to URL encoded string
            StringBuilder postDataStringBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : postData.entrySet()) {
                if (postDataStringBuilder.length() != 0) postDataStringBuilder.append('&');
                postDataStringBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                postDataStringBuilder.append('=');
                postDataStringBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            // Write POST data to the connection
            try (OutputStream os = connection.getOutputStream();
                 OutputStreamWriter writer = new OutputStreamWriter(os, "UTF-8")) {
                writer.write(postDataStringBuilder.toString());
            }

            // Get the response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("Request successful");

                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    // Parse the response as JSON
                    JSONObject jsonObject = new JSONObject(response.toString());
                    System.out.println("status: " + jsonObject.getInt("status"));
                    System.out.println("message: " + jsonObject.getString("message"));
                }
            } else {
                System.out.println("Request failed with response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
