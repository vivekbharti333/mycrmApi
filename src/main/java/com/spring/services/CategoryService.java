package com.spring.services;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.constant.Constant;
import com.spring.entities.CategoryDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.CategoryHelper;
import com.spring.object.request.ArticleRequestObject;
import com.spring.object.request.Request;


@Service
public class CategoryService {
	
	@Autowired
	private CategoryHelper categoryHelper;
	
	
	@Transactional
	public ArticleRequestObject addCategory(Request<ArticleRequestObject> ArticleRequestObject) 
			throws BizException, Exception {
		ArticleRequestObject leadRequest = ArticleRequestObject.getPayload();
		categoryHelper.validateArticleRequest(leadRequest);
		
		CategoryDetails existsCategoryDetails = categoryHelper.getcategoryDetailsByNameAndCode(leadRequest);
		if(existsCategoryDetails == null) {
			
			CategoryDetails categoryDetails = categoryHelper.getCategoryDetailsByReqObj(leadRequest);
			categoryDetails = categoryHelper.saveCategoryDetails(categoryDetails);
			
			leadRequest.setRespCode(Constant.SUCCESS_CODE);
			leadRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
			return leadRequest;
		}else {
			leadRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			leadRequest.setRespMesg(Constant.REGISTERED_FAILED);
			return leadRequest;
		}
	}


	public List<CategoryDetails> getCategoryList(Request<ArticleRequestObject> ArticleRequestObject) {
		ArticleRequestObject leadRequest = ArticleRequestObject.getPayload();
		List<CategoryDetails> categoryList = categoryHelper.getCategoryList(leadRequest);
		return categoryList;
	}
	
	

}

