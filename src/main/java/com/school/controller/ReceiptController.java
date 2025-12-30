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
import com.school.object.request.SchoolReceiptRequest;
import com.school.services.ReceiptService;


@CrossOrigin(origins = "*")
@RestController
public class ReceiptController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private	ReceiptService receiptService;
	


	@RequestMapping(path = "addReceipt", method = RequestMethod.POST)
	public Response<SchoolReceiptRequest> addStudent(@RequestBody Request<SchoolReceiptRequest> SchoolReceiptRequest,
			HttpServletRequest request) {
		GenricResponse<SchoolReceiptRequest> responseObj = new GenricResponse<SchoolReceiptRequest>();
		try {
			SchoolReceiptRequest response = receiptService.addReceipt(SchoolReceiptRequest);
			return responseObj.createSuccessResponse(response, Constant.SUCCESS_CODE);
		} catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
//	@RequestMapping(path = "updateStudent", method = RequestMethod.POST)
//	public Response<SchoolReceiptRequest> updateStudent(@RequestBody Request<SchoolReceiptRequest> SchoolReceiptRequest,
//			HttpServletRequest request) {
//		GenricResponse<SchoolReceiptRequest> responseObj = new GenricResponse<SchoolReceiptRequest>();
//		try {
//			SchoolReceiptRequest response = studentService.updateStudent(SchoolReceiptRequest);
//			return responseObj.createSuccessResponse(response, Constant.SUCCESS_CODE);
//		} catch (BizException e) {
//			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
//		}
//	}

	
	@RequestMapping(path = "getReceiptDetails", method = RequestMethod.POST)
	public Response<SchoolReceiptRequest> getReceiptDetails(@RequestBody Request<SchoolReceiptRequest> SchoolReceiptRequest) {
		GenricResponse<SchoolReceiptRequest> response = new GenricResponse<SchoolReceiptRequest>();
		try {
			List<SchoolReceiptRequest> receiptList = receiptService.getReceiptDetails(SchoolReceiptRequest);
			return response.createListResponse(receiptList, 200, String.valueOf(receiptList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		}
	}
}
