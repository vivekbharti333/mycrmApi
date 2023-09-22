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
			.url("https://face-verification2.p.rapidapi.com/faceverification")
			.post(body)
			.addHeader("content-type", "application/x-www-form-urlencoded")
			.addHeader("X-RapidAPI-Key", "322c136920msh0ec70ceb6624fe3p1d8f74jsn335785bb48fb")
			.addHeader("X-RapidAPI-Host", "face-verification2.p.rapidapi.com")
			.build();

		Response response = client.newCall(request).execute();
		
		return response;
	}

}
