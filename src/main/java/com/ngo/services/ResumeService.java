package com.ngo.services;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.ngo.entities.ResumeDetails;
import com.ngo.helper.ResumeHelper;
import com.common.object.request.Request;
import com.ngo.object.request.ResumeRequestObject;


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
			
			if(resumeRequest.getResume() != null) {
				resumeRequest.setResumeUploaded("YES");
			}else {
				resumeRequest.setResumeUploaded("NO");
			}
			
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

