package com.common.controller.paymentgateway;

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
import com.ngo.object.request.CashfreeRequestObject;
import com.ngo.services.CashfreeService;

@CrossOrigin(origins = "*")
@RestController
public class CashfreeController {

	private static final Logger log = Logger.getLogger(CashfreeController.class);

	
	@Autowired CashfreeService cashfreeService;

	@RequestMapping(path = "updatePgPaymentStatusByCashfree", method = RequestMethod.POST)
	public Response<CashfreeRequestObject>updatePgPaymentStatusByCashfree(@RequestBody Request<CashfreeRequestObject> cashfreeRequestObject, HttpServletRequest request)
	{
		GenricResponse<CashfreeRequestObject> responseObj = new GenricResponse<CashfreeRequestObject>();
			try {
				CashfreeRequestObject responce =  cashfreeService.updatePgPaymentStatusByCashfree(cashfreeRequestObject);
				return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
			}catch (BizException e) {
				return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
			} 
	 		catch (Exception e) {e.printStackTrace();
				return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
			}
		}
}
