package com.ngo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.AmazonApi.TextExtractor;
import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.ngo.object.request.AttendanceRequestObject;
import com.ngo.services.AttendenceService;
import com.common.object.request.Request;
import com.common.object.response.GenricResponse;
import com.common.object.response.Response;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@CrossOrigin(origins = "*")
@RestController
public class OCRController {
	
	@Autowired
	private TextExtractor textExtractor;
	
	
//	@RequestMapping(path = "markAttendance", method = RequestMethod.POST)
//	public Response<AttendanceRequestObject>addDonation(@RequestBody Request<AttendanceRequestObject> attendanceRequestObject, HttpServletRequest request)
//	{
//		GenricResponse<AttendanceRequestObject> responseObj = new GenricResponse<AttendanceRequestObject>();
//		try {
//			AttendanceRequestObject responce =  attendenceService.markAttendance(attendanceRequestObject);
//			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
//		}catch (BizException e) {
//			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
//		} 
// 		catch (Exception e) {
// 			e.printStackTrace();
//			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
//		}
//	}
	

    @PostMapping("/extract")
    public Map<String, Object> extractFromBase64(@RequestBody Map<String, String> body) {
        String base64Image = body.get("image");
        return textExtractor.extractTextFromBase64(base64Image);
    }
    
    @GetMapping("testExtract")
    public void testExtract() throws Exception {
    	
    	System.out.println("Enter Hai ");

        String imagePath = "C:\\Users\\HP\\Documents\\test.png";

        // Step 1: Preprocess Image
        BufferedImage processedImage = preprocessImage(imagePath);

        File tempFile = new File("processed.png");
        ImageIO.write(processedImage, "png", tempFile);

        // Step 2: OCR
        String text = doOCR(tempFile);

        System.out.println("===== RAW OCR TEXT =====");
        System.out.println(text);

        // Step 3: Extract structured data
        extractData(text);

        // Cleanup
        tempFile.delete();
    }

    // ================= OCR =================
    public static String doOCR(File file) {
        ITesseract tesseract = new Tesseract();

        // Change path if needed
        tesseract.setDatapath("C:\\Users\\HP\\AppData\\Local\\Programs\\Tesseract-OCR\\tessdata\\eng.traineddata");

        // Language
        tesseract.setLanguage("eng");

        try {
            return tesseract.doOCR(file);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
    }

    // ================= IMAGE PREPROCESSING =================
    public static BufferedImage preprocessImage(String path) throws Exception {

        BufferedImage original = ImageIO.read(new File(path));

        // Resize (increase resolution)
        BufferedImage resized = new BufferedImage(
                original.getWidth() * 2,
                original.getHeight() * 2,
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(original, 0, 0, resized.getWidth(), resized.getHeight(), null);
        g.dispose();

        // Convert to Grayscale
        BufferedImage gray = new BufferedImage(
                resized.getWidth(),
                resized.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY
        );

        Graphics g2 = gray.getGraphics();
        g2.drawImage(resized, 0, 0, null);
        g2.dispose();

        // Apply simple threshold (binarization)
        BufferedImage binary = new BufferedImage(
                gray.getWidth(),
                gray.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY
        );

        for (int x = 0; x < gray.getWidth(); x++) {
            for (int y = 0; y < gray.getHeight(); y++) {
                int pixel = gray.getRGB(x, y);
                int r = (pixel >> 16) & 0xff;

                if (r > 128) {
                    binary.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    binary.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }

        return binary;
    }

    // ================= DATA EXTRACTION =================
    public static void extractData(String text) {

        // ===== Amount =====
        Pattern amountPattern = Pattern.compile("(\\d+[.,]?\\d*)");
        Matcher amountMatcher = amountPattern.matcher(text);

        String amount = null;
        while (amountMatcher.find()) {
            amount = amountMatcher.group();
        }

        // ===== Date =====
        Pattern datePattern = Pattern.compile("\\b(\\d{2}[/-]\\d{2}[/-]\\d{4})\\b");
        Matcher dateMatcher = datePattern.matcher(text);

        String date = dateMatcher.find() ? dateMatcher.group() : null;

        // ===== Name (simple logic) =====
        String name = null;
        String[] lines = text.split("\\n");
        
        System.out.println("Line : "+lines);

        for (String line : lines) {
            if (line.toLowerCase().contains("name")) {
                name = line;
                break;
            }
        }

        System.out.println("\n===== EXTRACTED DATA =====");
        System.out.println("Name   : " + name);
        System.out.println("Amount : " + amount);
        System.out.println("Date   : " + date);
    }
	
	
	
}
