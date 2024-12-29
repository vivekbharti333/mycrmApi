package com.spring.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.spring.constant.Constant;
import com.spring.entities.AddressDetails;
import com.spring.entities.CurrencyMaster;
import com.spring.entities.UserDetails;
import com.spring.enums.RoleType;
import com.spring.enums.Status;
import com.spring.exceptions.BizException;
import com.spring.helper.AddressHelper;
import com.spring.helper.CurrencyHelper;
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
	private AddressHelper addressHelper;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private CurrencyHelper currencyHelper;
	
	
	public UserRequestObject updateUserSubscription(Request<UserRequestObject> userRequestObject) throws BizException, Exception {
		UserRequestObject userRequest = userRequestObject.getPayload();
		userHelper.validateUserRequest(userRequest);
		
		UserDetails userDetails = userHelper.getUserDetailsByLoginIdAndStatus(userRequest.getLoginId());
//		System.out.println(userDetails.getValidityExpireOn());
//		System.out.println(userRequest.getLoginId());
		if (userDetails != null) {
			
			 // Current date
			
			Date validate = userDetails.getValidityExpireOn();
			Date currentDate = new Date();

			if (currentDate.before(validate)) {
			    // currentDate is before validate date
				currentDate = userDetails.getValidityExpireOn();
			} 
	        
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(currentDate);
	        
	        // Add 365 days to the current date
	        calendar.add(Calendar.DAY_OF_YEAR, 364);
	        
	        // Get the updated date
	        Date dateAfter364Days = calendar.getTime();
	        
//	        System.out.println("Current Date: " + currentDate);
//	        System.out.println("Date After 365 Days: " + dateAfter364Days);
	        
	        userRequest.setValidityExpireOn(dateAfter364Days);
	        
	        int isUpdate = userHelper.updateAllUserBySuperadminId(userRequest);
	        
	        if(isUpdate >0) {
	        	userRequest.setRespCode(Constant.SUCCESS_CODE);
				userRequest.setRespMesg(Constant.RENEW_SUCCESSFULLY);
				return userRequest;
	        } else {
	        	userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
				userRequest.setRespMesg(Constant.DATA_NOT_FOUND);
				return userRequest;
	        }
	        
			
		}
		return userRequest;
		
	}

	public UserRequestObject doLogin(Request<UserRequestObject> userRequestObject) throws BizException, Exception {
		UserRequestObject userRequest = userRequestObject.getPayload();
		userHelper.validateUserRequest(userRequest);
		
		UserDetails userDetails = userHelper.getUserDetailsByLoginIdAndStatus(userRequest.getLoginId());
		if (userDetails != null) {
			if(userDetails.getStatus().equalsIgnoreCase(Status.INACTIVE.name())) {
				userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
				userRequest.setRespMesg(Constant.INACTIVE_USER);
				return userRequest;
			}

			boolean isValid = userHelper.checkValidityOfUser(userDetails.getValidityExpireOn());

			if (isValid) {

				if (BCrypt.checkpw(userRequest.getPassword(), userDetails.getPassword())) {
					logger.info("Login Successfully: " + userRequest);

					// generate secret key.
					String secretKey = jwtTokenUtil.generateSecureSecretKey();

					// update secret key in UserDetails.
					userDetails.setSecretKey(secretKey);
					userHelper.UpdateUserDetails(userDetails);

					String token = jwtTokenUtil.generateAccessToken(userDetails);

					userRequest.setLoginId(userDetails.getLoginId());
					userRequest.setPassword(null);

					userRequest.setFirstName(userDetails.getFirstName());
					userRequest.setLastName(userDetails.getLastName());
					userRequest.setService(userDetails.getService());
//					userRequest.setUserPicture(userDetails.getUserPicture());
					userRequest.setPermissions(userDetails.getPermissions());
					userRequest.setRoleType(userDetails.getRoleType());
					userRequest.setSuperadminId(userDetails.getSuperadminId());
					userRequest.setIsPassChanged(userDetails.getIsPassChanged());
					userRequest.setTeamLeaderId(userDetails.getCreatedBy());
					userRequest.setToken(token);
					userRequest.setValidityExpireOn(userDetails.getValidityExpireOn());
					
					userRequest.setRespCode(Constant.SUCCESS_CODE);
					userRequest.setRespMesg(Constant.LOGIN_SUCCESS);
					return userRequest;
				} else {
					userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
					userRequest.setRespMesg(Constant.INVALID_LOGIN);
					return userRequest;
				}
			} else {
				userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
				userRequest.setRespMesg(Constant.EXPIRED_LOGIN);
				return userRequest;
			}
		} else {
			userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			userRequest.setRespMesg(Constant.INVALID_LOGIN);
			return userRequest;
		}
	}
	
	public UserRequestObject getUserDetailsByLoginId(Request<UserRequestObject> userRequestObject) throws BizException {
		UserRequestObject userRequest = userRequestObject.getPayload();
		userHelper.validateUserRequest(userRequest);

		UserDetails userDetails = userHelper.getUserDetailsByLoginId(userRequest.getLoginId());
		if (userDetails != null) {
			
			UserDetails teamLeader= userHelper.getUserDetailsByLoginId(userDetails.getCreatedBy());
			
			userRequest.setFirstName(userDetails.getFirstName());
			userRequest.setLastName(userDetails.getLastName());
			userRequest.setUserPicture(userDetails.getUserPicture());
			userRequest.setEmailId(userDetails.getEmailId());
			userRequest.setMobileNo(userDetails.getMobileNo());
			userRequest.setRoleType(userDetails.getRoleType());
			userRequest.setTeamLeaderId(teamLeader.getFirstName()+" "+teamLeader.getLastName());
//			System.out.println("(userDetails.getMobileNo() : "+(userDetails.getMobileNo()));
//			System.out.println("(userRequest.setMobileNo : "+(userRequest.getMobileNo()));
			
			userRequest.setRespCode(Constant.SUCCESS_CODE);
			return userRequest;
		}
		return userRequest;
	}
	

	@Transactional
	public UserRequestObject userRegistration(Request<UserRequestObject> userRequestObject)
			throws BizException, Exception {
		UserRequestObject userRequest = userRequestObject.getPayload();
		userHelper.validateUserRequest(userRequest);

//		String password = userHelper.generateRandomChars("ABCD145pqrs678abcdef90EF9GHxyzIJKL5MNOPQRghijS1234560TUVWXYlmnoZ1234567tuvw890", 10);
//		userRequest.setPassword("test@123");
			

		Boolean isValid = jwtTokenUtil.validateJwtToken(userRequest.getCreatedBy(), userRequest.getToken());
//		if (isValid) {

			UserDetails existsUserDetails = userHelper.getUserDetailsByLoginIdAndSuperadminId(userRequest.getMobileNo(), userRequest.getSuperadminId());
			if (existsUserDetails == null) {
				userRequest.setPassword("12345");

				String password = BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt());
				userRequest.setPassword(password);
				
				if(userRequest.getFirstName() == null || userRequest.getFirstName().equalsIgnoreCase("")) {
					userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
					userRequest.setRespMesg("Enter First Name");
					return userRequest;
				}
				
				if(userRequest.getLastName() == null || userRequest.getLastName().equalsIgnoreCase("")) {
					userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
					userRequest.setRespMesg("Enter Last Name");
					return userRequest;
				}

				UserDetails userDetails = userHelper.getUserDetailsByReqObj(userRequest);
				userDetails = userHelper.saveUserDetails(userDetails);
				
				// Save Address
				if(userRequest.getRequestedFor().equalsIgnoreCase("WEB")) {
					for (AddressRequestObject addressRequest : userRequest.getAddressList()) {
						addressRequest.setUserType(userRequest.getRoleType());
						addressService.saveAddressDetailsByRequest(addressRequest, userDetails.getId(), userRequest.getSuperadminId());
					}
				}else {
					AddressRequestObject addressRequestObj = addressHelper.setAddressRequestObjectByUserReqObj(userRequest);
					
					AddressDetails addressDetails = addressHelper.getAddressDetailsByReqObj(addressRequestObj, userDetails.getId(), userDetails.getSuperadminId());
					addressHelper.saveAddressDetails(addressDetails);
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
//		} else {
//			userRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
//			userRequest.setRespMesg(Constant.INVALID_TOKEN);
//			return userRequest;
//		}
	}

	
	@Transactional
	public UserRequestObject updateUserDetails(Request<UserRequestObject> userRequestObject)
			throws BizException, Exception {
		UserRequestObject userRequest = userRequestObject.getPayload();
		userHelper.validateUserRequest(userRequest);
		
//		Boolean isValid = jwtTokenUtil.validateJwtToken(userRequest.getCreatedBy(), userRequest.getToken());
//		if (isValid) {
			
		UserDetails userDetails = userHelper.getUserDetailsByLoginId(userRequest.getLoginId());
		if (userDetails != null) {
			System.out.println("Enter 1");
			userDetails = userHelper.getUpdatedUserDetailsByReqObj(userDetails, userRequest);
			userDetails = userHelper.UpdateUserDetails(userDetails);
			
			System.out.println("Enter 2");
			
			if(userRequest.getRequestedFor().equalsIgnoreCase("WEB")) {
				for (AddressRequestObject addressRequest : userRequest.getAddressList()) {
					AddressDetails addressDetails = addressHelper.getAddressDetailsByUserIdAndAddressType(userDetails.getId(), addressRequest.getAddressType(), userDetails.getSuperadminId());
					
					if(addressDetails != null) {
						addressService.updateAddressDetailsByRequest(addressRequest,addressDetails);
					}
				}
			}else {
				AddressRequestObject addressRequestObj = addressHelper.setAddressRequestObjectByUserReqObj(userRequest);
				AddressDetails addressDetails = addressHelper.getAddressDetailsByUserIdAndAddressType(userDetails.getId(), addressRequestObj.getAddressType(), userDetails.getSuperadminId());
				
				if(addressDetails != null) {
					addressService.updateAddressDetailsByRequest(addressRequestObj,addressDetails);
				}
			}
			

			userRequest.setRespCode(Constant.SUCCESS_CODE);
			userRequest.setRespMesg(Constant.UPDATED_SUCCESS);
			return userRequest;
		} else {
			userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			userRequest.setRespMesg(Constant.USER_NOT_EXIST);
			return userRequest;
		}
//		}else {
//			userRequest.setRespCode(Constant.INVALID_TOKEN_CODE);
//			userRequest.setRespMesg(Constant.INVALID_TOKEN);
//			return userRequest;
//		}
	}

	public UserRequestObject changeUserPassword(Request<UserRequestObject> userRequestObject)throws BizException, Exception {
		UserRequestObject userRequest = userRequestObject.getPayload();
		userHelper.validateUserRequest(userRequest);
		
		System.out.println(userRequest.getLoginId()+" , "+userRequest.getPassword());

		UserDetails userDetails = userHelper.getUserDetailsByLoginId(userRequest.getLoginId());
		if (userDetails != null) {
			String password = BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt());
			userDetails.setPassword(password);
			userDetails.setIsPassChanged("YES");
			userDetails = userHelper.UpdateUserDetails(userDetails);
			
			userRequest.setStatus(userDetails.getStatus());
			userRequest.setRespCode(Constant.SUCCESS_CODE);
			userRequest.setRespMesg(Constant.UPDATED_SUCCESS);
			return userRequest;
		}else {
			userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			userRequest.setRespMesg(Constant.USER_NOT_EXIST);
			return userRequest;
		}
	}

	
	public UserRequestObject changeUserStatus(Request<UserRequestObject> userRequestObject)
			throws BizException, Exception {
		UserRequestObject userRequest = userRequestObject.getPayload();
		userHelper.validateUserRequest(userRequest);

		UserDetails userDetails = userHelper.getUserDetailsByLoginId(userRequest.getLoginId());
		if (userDetails != null) {

			if (userDetails.getStatus().equalsIgnoreCase(Status.INACTIVE.name())) {
				userDetails.setStatus(Status.ACTIVE.name());
			} else {
				userDetails.setStatus(Status.INACTIVE.name());
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
	
	public UserRequestObject removeUserParmanent(Request<UserRequestObject> userRequestObject) 
			throws BizException, Exception {
		UserRequestObject userRequest = userRequestObject.getPayload();
		userHelper.validateUserRequest(userRequest);
		UserDetails userDetails = userHelper.getUserDetailsByLoginIdAndSuperadminId(userRequest.getLoginId(), userRequest.getSuperadminId());
		if (userDetails != null) {
			
			userDetails.setStatus(Status.REMOVED.name());
			
			userDetails.setLoginId(userDetails.getLoginId()+"removed");
			userDetails.setCreatedBy(userDetails.getCreatedBy()+"removed");
			userDetails.setSuperadminId(userDetails.getSuperadminId()+"removed");
			userDetails = userHelper.UpdateUserDetails(userDetails);
			
			userRequest.setStatus(userDetails.getStatus());
			userRequest.setRespCode(Constant.SUCCESS_CODE);
			userRequest.setRespMesg(Constant.REMOVED_SUCCESS);
			return userRequest;
		} else {
			userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			userRequest.setRespMesg("User Not Found");
			return userRequest;
		}
	}
	
	public UserRequestObject changeTeamLeader(Request<UserRequestObject> userRequestObject) 
			throws BizException, Exception {
		UserRequestObject userRequest = userRequestObject.getPayload();
		userHelper.validateUserRequest(userRequest);
		
		UserDetails userDetails = userHelper.getUserDetailsByLoginId(userRequest.getLoginId());
		if (userDetails != null) {
			
			userDetails.setCreatedBy(userRequest.getTeamLeaderId());
			userHelper.UpdateUserDetails(userDetails);
			
			userRequest.setRespCode(Constant.SUCCESS_CODE);
			userRequest.setRespMesg(Constant.UPDATED_SUCCESS);
			return userRequest;
		}else {
			userRequest.setRespCode(Constant.BAD_REQUEST_CODE);
			userRequest.setRespMesg("User Not Found");
			return userRequest;
		}
	}
	
	public UserRequestObject changeUserRole(Request<UserRequestObject> userRequestObject) 
			throws BizException, Exception {
		UserRequestObject userRequest = userRequestObject.getPayload();
		userHelper.validateUserRequest(userRequest);
		
		UserDetails userDetails = userHelper.getUserDetailsByLoginId(userRequest.getLoginId());
		if (userDetails != null) {

			userDetails.setRoleType(userRequest.getRoleType());
			userDetails.setCreatedBy(userDetails.getSuperadminId());
			
			userHelper.UpdateUserDetails(userDetails);
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
	
	public List<UserDetails> getUserDetailsByUserRole(Request<UserRequestObject> userRequestObject) {
		UserRequestObject userRequest = userRequestObject.getPayload();
		List<UserDetails> userList = userHelper.getUserDetailsByUserRole(userRequest);
		return userList;
	}
	

	public List<UserDetails> getFundRisingOfficersBySuperadminId(Request<UserRequestObject> userRequestObject) {
		UserRequestObject userRequest = userRequestObject.getPayload();
		List<UserDetails> userList = userHelper.getFundRisingOfficersBySuperadminId(userRequest);
		return userList;
	}
	
	public List<UserDetails> getUserListForDropDown(Request<UserRequestObject> userRequestObject) {
		UserRequestObject userRequest = userRequestObject.getPayload();
		List<UserDetails> userList = userHelper.getUserListForDropDown(userRequest);
		return userList;
	}

	public List<AddressDetails> getAddressDetails(Request<UserRequestObject> userRequestObject) {
		UserRequestObject userRequest = userRequestObject.getPayload();
		List<AddressDetails> addressList = userHelper.getAddressDetails(userRequest);
		return addressList;
	}


	
}
