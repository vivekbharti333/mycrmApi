package com.whatsapp.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsapp.object.request.TemplateRequestObject;
import com.whatsapp.response.SendWaMessageResponse;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class SendTextMessageHelper {

	private static final String ACCESS_TOKEN = "EAARXhxNUBPgBQrD3LOphV0ks82HE4qmgZCGOxZB8BYhmZBYwt4nWfySm1T8DAhOBCwy3Q4GF6Dd42X4ZAOQl4LKHBBLxqXZBFqseI5Raz8V27SanrL8NhWZBLXpRminbUq2TfMN8YOIQxVlonw1ZBr9ajqahAiRqbzynunamkuahEWTnZA0EaM4sQHbhlujDWkPEeAZDZD";

	public void validateTemplateRequest(TemplateRequestObject templateRequestObject) throws BizException {
		if (templateRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	private static final OkHttpClient client = new OkHttpClient();

	public String getTextTemplateParameter(TemplateRequestObject templateRequest) throws Exception {

		Map<String, Object> root = new HashMap<>();
		root.put("messaging_product", "whatsapp");
		root.put("to", templateRequest.getToWhatsAppNumber());
		root.put("type", "template");

		// ---- Template ----
		Map<String, Object> template = new HashMap<>();
		template.put("name", templateRequest.getTemplateName());

		Map<String, String> language = new HashMap<>();
		language.put("code", templateRequest.getLanguage());
		template.put("language", language);

		// ---- Body Parameters ----
		List<Map<String, Object>> parameters = new ArrayList<>();

		if (templateRequest.getMsgBodyVariable() != null) {
			templateRequest.getMsgBodyVariable().forEach(var -> {
				Map<String, Object> param = new HashMap<>();
				param.put("type", "text");
				param.put("text", var.getBodyVariable());
				parameters.add(param);
			});
		}

		Map<String, Object> bodyComponent = new HashMap<>();
		bodyComponent.put("type", "body");
		bodyComponent.put("parameters", parameters);

		template.put("components", List.of(bodyComponent));
		root.put("template", template);

		return new ObjectMapper().writeValueAsString(root);
	}

	public SendWaMessageResponse callSendTemplateTextMessage(String jsonPayload) throws Exception {

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(jsonPayload, mediaType);

		Request request = new Request.Builder().url("https://graph.facebook.com/v24.0/902659692940310/messages")
				.post(body).addHeader("Content-Type", "application/json")
				.addHeader("Authorization", "Bearer " + ACCESS_TOKEN).build();

		try (okhttp3.Response response = client.newCall(request).execute()) {

			String responseBody = response.body() != null ? response.body().string() : "";

			ObjectMapper mapper = new ObjectMapper();
			SendWaMessageResponse res = new SendWaMessageResponse();

			// ❌ ERROR CASE
			if (!response.isSuccessful()) {

				JsonNode root = mapper.readTree(responseBody);
				JsonNode errorNode = root.path("error");

				String errorMessage = errorNode.path("message").asText();
				String errorType = errorNode.path("type").asText();
				int errorCode = errorNode.path("code").asInt();
				int errorSubCode = errorNode.path("error_subcode").asInt();

				// 🔍 LOG ERROR
				System.err.println("WhatsApp API Error");
				System.err.println("HTTP Status     : " + response.code());
				System.err.println("Message         : " + errorMessage);
				System.err.println("Type            : " + errorType);
				System.err.println("Code            : " + errorCode);
				System.err.println("Error Sub Code  : " + errorSubCode);

				// ✅ SET ERROR INTO RESPONSE OBJECT
				res.setErrorMessage(errorMessage);
				res.setErrorType(errorType);
				res.setErrorCode(errorCode);
				res.setErrorSubcode(errorSubCode);

				return res; // 👈 RETURN ERROR RESPONSE
			}

			// ✅ SUCCESS CASE
			JsonNode root = mapper.readTree(responseBody);

			res.setMessagingProduct(root.path("messaging_product").asText());

			// contacts[0]
			JsonNode contact = root.path("contacts").get(0);
			if (contact != null) {
				res.setSendTo(contact.path("input").asText());
				res.setWaId(contact.path("wa_id").asText());
			}

			// messages[0]
			JsonNode message = root.path("messages").get(0);
			if (message != null) {
				res.setMessagesId(message.path("id").asText());
				res.setMessageStatus(message.path("message_status").asText());
			}

			return res;
		}
	}

}
