package com.spring.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Component;



@Component
public class ShortUrl {
	
	public String shortUrl(String paymentUrl) throws IOException {
		 String endpoint = "http://tinyurl.com/api-create.php?url=" + URLEncoder.encode(paymentUrl, "UTF-8");
	        URL url = new URL(endpoint);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	        conn.setRequestMethod("GET");

	        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String shortUrl = reader.readLine();

	        reader.close();
	        conn.disconnect();

	        System.out.println("hgh : "+shortUrl);
			return shortUrl; 
	}
}
