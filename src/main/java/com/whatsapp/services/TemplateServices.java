package com.whatsapp.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.constant.Constant;
import com.common.entities.WhatsAppDetails;
import com.common.exceptions.BizException;
import com.common.helper.WhatsAppHelper;
import com.common.object.request.Request;
import com.whatsapp.helper.SendTextMessageHelper;
import com.whatsapp.helper.TemplateMapperHelper;
import com.whatsapp.object.request.TemplateRequestObject;
import com.whatsapp.response.WhatsAppMessageResponse;

@Service
public class TemplateServices {

	@Autowired
	private TemplateMapperHelper templateMapperHelper;
	
	@Autowired
	private SendTextMessageHelper sendTextMessageHelper;
	
	@Autowired WhatsAppHelper whatsAppHelper;
	
	

	public List<TemplateRequestObject> getWhatsAppTemplate(Request<TemplateRequestObject> templateRequestObject)
			throws IOException, BizException {
		TemplateRequestObject templateRequest = templateRequestObject.getPayload();
		templateMapperHelper.validateTemplateRequest(templateRequest);

		List<TemplateRequestObject> templateList = new ArrayList<>();
		templateList = templateMapperHelper.getTemplates(templateRequest);
		return templateList;
	}

//	public List<TemplateRequestObject> deleteWhatsAppTemplateByName(Request<TemplateRequestObject> templateRequestObject) 
//			
//
//		
//		return null;
//	}

	public TemplateRequestObject deleteWhatsAppTemplateByName(Request<TemplateRequestObject> templateRequestObject) throws Exception {
		TemplateRequestObject templateRequest = templateRequestObject.getPayload();
		templateMapperHelper.validateTemplateRequest(templateRequest);
		
		WhatsAppDetails whatsAppDetails = whatsAppHelper.getWhatsAppBySuperadminId(Constant.GLOBAL_SUPERADMIN_ID);
		
		String apiResp = templateMapperHelper.deleteTemplate(templateRequest.getTemplateName(), whatsAppDetails);
		
		
		// ✅ Parse JSON and print success
	    if (apiResp != null) {
	        JSONObject json = new JSONObject(apiResp);

	        if (json.has("success")) {
	            boolean success = json.getBoolean("success");
	            
	        	templateRequest.setRespCode(Constant.SUCCESS_CODE);
	        	templateRequest.setRespMesg("Successfully Deleted");
	        	return templateRequest;
	        }else {
	        	templateRequest.setRespCode(Constant.BAD_REQUEST_CODE);
	        	templateRequest.setRespMesg("Can not Delete");
	        	return templateRequest;
	        }   }
		return templateRequest;
	}

}
