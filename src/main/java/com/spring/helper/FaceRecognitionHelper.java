package com.spring.helper;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Component
public class FaceRecognitionHelper {

	
	public Response faceRecognitionApi(String originalImg, String currentImg) throws IOException {
		
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "image1Base64=data:image/png;base64,"+originalImg+"&image2Base64="+currentImg);
		Request request = new Request.Builder()
			.url("https://face-recognition18.p.rapidapi.com/register_face")
			.post(body)
			.addHeader("content-type", "application/x-www-form-urlencoded")
			.addHeader("X-RapidAPI-Key", "8dff77f6ddmsh92fd83a2e3dbf23p1f0e87jsn532ed24eea2d")
			.addHeader("X-RapidAPI-Host", "face-recognition18.p.rapidapi.com")
			.build();

		Response response = client.newCall(request).execute();
		
		return response;
	}

}
