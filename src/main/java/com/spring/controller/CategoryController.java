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
import com.spring.entities.CategoryDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.ArticleRequestObject;
import com.spring.object.request.Request;
import com.spring.object.response.GenricResponse;
import com.spring.object.response.Response;
import com.spring.services.CategoryService;


@CrossOrigin(origins = "*")
@RestController
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	

	@RequestMapping(path = "addCategory", method = RequestMethod.POST)
	public Response<ArticleRequestObject>addCategory(@RequestBody Request<ArticleRequestObject> articleRequestObject, HttpServletRequest request)
	{
		GenricResponse<ArticleRequestObject> responseObj = new GenricResponse<ArticleRequestObject>();
		try {
			ArticleRequestObject responce =  categoryService.addCategory(articleRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getCategoryList", method = RequestMethod.POST)
	public Response<CategoryDetails> getCategoryList(@RequestBody Request<ArticleRequestObject> articleRequestObject) {
		GenricResponse<CategoryDetails> response = new GenricResponse<CategoryDetails>();
		try {
			List<CategoryDetails> categoryList = categoryService.getCategoryList(articleRequestObject);
			return response.createListResponse(categoryList, Constant.SUCCESS_CODE);
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
}
