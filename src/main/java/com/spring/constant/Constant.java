package com.spring.constant;

public class Constant {
	 /** Document path **/
//	 public static final String docLocation = "/opt/tomcat-8/webapps/PaymentReceipt";
	 
	 public static final String userPicPath = "/user";	
	 public static final String proofPicPath = "/proof";
	 public static final String VehicleImage = "/VehicleImage";
	 public static final String AadhharImage = "/AadhharImage";
	 public static final String receipt = "/receipt";
	 public static final String defaultPath = "/Default";
	 

	 /** Response Message **/
	 public static final String INACTIVE = "User is Inactive";
	 public static final String NOT_VERIFIED = "User Not Verified";
	 public static final String EXIST_USER = "User Already registered";
	 public static final String NOT_EXIST_USER = "User Not Exists";
	 
	 /** Response code **/
	 public static final int SUCCESS_CODE = 200;
	 public static final int NO_CONTENT_CODE = 204;
	 public static final int BAD_REQUEST_CODE = 400;
	 public static final int INVALID_TOKEN_CODE = 401;
	 public static final int ALREADY_EXISTS = 403;
	 public static final int INTERNAL_SERVER_ERR = 500;
	
	 /* Response Message */ 
	 public static final String INVALID_REQUEST = "Invalid Request";
	 public static final String INVALID_LOGIN = "Invalid Login Details";
	 public static final String INVALID_TOKEN = "Invalid Token";
	 public static final String EXPIRED_LOGIN = "Validity Expired";
	 
	 public static final String LOGIN_SUCCESS = "Login Successfully";
	 public static final String LOGOUT_SUCCESS = "Logout Successfully";
	 
	 public static final String USER_EXIST = "Login Id Already Exists";
	 public static final String USER_NOT_EXIST = "User Not Exists";
	 
	 public static final String INVOICE_GEN_SUCCESS = "Invoice Generated Successfully";
	 public static final String INVOICE_NUM_EXISTS = "Invoice no already exists";
	 
	 public static final String REGISTERED_SUCCESS = "Registered Successfully";
	 public static final String REGISTERED_FAILED = "Already Registered";
	 public static final String UPDATED_SUCCESS = "Updated Successfully";
	 public static final String DATA_NOT_FOUND ="Data Not Available";
	 
}



