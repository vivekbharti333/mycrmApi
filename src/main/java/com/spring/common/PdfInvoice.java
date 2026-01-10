package com.spring.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.constant.Constant;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.ngo.entities.DonationDetails;
import com.ngo.entities.InvoiceHeaderDetails;
import com.ngo.helper.DonationHelper;


@Component
public class PdfInvoice {
	
	@Autowired
	private DonationHelper donationHelper;
	
	@Autowired
	private FilePath filePath;
	
//	src="data:image/jpeg;base64,[the value of your base64DataString]"
	
	public String htmlInvoice(DonationDetails donationDetails, InvoiceHeaderDetails invoiceHeaderDetails) {
		
		if(donationDetails.getDonorName()== null || donationDetails.getDonorName().equalsIgnoreCase("")) {
			donationDetails.setDonorName("XXXX");
		}if(donationDetails.getAddress() == null || donationDetails.getAddress().equalsIgnoreCase("")) {
			donationDetails.setAddress("XXXX");
		}if(donationDetails.getEmailId() == null || donationDetails.getEmailId().equalsIgnoreCase("")) {
			donationDetails.setEmailId("XXXX");
		}if(donationDetails.getMobileNumber() == null || donationDetails.getMobileNumber().equalsIgnoreCase("")) {
			donationDetails.setMobileNumber("XXXX");
		}if(donationDetails.getPanNumber() == null || donationDetails.getPanNumber().equalsIgnoreCase("")) {
			donationDetails.setPanNumber("XXXX");
		}
		
		String basePath = filePath.getPathToUploadFile(Constant.invoiceImage);

		String pattern = "dd/MM/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(donationDetails.getCreatedAt());
		
		double amount = donationDetails.getAmount();
		long finalValue = Math.round(amount); 
			
		String word = convertNumber(finalValue);
		
		String regAdd = "<h6 style=\"margin: 2px; font-size: 14px;\">Reg. Add:"+invoiceHeaderDetails.getRegAddress()+"</h6>";
		if(invoiceHeaderDetails.getRegAddress().equals("N/A") || invoiceHeaderDetails.getRegAddress().equals("") || invoiceHeaderDetails.getRegAddress().isEmpty()) {
			regAdd = "";
		} 
		
		String panNo = ",&nbsp; PAN NO.: "+invoiceHeaderDetails.getPanNumber();
		
		if(invoiceHeaderDetails.getPanNumber().equals("N/A") || invoiceHeaderDetails.getPanNumber().equals("") || invoiceHeaderDetails.getPanNumber().isEmpty()) {
			panNo = "";
		} 
		
		String mobileNo = "Mobile No.</strong> "+invoiceHeaderDetails.getMobileNo()+",";
		if(invoiceHeaderDetails.getMobileNo().equals("N/A") || invoiceHeaderDetails.getMobileNo().equals("") || invoiceHeaderDetails.getMobileNo().isEmpty()) {
			mobileNo = "";
		}
		
		String donorPan = "Pan No: <strong> "+ donationDetails.getPanNumber() + "</strong>&nbsp;";
		if(donationDetails.getPanNumber().equalsIgnoreCase("N/A") || donationDetails.getPanNumber().equalsIgnoreCase("") || donationDetails.getPanNumber().isEmpty()) {
			
		}
		
		
		String HTML = "<div class=\"body-Container\" style=\" width: 100%; border: 1px solid black;\">\n"
			    + "<table width=\"100%\">\n" + "<tr>\n" + "<td width=\"16%\" height=\"140px\">\n"
			    + "<img src="+basePath+File.separator+invoiceHeaderDetails.getCompanyLogo()+" alt=\"Image\" width=\"100%\" height=\"100%\">\n"
//				+ "<img src=\"data:image/jpeg;base64," + invoiceHeaderDetails.getCompanyLogo() + "\" alt=\"Image\" width=\"100%\" height=\"100%\">\n"
			    + "</td>\n" + "<td width=\"60%\">\n" + "<center>\n"
			    + "<h1 style=\"font-size: 40px; margin: 0;\"><strong style=\"color: "+invoiceHeaderDetails.getCompanyFirstNameColor()+";\">"+invoiceHeaderDetails.getCompanyFirstName()+"</strong> <strong style=\"color: "+invoiceHeaderDetails.getCompanyLastNameColor()+";\">"+invoiceHeaderDetails.getCompanyLastName()+"</strong></h1>\n"
//			    + "<h5 style=\"margin: 2px;font-size: 14px;\">Registration No.: "+invoiceHeaderDetails.getGstNumber()+",&nbsp; PAN NO.: "+invoiceHeaderDetails.getPanNumber()+"</h5>\n"
			    + "<h5 style=\"margin: 2px;font-size: 14px;\">Registration No.: "+invoiceHeaderDetails.getGstNumber()+ panNo +"</h5>\n"
			    + "<h6 style=\"margin: 2px; font-size: 14px;\">Off. Add:"+invoiceHeaderDetails.getOfficeAddress()+"</h6>\n"
//			    + "<h6 style=\"margin: 2px; font-size: 14px;\">Reg. Add:"+invoiceHeaderDetails.getRegAddress()+"</h6>"
			    + regAdd 
			    + "</td></tr></table></center>"
			    + "<center><p><strong style=\"font-size: 18px;\">Donation Receipt</strong> </p>\n</center>\n"
			    + "<div class=\"form-container\" style=\"width: 100%; height: auto; background-color: "+invoiceHeaderDetails.getBackgroundColor()+";\">\n"
			    + "<hr>\n" + "<p style=\"padding: 10px;font-size: 22px \">Receipt No: <strong>"
			    + donationDetails.getInvoiceNumber()
			    + "</strong> <span style=\"text-align: right; float: right;margin-right: 10px;\">Date:" + date
			    + "</span> </p>\n"
			    + "<p style=\"font-size: 19px;margin-right: 40px; margin-left: 40px;line-height: 30px; text-align: justify;\"><strong>"+invoiceHeaderDetails.getCompanyFirstName()+" "+invoiceHeaderDetails.getCompanyLastName()+"</strong> is thankful to <strong>"
			    + donationDetails.getDonorName() + "</strong> &nbsp;"
			    + "Address: <strong>" + donationDetails.getAddress() + "</strong>&nbsp;"
			    + "Email: <strong>"+ donationDetails.getEmailId() + "</strong>&nbsp;"
			    + "Contact No: <strong>" + donationDetails.getMobileNumber() + "</strong>&nbsp;"
//			    + "Pan No: <strong> "+ donationDetails.getPanNumber() + "</strong>&nbsp;"
			    + donorPan
			    		+ "<strong>for kind</strong> donation of <strong>Rs: " + donationDetails.getAmount()
			    + "/-</strong>. ("+ word + " Only in "+donationDetails.getCurrencyCode()+") for "+donationDetails.getProgramName()+".\n" + "\n" + "\n" + "<br>\n"
			    + "<img src=\""+basePath+File.separator+invoiceHeaderDetails.getCompanyStamp()+"\" style=\"width: 115px;margin-left: 100px; height: 115px;\">\n"
			    + "<p style=\"font-size: 20px;letter-spacing: 0.6px;margin-top: -30px; margin-left:110px;\">Authorised Sign.</p>\n"
			    + "\n" + "\n";

			if (!"N/A".equals(invoiceHeaderDetails.getThankYouNote())) {
			    HTML += "<center><h1 style=\"border-top: 2px solid black;\">Thank You Letter</h1> </center>\n"
			        + invoiceHeaderDetails.getThankYouNote() + "\n";
			}

			HTML += "<hr>\n" + "</div>\n" + "<div style=\"\">\n"
			    + "<p style=\"font-weight: bold;margin-right: 20px;line-height: 1.6; margin-left: 20px; text-align: justify;\">"+invoiceHeaderDetails.getFooter()+"</p>\n"
			    + "</div>\n" + "</div>\n" + "<center>\n"
//			    + "<h6 style=\"margin: 2px;font-size: 14px;\">Mobile No.</strong> "+invoiceHeaderDetails.getMobileNo()+", &nbsp;&nbsp;<strong>Email:</strong> "+invoiceHeaderDetails.getEmailId()+", &nbsp;&nbsp; Website: "+invoiceHeaderDetails.getWebsite()+"</h6>\n"
			    + "<h6 style=\"margin: 2px;font-size: 14px;\">"+mobileNo+" &nbsp;&nbsp;<strong>Email:</strong> "+invoiceHeaderDetails.getEmailId()+", &nbsp;&nbsp; Website: "+invoiceHeaderDetails.getWebsite()+"</h6>\n"
			    + "</center>";

			return HTML;
	}
	
	

//	public ByteArrayOutputStream generatePdfInvoice(DonationDetails donationDetails,
//			InvoiceHeaderDetails invoiceHeaderDetails) throws IOException {
//
//		ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
//
//		donationDetails.setInvoiceDownloadStatus("YES");
//		donationHelper.updateDonationDetails(donationDetails);
//
//		String htmlContent = htmlInvoice(donationDetails, invoiceHeaderDetails);
//
//		// Convert HTML to PDF and write it to the output stream
//		HtmlConverter.convertToPdf(htmlContent, pdfStream);
//
//		//Coment it if dont want to save in local storage
//		try (FileOutputStream fileOutputStream = new FileOutputStream(Constant.baseDocLocation+Constant.receipt+donationDetails.getReceiptNumber().toString()+".pdf")) {
//	        pdfStream.writeTo(fileOutputStream);
//	        
//	        System.out.println("Pdf Done");
//	        
//	    }
//			
//		return pdfStream;
//	}
	
