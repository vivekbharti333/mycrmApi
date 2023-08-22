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
import com.spring.entities.LeadDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.ArticleRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.LeadService;


@CrossOrigin(origins = "*")
@RestController
public class LeadController {
	
	@Autowired
	LeadService leadService;
	

	@RequestMapping(path = "regFreshLead", method = RequestMethod.POST)
	public Response<ArticleRequestObject>regFreshLead(@RequestBody Request<ArticleRequestObject> articleRequestObject, HttpServletRequest request)
	{
		GenricResponse<ArticleRequestObject> responseObj = new GenricResponse<ArticleRequestObject>();
		try {
			ArticleRequestObject responce =  leadService.regFreshLead(articleRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getLeadDetails", method = RequestMethod.POST)
	public Response<LeadDetails> getLeadDetails(@RequestBody Request<ArticleRequestObject> articleRequestObject) {
		GenricResponse<LeadDetails> response = new GenricResponse<LeadDetails>();
		try {
			List<LeadDetails> leadList = leadService.getLeadDetails(articleRequestObject);
			return response.createListResponse(leadList, Constant.SUCCESS_CODE);
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
}
