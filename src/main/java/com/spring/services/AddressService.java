package com.spring.services;

import java.util.List;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.spring.constant.Constant;
import com.spring.entities.AddressDetails;
import com.spring.entities.UserDetails;
import com.spring.exceptions.BizException;
import com.spring.helper.AddressHelper;
import com.spring.helper.CustomerHelper;
import com.spring.helper.UserHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.AddressRequestObject;
import com.spring.object.request.Request;
import com.spring.object.request.UserRequestObject;


@Service
public class AddressService {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());


	@Autowired
	private AddressHelper addressHelper;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	
	

	@Transactional
	public AddressDetails saveAddressDetailsByRequest(AddressRequestObject addressRequest, Long id, String superadminId)
			throws BizException {
		addressHelper.validateAddressRequest(addressRequest);

		AddressDetails addressDetails = addressHelper.getAddressDetailsByReqObj(addressRequest, id, superadminId);
		addressHelper.saveAddressDetails(addressDetails);
		return addressDetails;
		
	}
	
	@Transactional
	public AddressDetails updateAddressDetailsByRequest(AddressRequestObject addressRequest, AddressDetails addressDetails)
			throws BizException {
		addressHelper.validateAddressRequest(addressRequest);

		addressHelper.getUpdatedAddressDetailsByReqObj(addressRequest, addressDetails);
		addressHelper.updateAddressDetails(addressDetails);
		return addressDetails;
		
	}


	
	
//	public List<UserDetails> getUserDetails(Request<UserRequestObject> userRequestObject) {
//		UserRequestObject userRequest = userRequestObject.getPayload();
//		List<UserDetails> userList = userHelper.getUserDetails(userRequest);
//		return userList;
//	}

}
