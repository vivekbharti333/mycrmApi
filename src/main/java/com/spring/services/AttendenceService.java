package com.spring.services;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.AttendanceDetails;
import com.spring.entities.UserDetails;
import com.spring.enums.RequestFor;
import com.spring.exceptions.BizException;
import com.spring.helper.AttendanceHelper;
import com.spring.helper.FaceRecognitionHelper;
import com.spring.helper.UserHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.AttendanceRequestObject;
import com.spring.object.request.Request;
import com.squareup.okhttp.Response;


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


@Service
public class AttendenceService {
	
	@Autowired
	private AttendanceHelper attendanceHelper;
	
	@Autowired
	private FaceRecognitionHelper faceRecognitionHelper;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserHelper userHelper;
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	


	public AttendanceRequestObject markAttendance(Request<AttendanceRequestObject> attendanceRequestObject) 
			throws BizException, Exception {
		AttendanceRequestObject attendanceRequest = attendanceRequestObject.getPayload();
		attendanceHelper.validateAttendanceRequest(attendanceRequest);
		
		Boolean isValid = jwtTokenUtil.validateJwtToken(attendanceRequest.getCreatedBy(), attendanceRequest.getToken());
		logger.info("Mark Attendance Is valid? : "+attendanceRequest.getCreatedBy()+" is " + isValid);

		if (isValid) {
			
			
			
			UserDetails userDetails = userHelper.getUserDetailsByLoginId(attendanceRequest.getCreatedBy());
			if (userDetails != null) {
				if(!userDetails.getUserPicture().isEmpty()) {
					
					if(attendanceRequest.getRequestFor().equals(RequestFor.PUNCHIN.name())) {
						//Response response =  faceRecognitionHelper.faceRecognitionApi(userDetails.getUserPicture(), attendanceRequest.getPunchInImage());
						 String response = "{\"protocol\":\"http/1.1\", \"code\":200, \"message\":\"OK\", \"url\":\"https://face-verification2.p.rapidapi.com/faceverification\"}";

					        if (response != null) {
					            JSONObject obj = new JSONObject(response);
					            if(obj.getInt("code") == 200) {
					            	attendanceRequest.setPunchInStatus("MARKED");
					            	AttendanceDetails attendanceDetails = attendanceHelper.markPunchInAttendance(attendanceRequest);
					            	attendanceDetails = attendanceHelper.saveAttendanceDetails(attendanceDetails);
					            	
					            	attendanceRequest.setRespCode(Constant.SUCCESS_CODE);
					            	attendanceRequest.setRespMesg(obj.get("message").toString());
					            	return attendanceRequest;
					            }
					        }
						
						logger.info("Punchin Response : "+response);
					}else if(attendanceRequest.getRequestFor().equals(RequestFor.PUNCHOUT.name())) {
						Response response =  faceRecognitionHelper.faceRecognitionApi(userDetails.getUserPicture(), attendanceRequest.getPunchInImage());
						logger.info("Punchout Response : "+response);
					}
					
					
					
//					Response{protocol=http/1.1, code=200, message=OK, url=https://face-verification2.p.rapidapi.com/faceverification}
					
					
				}else {
					//user pic not available
				}
				
			}else {
				//user not found
			}
			
			
			
		}
		
		return null;
	}
	
	
	public void amazonApi() throws Exception{
	       Float similarityThreshold = 70F;
//	       String sourceImage = "source.jpg";
//	       String targetImage = "target.jpg";
	       String sourceImage = "C:\\Users\\HP\\Desktop\\abc.jpg";
	       String targetImage = "C:\\Users\\HP\\Desktop\\cde.jpg";
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
	   }



	
	

}

