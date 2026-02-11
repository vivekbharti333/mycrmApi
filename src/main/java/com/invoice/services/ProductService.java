package com.invoice.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.common.object.request.Request;
import com.invoice.entities.ProductDetails;
import com.invoice.helper.ProductHelper;
import com.invoice.object.request.ProductRequestObject;

@Service
public class ProductService {
	
	@Autowired
	private ProductHelper productHelper;
	
	
	@Transactional
	public ProductRequestObject addProduct(Request<ProductRequestObject> productRequestObject)
			throws BizException, Exception {

		ProductRequestObject productRequest = productRequestObject.getPayload();
		productHelper.validateProductRequest(productRequest);

		ProductDetails existsProductDetails = productHelper.getProductDetailsByName(productRequest.getProductName(), productRequest.getSuperadminId());
		if (existsProductDetails == null) {

			ProductDetails productDetails = productHelper.getProductDetailsByReqObj(productRequest);
			productDetails = productHelper.saveProductDetails(productDetails);

			productRequest.setRespCode(Constant.SUCCESS_CODE);
			productRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
			return productRequest;
		}
		productRequest.setRespCode(Constant.ALREADY_EXISTS);
		productRequest.setRespMesg(Constant.ALLREADY_EXISTS_MSG);
		return productRequest;
	}
	
	public List<ProductDetails> getProductDetails(Request<ProductRequestObject> productRequestObject) {
		ProductRequestObject productRequest = productRequestObject.getPayload();
		List<ProductDetails> productList = productHelper.getProductDetails(productRequest);
		return productList;
	}



}
