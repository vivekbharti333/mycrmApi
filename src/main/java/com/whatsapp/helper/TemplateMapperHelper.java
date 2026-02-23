package com.whatsapp.helper;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.common.object.request.UserRequestObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsapp.object.request.TemplateRequestObject;
import com.whatsapp.object.request.TemplateVaribaleRequest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class TemplateMapperHelper {
	
	public void validateTemplateRequest(TemplateRequestObject templateRequestObject) throws BizException {
		if (templateRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}


	private static final String LIST_TEMPLATE_URL = "https://graph.facebook.com/v24.0/1580519719664370/message_templates";

	private static final String SINGLE_TEMPLATE_BASE_URL = "https://graph.facebook.com/v24.0/";

	private static final String ACCESS_TOKEN = "EAARXhxNUBPgBQrD3LOphV0ks82HE4qmgZCGOxZB8BYhmZBYwt4nWfySm1T8DAhOBCwy3Q4GF6Dd42X4ZAOQl4LKHBBLxqXZBFqseI5Raz8V27SanrL8NhWZBLXpRminbUq2TfMN8YOIQxVlonw1ZBr9ajqahAiRqbzynunamkuahEWTnZA0EaM4sQHbhlujDWkPEeAZDZD";

	/**
	 * 🔑 MAIN METHOD Uses requestFor from TemplateRequestObject
	 */
	public List<TemplateRequestObject> getTemplates(TemplateRequestObject request) throws IOException {

		System.out.println("Enter 1");

		String jsonResponse = fetchTemplatesFromWhatsApp(request);
		System.out.println("Enter 2");

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(jsonResponse);

		// SAME mapper for BOTH ALL & BYID
		return mapDataNode(rootNode);
	}

	/**
	 * Calls WhatsApp API based on requestFor
	 */
	private String fetchTemplatesFromWhatsApp(TemplateRequestObject request) throws IOException {

		if (request.getRequestFor() == null) {
			throw new IllegalArgumentException("requestFor is required");
		}

		String url;

		if ("ALL".equalsIgnoreCase(request.getRequestFor())) {

			url = LIST_TEMPLATE_URL;

		} else if ("BY_ID".equalsIgnoreCase(request.getRequestFor())) {

			if (request.getTemplateId() == null || request.getTemplateId().isBlank()) {
				throw new IllegalArgumentException("templateId is required when requestFor = BYID");
			}

			url = SINGLE_TEMPLATE_BASE_URL + request.getTemplateId();

		} else if ("BY_NAME".equalsIgnoreCase(request.getRequestFor())) {

			if (request.getTemplateName() == null || request.getTemplateName().isBlank()) {
				throw new IllegalArgumentException("template name is required when requestFor = BYNAME");
			}

			url = SINGLE_TEMPLATE_BASE_URL+ "1580519719664370/message_templates?name=" +request.getTemplateName();
			
			System.out.println(url);

		} else {
			throw new IllegalArgumentException("Invalid requestFor value: " + request.getRequestFor());
		}

		OkHttpClient client = new OkHttpClient();

		Request httpRequest = new Request.Builder().url(url).get().addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
				.build();

		try (Response response = client.newCall(httpRequest).execute()) {

			// 🔥 READ BODY ONCE FOR BOTH SUCCESS & ERROR
			String responseBody = response.body() != null ? response.body().string() : "";

			if (!response.isSuccessful()) {
				throw new IOException("WhatsApp API error: HTTP " + response.code() + " | Response: " + responseBody);
			}

			return responseBody;
		}
	}

	/**
	 * 🔁 UNIVERSAL MAPPER Handles: - data[] (ALL) - single object (BYID)
	 */
	private List<TemplateRequestObject> mapDataNode(JsonNode rootNode) {

		List<TemplateRequestObject> result = new ArrayList<>();

		if (rootNode == null || rootNode.isMissingNode()) {
			return result;
		}

		List<JsonNode> templateNodes = new ArrayList<>();

		if (rootNode.has("data") && rootNode.get("data").isArray()) {
			rootNode.get("data").forEach(templateNodes::add);
		} else if (rootNode.isObject()) {
			templateNodes.add(rootNode);
		}

		for (JsonNode templateNode : templateNodes) {

			TemplateRequestObject obj = new TemplateRequestObject();

			obj.setTemplateId(templateNode.path("id").asText());
			obj.setTemplateName(templateNode.path("name").asText());
			obj.setParameterFormat(templateNode.path("parameter_format").asText());
			obj.setLanguage(templateNode.path("language").asText());
			obj.setStatus(templateNode.path("status").asText());
			obj.setCategory(templateNode.path("category").asText());
			obj.setSub_category(templateNode.path("sub_category").asText());

			List<TemplateVaribaleRequest> variables = new ArrayList<>();

			for (JsonNode component : templateNode.path("components")) {

				String type = component.path("type").asText();

				switch (type) {

				case "HEADER":
					obj.setHeaderFormat(component.path("format").asText());
					obj.setHeaderText(component.path("text").asText());
					break;

				case "BODY":
					obj.setMsgBodyText(component.path("text").asText());

					JsonNode bodyText = component.path("example").path("body_text");

					if (bodyText.isArray() && bodyText.size() > 0) {
						for (JsonNode val : bodyText.get(0)) {
							TemplateVaribaleRequest v = new TemplateVaribaleRequest();
							v.setBodyVariable(val.asText());
							variables.add(v);
						}
					}
					break;

				case "FOOTER":
					obj.setFooterText(component.path("text").asText());
					break;
				}
			}

			obj.setMsgBodyVariable(variables);
			result.add(obj);
		}

		return result;
	}
}