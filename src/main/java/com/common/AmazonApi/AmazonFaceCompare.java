package com.common.AmazonApi;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.Image;
import com.common.enums.Status;
import com.ngo.entities.ApiDetails;
import com.ngo.helper.APIHelper;
import com.ngo.object.request.AttendanceRequestObject;
import com.spring.common.Base64Image;

@Component
public class AmazonFaceCompare {
	
	@Autowired
	private APIHelper apiHelper;
	
	@Autowired
	private Base64Image base64Image;

	public AttendanceRequestObject amazonFaceCompare(AttendanceRequestObject attendanceRequest) {
//	    Map<String, Object> resultMap = new HashMap<>();
		try {
			// AWS credentials
			ApiDetails apiDetails = apiHelper.getApiDetailsByServiceProvider("AMAZON" , "FACE_COMPARE");
			
			BasicAWSCredentials awsCreds = new BasicAWSCredentials(apiDetails.getApiKeys(), apiDetails.getApiValue());
			
			AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
					.withRegion(Regions.US_EAST_1).withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

			// Convert source and target images
			byte[] sourceBytes = decodeBase64Image(attendanceRequest.getOriginalImage());
			byte[] targetBytes = decodeBase64Image(attendanceRequest.getClickImage());
//			byte[] sourceBytes = decodeBase64Image(base64Image.base64FirstImage());
//			byte[] targetBytes = decodeBase64Image(base64Image.base64SecondImage());

			Image source = new Image().withBytes(ByteBuffer.wrap(sourceBytes));
			Image target = new Image().withBytes(ByteBuffer.wrap(targetBytes));

			CompareFacesRequest request = new CompareFacesRequest().withSourceImage(source).withTargetImage(target)
					.withSimilarityThreshold(70F);

			CompareFacesResult compareResult = rekognitionClient.compareFaces(request);

			List<CompareFacesMatch> matches = compareResult.getFaceMatches();

			if (!matches.isEmpty()) {
				attendanceRequest.setSimilarity(matches.get(0).getSimilarity());
				attendanceRequest.setStatus(Status.MATCH.name());
				attendanceRequest.setRespMesg("Face matched successfully");
			} else {
				attendanceRequest.setSimilarity(matches.get(0).getSimilarity());
				attendanceRequest.setStatus(Status.NOT_MATCH.name());
				attendanceRequest.setRespMesg("No face matched");
			}

//	        if (!matches.isEmpty()) {
//	            float similarity = matches.get(0).getSimilarity();
//	            resultMap.put("status", "MATCH");
//	            resultMap.put("similarity", similarity);
//	            resultMap.put("message", "Face matched successfully");
//	        } else {
//	            resultMap.put("status", "NO_MATCH");
//	            resultMap.put("similarity", 0);
//	            resultMap.put("message", "No face matched");
//	        }

		} catch (Exception e) {
			e.printStackTrace();
			attendanceRequest.setStatus("ERROR");
			attendanceRequest.setRespMesg(e.getMessage());
		}
		return attendanceRequest;
	}

	private byte[] decodeBase64Image(String base64Image) {
		if (base64Image == null || base64Image.isEmpty()) {
			throw new IllegalArgumentException("Base64 image cannot be null or empty");
		}

		// Remove prefix if present
		if (base64Image.startsWith("data:image")) {
			base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
		}

		return Base64.getDecoder().decode(base64Image);
	}

}
