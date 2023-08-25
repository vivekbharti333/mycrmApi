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
import com.spring.helper.UserHelper;
import com.spring.jwt.JwtTokenUtil;
import com.spring.object.request.AddressRequestObject;
import com.spring.object.request.Request;
import com.spring.object.request.UserRequestObject;


@Service
public class UserService {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private UserHelper userHelper;

	@Autowired
	private AddressService addressService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public UserRequestObject doLogin(Request<UserRequestObject> userRequestObject) throws BizException, Exception {
		UserRequestObject userRequest = userRequestObject.getPayload();
		userHelper.validateUserRequest(userRequest);

		UserDetails userDetails = userHelper.getUserDetailsByLoginId(userRequest.getLoginId());
		if (userDetails != null) {

			if (BCrypt.checkpw(userRequest.getPassword(), userDetails.getPassword())) {
				logger.info("Login Successfully: "+userRequest);
				
				//generate secret key.
				String secretKey = jwtTokenUtil.generateSecretKey();
				
				//update secret key in UserDetails.
				userDetails.setSecretKey(secretKey);
				userHelper.UpdateUserDetails(userDetails);

				String token = jwtTokenUtil.generateAccessToken(userDetails);
				logger.info("Token : "+token);
						
				userRequest.setLoginId(userDetails.getLoginId());
				userRequest.setPassword(null);
				userRequest.setFirstName(userDetails.getFirstName());
				userRequest.setLastName(userDetails.getLastName());
				userRequest.setRoleType(userDetails.getRoleType());
				userRequest.setSuperadminId(userDetails.getSuperadminId());
				userRequest.setToken(token);

				userRequest.setRespCode(Constant.SUCCESS_CODE);
				userRequest.setRespMesg(Constant.LOGIN_SUCCESS);
				return userRequest;
			} else {
				userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
				userRequest.setRespMesg(Constant.INVALID_LOGIN);
				return userRequest;
			}
		}else {
			userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			userRequest.setRespMesg(Constant.INVALID_LOGIN);
			return userRequest;
		}
	}

	@Transactional
	public UserRequestObject userRegistration(Request<UserRequestObject> userRequestObject)
			throws BizException, Exception {
		UserRequestObject userRequest = userRequestObject.getPayload();
		userHelper.validateUserRequest(userRequest);

//		String password = userHelper.generateRandomChars("ABCD145pqrs678abcdef90EF9GHxyzIJKL5MNOPQRghijS1234560TUVWXYlmnoZ1234567tuvw890", 10);
//		userRequest.setPassword("test@123");

		Boolean isValid = jwtTokenUtil.validateJwtToken(userRequest.getCreatedBy(), userRequest.getToken());
		logger.info("Usere Registration Is valid? : "+userRequest.getCreatedBy()+" is " + isValid);

		if (isValid) {

			UserDetails existsUserDetails = userHelper.getUserDetailsByLoginId(userRequest.getMobileNo());
			if (existsUserDetails == null) {

				String password = BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt());
				userRequest.setPassword(password);

				UserDetails userDetails = userHelper.getUserDetailsByReqObj(userRequest);
				userDetails = userHelper.saveUserDetails(userDetails);

				logger.info("User Address : "+userRequest.getAddressList());
				
				// Save Address
				for (AddressRequestObject addressRequest : userRequest.getAddressList()) {
//					AddressDetails addressDetails = addressHelper.getAddressDetailsByReqObj(addressRequest, userDetails.getId(), userRequest.getSuperadminId());
//					addressDetails = addressHelper.saveAddressDetails(addressDetails);
					addressRequest.setUserType(userRequest.getRoleType());
					AddressDetails addressDetails = addressService.saveAddressDetailsByRequest(addressRequest, userDetails.getId(), userRequest.getSuperadminId());
					
				}

				// send sms

				userRequest.setRespCode(Constant.SUCCESS_CODE);
				userRequest.setRespMesg(Constant.REGISTERED_SUCCESS);
				return userRequest;
			} else {
				userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
				userRequest.setRespMesg(Constant.USER_EXIST);
				return userRequest;
			}
		} else {
			userRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
			userRequest.setRespMesg(Constant.INVALID_TOKEN);
			return userRequest;
		}
	}

	
	@Transactional
	public UserRequestObject updateUserDetails(Request<UserRequestObject> userRequestObject)
			throws BizException, Exception {
		UserRequestObject userRequest = userRequestObject.getPayload();
		userHelper.validateUserRequest(userRequest);

		UserDetails userDetails = userHelper.getUserDetailsByLoginId(userRequest.getLoginId());
		if (userDetails != null) {

			userDetails = userHelper.getUpdatedUserDetailsByReqObj(userRequest, userDetails);
			userDetails = userHelper.UpdateUserDetails(userDetails);

			// send sms

			userRequest.setRespCode(Constant.SUCCESS_CODE);
			userRequest.setRespMesg(Constant.UPDATED_SUCCESS);
			return userRequest;
		} else {
			userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			userRequest.setRespMesg("User Not Found");
			return userRequest;
		}
	}

	public UserRequestObject changeUserStatus(Request<UserRequestObject> userRequestObject)
			throws BizException, Exception {
		UserRequestObject userRequest = userRequestObject.getPayload();
		userHelper.validateUserRequest(userRequest);

		UserDetails userDetails = userHelper.getUserDetailsByLoginId(userRequest.getLoginId());
		if (userDetails != null) {

			if (userDetails.getStatus().equalsIgnoreCase("INACTIVE")) {
				userDetails.setStatus("ACTIVE");
			} else {
				userDetails.setStatus("INACTIVE");
			}
			userDetails = userHelper.UpdateUserDetails(userDetails);

			userRequest.setStatus(userDetails.getStatus());
			userRequest.setRespCode(Constant.SUCCESS_CODE);
			userRequest.setRespMesg(Constant.UPDATED_SUCCESS);
			return userRequest;
		} else {
			userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			userRequest.setRespMesg("User Not Found");
			return userRequest;
		}
	}

	public List<UserDetails> getUserDetails(Request<UserRequestObject> userRequestObject) {
		UserRequestObject userRequest = userRequestObject.getPayload();
		List<UserDetails> userList = userHelper.getUserDetails(userRequest);
		return userList;
	}

}
