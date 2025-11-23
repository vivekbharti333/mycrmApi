package com.spring.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.spring.constant.Constant;
import com.spring.exceptions.BizException;
import com.spring.object.request.AttendanceRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.AttendenceService;


@CrossOrigin(origins = "*")
@RestController
public class AttendanceController {
	
	@Autowired
	AttendenceService attendenceService;
	
	
	@RequestMapping(path = "markAttendance", method = RequestMethod.POST)
	public Response<AttendanceRequestObject>addDonation(@RequestBody Request<AttendanceRequestObject> attendanceRequestObject, HttpServletRequest request)
	{
		GenricResponse<AttendanceRequestObject> responseObj = new GenricResponse<AttendanceRequestObject>();
		
		System.out.println("Enter");
		try {
			AttendanceRequestObject responce =  attendenceService.markAttendance(attendanceRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
	
}
