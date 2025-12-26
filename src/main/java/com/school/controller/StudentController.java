package com.school.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.common.object.response.GenricResponse;
import com.common.object.response.Response;
import com.school.entities.StudentDetails;
import com.school.object.request.StudentRequestObject;
import com.school.services.StudentService;


@CrossOrigin(origins = "*")
@RestController
public class StudentController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private	StudentService studentService;
	


	@RequestMapping(path = "addStudent", method = RequestMethod.POST)
	public Response<StudentRequestObject> addStudent(@RequestBody Request<StudentRequestObject> studentRequestObject,
			HttpServletRequest request) {
		System.out.println("Enter");
		GenricResponse<StudentRequestObject> responseObj = new GenricResponse<StudentRequestObject>();
		try {
			StudentRequestObject response = studentService.addStudent(studentRequestObject);
			return responseObj.createSuccessResponse(response, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
	@RequestMapping(path = "getStudentDetails", method = RequestMethod.POST)
	public Response<StudentDetails> getStudentDetails(@RequestBody Request<StudentRequestObject> studentRequestObject) {
		GenricResponse<StudentDetails> response = new GenricResponse<StudentDetails>();
		try {
			List<StudentDetails> studentList = studentService.getStudentDetails(studentRequestObject);
			return response.createListResponse(studentList, 200, String.valueOf(studentList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		}
	}
}
