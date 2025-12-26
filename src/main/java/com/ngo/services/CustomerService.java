package com.ngo.services;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.common.constant.Constant;
import com.common.entities.UserDetails;
import com.common.enums.RoleType;
import com.common.exceptions.BizException;
import com.common.helper.AddressHelper;
import com.common.helper.UserHelper;
import com.common.jwt.JwtTokenUtil;
import com.ngo.entities.AddressDetails;
import com.ngo.entities.CustomerDetails;
import com.ngo.helper.CustomerHelper;
import com.ngo.object.request.AddressRequestObject;
import com.ngo.object.request.CustomerRequestObject;
import com.common.object.request.Request;
import com.common.object.request.UserRequestObject;


@Service
public class CustomerService {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private CustomerHelper customerHelper;

	@Autowired
	private AddressService addressService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	

	@Transactional
	public CustomerRequestObject customerRegistration(Request<CustomerRequestObject> customerRequestObject)
			throws BizException, Exception {
		CustomerRequestObject customerRequest = customerRequestObject.getPayload();
		customerHelper.validateCustomerRequest(customerRequest);

		Boolean isValid = jwtTokenUtil.validateJwtToken(customerRequest.getCreatedBy(), customerRequest.getToken());
		logger.info("Usere Registration Is valid? : " + customerRequest.getCreatedBy() + " is " + isValid);

		if (isValid) {
			CustomerDetails existsCustomerDetails = customerHelper
					.getCustomerDetailsByGstNo(customerRequest.getGstNumber());
			if (existsCustomerDetails == null) {
				CustomerDetails customerDetails = customerHelper.getCustomerDetailsByReqObj(customerRequest);
				customerHelper.saveCustomerDetails(customerDetails);

				// address
				for (AddressRequestObject addressRequest : customerRequest.getAddressList()) {
					addressRequest.setUserType(RoleType.CUSTOMER.name());
					addressService.saveAddressDetailsByRequest(addressRequest, customerDetails.getId(),
							customerRequest.getSuperadminId());
				}

				customerRequest.setRespCode(Constant.SUCCESS_CODE);
				customerRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
				return customerRequest;

			} else {
				customerRequest.setRespCode(Constant.ALREADY_EXISTS);
				customerRequest.setRespMesg("GST No. Already Exists");
				return customerRequest;
			}

		} else {
			customerRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
			customerRequest.setRespMesg(Constant.INVALID_TOKEN);
			return customerRequest;
		}
	}
		

	public List<CustomerDetails> getCustomerList(Request<CustomerRequestObject> customerRequestObject) {
		CustomerRequestObject customerRequest = customerRequestObject.getPayload();
		Boolean isValid = jwtTokenUtil.validateJwtToken(customerRequest.getCreatedBy(), customerRequest.getToken());
		List<CustomerDetails> customerList = new ArrayList<CustomerDetails>();
		if (isValid) {
			customerList = customerHelper.getCustomerList(customerRequest);
			return customerList;
		}
		return customerList;
	}
	
	

}
