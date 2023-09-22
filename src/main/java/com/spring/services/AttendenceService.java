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



	
	

}

