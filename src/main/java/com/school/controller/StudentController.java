package com.school.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.common.object.response.GenricResponse;
import com.common.object.response.Response;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.school.entities.StudentDetails;
import com.school.helper.StudentHelper;
import com.school.object.request.StudentRequestObject;
import com.school.pdf.AdmissionFormPdf;
import com.school.services.StudentService;


@CrossOrigin(origins = "*")
@RestController
public class StudentController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private	StudentService studentService;
	
	@Autowired
	private StudentHelper studentHelper;
	
	@Autowired
	private AdmissionFormPdf admissionFormPdf;
	


	@RequestMapping(path = "addStudent", method = RequestMethod.POST)
	public Response<StudentRequestObject> addStudent(@RequestBody Request<StudentRequestObject> studentRequestObject,
			HttpServletRequest request) {
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
	
	@RequestMapping(path = "updateStudent", method = RequestMethod.POST)
	public Response<StudentRequestObject> updateStudent(@RequestBody Request<StudentRequestObject> studentRequestObject,
			HttpServletRequest request) {
		GenricResponse<StudentRequestObject> responseObj = new GenricResponse<StudentRequestObject>();
		try {
			StudentRequestObject response = studentService.updateStudent(studentRequestObject);
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
	
	
	@GetMapping("downloadAdmissionForm")
	public void downloadReceiptPdf(
	        @RequestParam("id") Long id,
	        HttpServletResponse response) throws Exception {

	    ByteArrayOutputStream baos = new ByteArrayOutputStream();

	    PdfWriter writer = new PdfWriter(baos);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf, PageSize.A4);

	    StudentDetails studentDetails = studentHelper.getStudentDetailsById(id);
	    admissionFormPdf.generate(document, studentDetails);
	    document.close();

	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=Addmission_Form.pdf");

	    response.getOutputStream().write(baos.toByteArray());
	    response.getOutputStream().flush();
	}

}
