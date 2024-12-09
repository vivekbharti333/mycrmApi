package com.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.spring.constant.Constant;
import com.spring.entities.ResumeDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.ResumeRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.ResumeService;


@CrossOrigin(origins = "*")
@RestController
public class ResumeController {
	
	@Autowired
	ResumeService resumeService;
	

	@RequestMapping(path = "addResume", method = RequestMethod.POST)
	public Response<ResumeRequestObject>addResume(@RequestBody Request<ResumeRequestObject> resumeRequestObject, HttpServletRequest request)
	{
		GenricResponse<ResumeRequestObject> responseObj = new GenricResponse<ResumeRequestObject>();
		try {
			ResumeRequestObject responce =  resumeService.addResume(resumeRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getResumeDetails", method = RequestMethod.POST)
	public Response<ResumeDetails> getResumeDetails(@RequestBody Request<ResumeRequestObject> ResumeRequestObject) {
		GenricResponse<ResumeDetails> response = new GenricResponse<ResumeDetails>();
		try {
			List<ResumeDetails> leadList = resumeService.getResumeDetails(ResumeRequestObject);
			return response.createListResponse(leadList, Constant.SUCCESS_CODE, String.valueOf(leadList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
}
