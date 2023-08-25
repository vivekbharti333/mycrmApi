package com.spring.services;

import java.util.List;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.spring.constant.Constant;
import com.spring.entities.AddressDetails;
import com.spring.entities.CustomerDetails;
import com.spring.entities.UserDetails;
import com.spring.enums.RoleType;
import com.spring.exceptions.BizException;
import com.spring.helper.AddressHelper;
import com.spring.helper.CustomerHelper;
import com.spring.helper.UserHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.AddressRequestObject;
import com.spring.object.request.CustomerRequestObject;
import com.spring.object.request.Request;
import com.spring.object.request.UserRequestObject;


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
		List<CustomerDetails> customerList = customerHelper.getCustomerList(customerRequest);
		return customerList;
	}
	
	
//	public List<UserDetails> getUserDetails(Request<UserRequestObject> userRequestObject) {
//		UserRequestObject userRequest = userRequestObject.getPayload();
//		List<UserDetails> userList = userHelper.getUserDetails(userRequest);
//		return userList;
//	}

}
