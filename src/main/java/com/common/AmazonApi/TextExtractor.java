package com.common.AmazonApi;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.*;
import java.util.*;


@Component
public class TextExtractor {
	
	private String key= "";
	private String value= "";

	public Map<String, Object> extractTextFromBase64(String base64Image) {
		Map<String, Object> result = new HashMap<>();

		try {
			// 1️⃣ Convert Base64 to bytes
			byte[] imageBytes = Base64.getDecoder().decode(base64Image);
			SdkBytes sdkBytes = SdkBytes.fromByteArray(imageBytes);

			// 2️⃣ Create Document
			Document document = Document.builder().bytes(sdkBytes).build();

			// 3️⃣ AWS Credentials
			AwsBasicCredentials awsCreds = AwsBasicCredentials.create(key, value);

			// 4️⃣ Create Textract Client
			try (TextractClient textractClient = TextractClient.builder().region(Region.AP_SOUTH_1)
					.credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build()) {

				// 5️⃣ Analyze Document
				AnalyzeDocumentRequest request = AnalyzeDocumentRequest.builder().featureTypes(FeatureType.FORMS)
						.document(document).build();

				AnalyzeDocumentResponse response = textractClient.analyzeDocument(request);

				// 6️⃣ Extract detected text lines
				List<String> lines = new ArrayList<>();
				for (Block block : response.blocks()) {
					if (block.blockType().equals(BlockType.LINE)) {
						lines.add(block.text());
					}
				}

				result.put("status", "success");
				result.put("payload", lines);
			}
			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("message", e.getMessage());
		}

		return result;
	}
}
