package com.spring.object.response;

import java.util.List;
import java.util.Map;

import com.spring.constant.Constant;
import com.spring.enums.Status;

public class GenricResponse <T> {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Response<T> createSuccessResponse( T responseObject ,Integer responseCode)
	{
        Response<T> response = new Response();
        if(responseObject != null) {
            response.setPayload(responseObject);
        }
        
        if(responseCode != null){
            response.setResponseCode(responseCode);
        }
        
        response.setResponseMessage(Status.SUCCESS.name());
        return  response;
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Response<T> createListResponse( List<T> responseObject ,Integer responseCode, String totalNum)
	{
        Response<T> response = new Response();
        if(!responseObject.isEmpty()) {
            response.setListPayload(responseObject);
            response.setResponseCode(responseCode);
            response.setTotalNumber(totalNum);
            response.setResponseMessage("Fetch Successfully");
        }else {
        	response.setResponseCode(Constant.NO_CONTENT_CODE);
        	response.setResponseMessage(Constant.DATA_NOT_FOUND);
        }
        
        return  response;
    }
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Response<T> createMapResponse(Map reponseList, Integer responseCode) {
		Response<T> response = new Response();
        if(!reponseList.isEmpty()) {
            response.setMapPayload(reponseList);
            response.setResponseCode(responseCode);
            response.setResponseMessage("Fetch Successfully");
        }else {
        	response.setResponseCode(Constant.NO_CONTENT_CODE);
        	response.setResponseMessage(Constant.DATA_NOT_FOUND);
        }
        return response;
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public  Response<T> createErrorResponse(Integer errorCode,String errorMessage) 
    {
        Response response = new Response();
        response.setResponseMessage(errorMessage);
        response.setResponseCode(errorCode);
        return response;
    }


}
