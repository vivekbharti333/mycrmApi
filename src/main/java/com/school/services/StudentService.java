package com.school.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.school.entities.StudentDetails;
import com.school.helper.StudentHelper;
import com.school.object.request.StudentRequestObject;

@Service
public class StudentService {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private StudentHelper studentHelper;

	public StudentRequestObject addStudent(Request<StudentRequestObject> studentRequestObject) throws BizException {
		StudentRequestObject studentRequest = studentRequestObject.getPayload();
		studentHelper.validateStudentRequest(studentRequest);

		StudentDetails existsStudentDetails = studentHelper.getStudentDetailsByAdmissionNumber(studentRequest.getAdmissionNo());
		if (existsStudentDetails == null) {
			StudentDetails studentDetails = studentHelper.getStudentDetailsByReqObj(studentRequest);
			studentDetails = studentHelper.saveStudentDetails(studentDetails);

			studentRequest.setRespCode(Constant.SUCCESS_CODE);
			studentRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
			return studentRequest;
		} else {
			studentRequest.setRespCode(Constant.ALREADY_EXISTS);
			studentRequest.setRespMesg(Constant.ALLREADY_EXISTS_MSG);
			return studentRequest;
		}
	}
	
	public StudentRequestObject updateStudent(Request<StudentRequestObject> studentRequestObject) throws BizException {
		StudentRequestObject studentRequest = studentRequestObject.getPayload();
		studentHelper.validateStudentRequest(studentRequest);

		StudentDetails studentDetails = studentHelper.getStudentDetailsById(studentRequest.getId());
		if (studentDetails != null) {
			studentDetails = studentHelper.getUpdatedStudentDetailsByReqObj(studentRequest, studentDetails);
			studentDetails = studentHelper.saveStudentDetails(studentDetails);

			studentRequest.setRespCode(Constant.SUCCESS_CODE);
			studentRequest.setRespMesg(Constant.UPDATED_SUCCESS);
			return studentRequest;
		} else {
			studentRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			studentRequest.setRespMesg(Constant.DATA_NOT_FOUND);
			return studentRequest;
		}
	}

	public List<StudentDetails> getStudentDetails(Request<StudentRequestObject> studentRequestObject) {
		StudentRequestObject studentRequest = studentRequestObject.getPayload();
		List<StudentDetails> addressList = studentHelper.getStudentDetails(studentRequest);
		return addressList;
	}

}
