package com.whatsapp.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.constant.Constant;
import com.common.entities.WhatsAppMessage;
import com.common.exceptions.BizException;
import com.ngo.object.request.WhatsAppRequestObject;
import com.whatsapp.dao.WhatsAppMessageDao;
import com.whatsapp.object.request.WhatsAppMessageRequestObject;

@Component
public class WhatsAppMessageHelper {
	
	@Autowired
	private WhatsAppMessageDao whatsAppMessageDao;


	public void validateWhatsAppMessageRequest(WhatsAppMessageRequestObject whatsAppMessageRequestObject) throws BizException {
		if (whatsAppMessageRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	@SuppressWarnings("unchecked")
	public List<WhatsAppMessage> getWhatsAppMessage(WhatsAppMessageRequestObject whatsAppMessageRequest) {
		List<WhatsAppMessage> results = new ArrayList<>();
		results = whatsAppMessageDao.getEntityManager().createQuery("SELECT WA FROM WhatsAppMessage WA ")
				.getResultList();
		return results;
	}



	
}
