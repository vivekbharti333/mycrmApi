package com.invoice.controller;

import java.util.List;

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
import com.invoice.entities.ProductDetails;
import com.invoice.object.request.ProductRequestObject;
import com.invoice.services.ProductService;

@CrossOrigin(origins = { "*" })
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping(path = "addProduct", method = RequestMethod.POST)
	public Response<ProductRequestObject> addProduct(@RequestBody Request<ProductRequestObject> productRequestObject) {
	    GenricResponse<ProductRequestObject> responseObj = new GenricResponse<>();
	    try {
	        ProductRequestObject response = productService.addProduct(productRequestObject);
	        return responseObj.createSuccessResponse(response, 200);
	    } catch (BizException e) {
	        return responseObj.createErrorResponse(400, e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        return responseObj.createErrorResponse(500, "Internal Server Error");
	    }
	}
	
	@RequestMapping(path = "getProductDetails", method = RequestMethod.POST)
	public Response<ProductDetails> getProductDetails(@RequestBody Request<ProductRequestObject> productRequestObject) {
		GenricResponse<ProductDetails> response = new GenricResponse<ProductDetails>();
		try {
			List<ProductDetails> customerList = productService.getProductDetails(productRequestObject);
			return response.createListResponse(customerList, 200, String.valueOf(customerList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.BAD_REQUEST_CODE, e.getMessage());
		}
	}

}