	public ByteArrayOutputStream generatePdfInvoice(
	        DonationDetails donationDetails,
	        InvoiceHeaderDetails invoiceHeaderDetails) throws IOException {

	    ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

	    String htmlContent = htmlInvoice(donationDetails, invoiceHeaderDetails);

	    ConverterProperties props = new ConverterProperties();
	    props.setCharset("UTF-8");

	    HtmlConverter.convertToPdf(htmlContent, pdfStream, props);

	    // Update DB only after success
	    donationDetails.setInvoiceDownloadStatus("YES");
	    donationHelper.updateDonationDetails(donationDetails);

	    // Optional local save
	    try (FileOutputStream fos = new FileOutputStream(
	            Constant.baseDocLocation +
	            Constant.receipt +
	            donationDetails.getReceiptNumber() + ".pdf")) {

	        pdfStream.writeTo(fos);
	    }

	    return pdfStream;
	}

	
	public boolean deleteInvoiceFile(DonationDetails donationDetails) {
	    File file = new File(Constant.baseDocLocation+Constant.receipt+donationDetails.getReceiptNumber().toString()+".pdf");
	    if (file.exists()) {
	    	System.out.println("Delete Successfully");
	        return file.delete(); // Returns true if the file is successfully deleted
	    } else {
	        System.out.println("File not found: " + filePath);
	        return false;
	    }
	}
	
//	public ByteArrayOutputStream generatePdfInvoiceAndSave(DonationDetails donationDetails,
//	        InvoiceHeaderDetails invoiceHeaderDetails) throws IOException {
//
//	    ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
//
//	    // Update the invoice download status
//	    donationDetails.setInvoiceDownloadStatus("YES");
//	    donationHelper.updateDonationDetails(donationDetails);
//
//	    // Generate HTML content
//	    String htmlContent = htmlInvoice(donationDetails, invoiceHeaderDetails);
//
//	    // Convert HTML to PDF and write it to the output stream
//	    HtmlConverter.convertToPdf(htmlContent, pdfStream);
//
//	    // Save the PDF to local storage
//	    try (FileOutputStream fileOutputStream = new FileOutputStream("D://invoice123.pdf")) {
//	        pdfStream.writeTo(fileOutputStream);
//	    }
//
//	    return pdfStream;
//	}
	
	

	

	
	private static final String[] units = { "", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight",
			" Nine" };
	private static final String[] twoDigits = { " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen",
			" Sixteen", " Seventeen", " Eighteen", " Nineteen" };
	private static final String[] tenMultiples = { "", "", " Twenty", " Thirty", " Forty", " Fifty", " Sixty",
			" Seventy", " Eighty", " Ninety" };
	private static final String[] placeValues = { " ", " Thousand", " Million", " Billion", " Trillion" };

	private String convertNumber(long number) {
		String word = "";
		int index = 0;
		do {
			// take 3 digits in each iteration
			int num = (int) (number % 1000);
			if (num != 0) {
				String str = ConversionForUptoThreeDigits(num);
				word = str + placeValues[index] + word;
			}
			index++;
			// next 3 digits
			number = number / 1000;
		} while (number > 0);
		return word;
	}

	private static String ConversionForUptoThreeDigits(int number) {
		String word = "";
		int num = number % 100;
		if (num < 10) {
			word = word + units[num];
		} else if (num < 20) {
			word = word + twoDigits[num % 10];
		} else {
			word = tenMultiples[num / 10] + units[num % 10];
		}

		word = (number / 100 > 0) ? units[number / 100] + " Hundred" + word : word;
		return word;
	}

}
