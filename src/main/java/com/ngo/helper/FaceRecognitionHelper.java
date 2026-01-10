package com.ngo.helper;

import java.io.IOException;

import org.springframework.stereotype.Component;

//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.Response;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import com.amazonaws.util.IOUtils;

@Component
public class FaceRecognitionHelper {

	
//	public Response faceRecognitionApi(String originalImg, String currentImg) throws IOException {
		
//		OkHttpClient client = new OkHttpClient();
//
//		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//		RequestBody body = RequestBody.create(mediaType, "image1Base64=data:image/png;base64,"+originalImg+"&image2Base64="+currentImg);
//		Request request = new Request.Builder()
//			.url("https://face-recognition18.p.rapidapi.com/register_face")
//			.post(body)
//			.addHeader("content-type", "application/x-www-form-urlencoded")
//			.addHeader("X-RapidAPI-Key", "8dff77f6ddmsh92fd83a2e3dbf23p1f0e87jsn532ed24eea2d")
//			.addHeader("X-RapidAPI-Host", "face-recognition18.p.rapidapi.com")
//			.build();
//
//		Response response = client.newCall(request).execute();
//		
//		return response;
//	}
	
	
	public String compareFace() throws Exception{
	       Float similarityThreshold = 70F;
	       String sourceImage = "C:\\Users\\HP\\Documents\\abc.png";
	       String targetImage = "C:\\Users\\HP\\Documents\\abc.png";
	       ByteBuffer sourceImageBytes=null;
	       ByteBuffer targetImageBytes=null;

	       AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

	       //Load source and target images and create input parameters
	       try (InputStream inputStream = new FileInputStream(new File(sourceImage))) {
	          sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
	       }
	       catch(Exception e)
	       {
	           System.out.println("Failed to load source image " + sourceImage);
	           System.exit(1);
	       }
	       try (InputStream inputStream = new FileInputStream(new File(targetImage))) {
	           targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
	       }
	       catch(Exception e)
	       {
	           System.out.println("Failed to load target images: " + targetImage);
	           System.exit(1);
	       }
	       

	       Image source=new Image()
	            .withBytes(sourceImageBytes);
	       Image target=new Image()
	            .withBytes(targetImageBytes);

	       CompareFacesRequest request = new CompareFacesRequest()
	               .withSourceImage(source)
	               .withTargetImage(target)
	               .withSimilarityThreshold(similarityThreshold);

	       // Call operation
	       CompareFacesResult compareFacesResult=rekognitionClient.compareFaces(request);


	       // Display results
	       List <CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
	       for (CompareFacesMatch match: faceDetails){
	         ComparedFace face= match.getFace();
	         BoundingBox position = face.getBoundingBox();
	         System.out.println("Face at " + position.getLeft().toString()
	               + " " + position.getTop()
	               + " matches with " + match.getSimilarity().toString()
	               + "% confidence.");

	       }
	       List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();

	       System.out.println("There was " + uncompared.size()
	            + " face(s) that did not match");
		return targetImage;
	   }

}
