package com.spring.services;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.constant.Constant;
import com.spring.entities.LeadDetails;
import com.spring.entities.ResumeDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.LeadHelper;
import com.spring.helper.ResumeHelper;
import com.spring.object.request.ResumeRequestObject;
import com.spring.object.request.Request;


@Service
public class ResumeService {
	
	@Autowired
	private ResumeHelper resumeHelper;
	
	
	@Transactional
	public ResumeRequestObject addResume(Request<ResumeRequestObject> resumeRequestObject) 
			throws BizException, Exception {
		ResumeRequestObject resumeRequest = resumeRequestObject.getPayload();
		resumeHelper.validateResumeRequest(resumeRequest);
		
		ResumeDetails existsResume = resumeHelper.getResumeDetailsByCandidateMobile(resumeRequest.getMobileNo());
		if(existsResume == null) {
			
			ResumeDetails resumeDetails = resumeHelper.getResumeDetailsByReqObj(resumeRequest);
			resumeDetails = resumeHelper.saveResumeDetails(resumeDetails);
			
			//send sms
			
			resumeRequest.setRespCode(Constant.SUCCESS_CODE);
			resumeRequest.setRespMesg("Successfully Submitted");
			return resumeRequest;
		}else {
			resumeRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			resumeRequest.setRespMesg("Resume Already Registered");
			return resumeRequest;
		}
	}


	public List<ResumeDetails> getResumeDetails(Request<ResumeRequestObject> resumeRequestObject) {
		ResumeRequestObject resumeRequest = resumeRequestObject.getPayload();
		List<ResumeDetails> resumeList = resumeHelper.getResumeDetails(resumeRequest);
		return resumeList;
	}
	
	

}

