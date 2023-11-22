package com.spring.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.itextpdf.html2pdf.HtmlConverter;
import com.spring.constant.Constant;
import com.spring.entities.DonationDetails;
import com.spring.entities.InvoiceHeaderDetails;
import com.spring.entities.UserDetails;
import com.spring.helper.DonationHelper;
import com.spring.helper.InvoiceHelper;
import com.spring.helper.UserHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class PdfInvoice {
	
	@Autowired
	private DonationHelper donationHelper;
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private InvoiceHelper invoiceHelper;
	
	@Autowired
	private FilePath filePath;
	
//	src="data:image/jpeg;base64,[the value of your base64DataString]"
	
public String htmlInvoice(DonationDetails donationDetails, InvoiceHeaderDetails invoiceHeaderDetails) {
		
		if(donationDetails.getDonorName()== null) {
			donationDetails.setDonorName("---");
		}if(donationDetails.getAddress() == null) {
			donationDetails.setAddress("---");
		}if(donationDetails.getEmailId() == null) {
			donationDetails.setEmailId("---");
		}if(donationDetails.getMobileNumber() == null) {
			donationDetails.setMobileNumber("---");
		}if(donationDetails.getPanNumber() == null) {
			donationDetails.setPanNumber("---");
		}
		
		String basePath = filePath.getPathToUploadFile(Constant.invoiceImage);

		String pattern = "dd/MM/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(donationDetails.getCreatedAt());
		
		double amount = donationDetails.getAmount();
		long finalValue = Math.round(amount); 
			
		String word = convertNumber(finalValue);

		
		String HTML = "<div class=\"body-Container\" style=\" width: 100%; border: 1px solid black;\">\n"
			    + "<table width=\"100%\">\n" + "<tr>\n" + "<td width=\"16%\" height=\"140px\">\n"
			    + "<img src="+basePath+File.separator+invoiceHeaderDetails.getCompanyLogo()+" alt=\"Image\" width=\"100%\" height=\"100%\">\n"
			    + "</td>\n" + "<td width=\"60%\">\n" + "<center>\n"
			    + "<h1 style=\"font-size: 40px; margin: 0;\"><strong style=\"color: "+invoiceHeaderDetails.getCompanyFirstNameColor()+";\">"+invoiceHeaderDetails.getCompanyFirstName()+"</strong> <strong style=\"color: "+invoiceHeaderDetails.getCompanyLastNameColor()+";\">"+invoiceHeaderDetails.getCompanyLastName()+"</strong></h1>\n"
			    + "<h5 style=\"margin: 2px;font-size: 14px;\">Registration No.: "+invoiceHeaderDetails.getGstNumber()+",&nbsp; PAN NO.: "+invoiceHeaderDetails.getPanNumber()+"</h5>\n"
			    + "<h6 style=\"margin: 2px; font-size: 14px;\">Off. Add:"+invoiceHeaderDetails.getOfficeAddress()+"</h6>\n"
			    + "<h6 style=\"margin: 2px; font-size: 14px;\">Reg. Add:"+invoiceHeaderDetails.getRegAddress()+"</h6>"
			    + "</td></tr></table></center>"
			    + "<center><p><strong style=\"font-size: 18px;\">Tax Benefit Receipt</strong> </p>\n</center>\n"
			    + "<div class=\"form-container\" style=\"width: 100%; height: auto; background-color: "+invoiceHeaderDetails.getBackgroundColor()+";\">\n"
			    + "<hr>\n" + "<p style=\"padding: 10px;font-size: 22px \">Receipt No: <strong>"
			    + donationDetails.getInvoiceNumber()
			    + "</strong> <span style=\"text-align: right; float: right;margin-right: 10px;\">Date:" + date
			    + "</span> </p>\n"
			    + "<p style=\"font-size: 19px;margin-right: 40px; margin-left: 40px;line-height: 30px; text-align: justify;\"><strong>"+invoiceHeaderDetails.getCompanyFirstName()+" "+invoiceHeaderDetails.getCompanyLastName()+"</strong> is thankful to <strong>"
			    + donationDetails.getDonorName() + "</strong> &nbsp; &nbsp; Address:<strong> "
			    + donationDetails.getAddress() + "</strong>&nbsp; &nbsp; Email:<strong> "
			    + donationDetails.getEmailId() + "</strong>&nbsp;&nbsp;Contact No: <strong>"
			    + donationDetails.getMobileNumber() + "</strong>&nbsp; &nbsp;Pan No: <strong>"
			    + donationDetails.getPanNumber()
			    + "</strong>&nbsp; &nbsp;<strong>for kind</strong> donation of <strong>Rs: " + donationDetails.getAmount()
			    + "/-</strong>. ("+ word + " Only/-) for "+donationDetails.getProgramName()+".\n" + "\n" + "\n" + "<br>\n"
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
			    + "<h6 style=\"margin: 2px;font-size: 14px;\">Mobile No.</strong> "+invoiceHeaderDetails.getMobileNo()+", &nbsp;&nbsp;<strong>Email:</strong> "+invoiceHeaderDetails.getEmailId()+", &nbsp;&nbsp; Website: "+invoiceHeaderDetails.getWebsite()+"</h6>\n"
			    + "</center>";

			return HTML;
	}
	

//	public void generatePdfInvoice1(DonationDetails donationDetails, InvoiceHeaderDetails invoiceHeaderDetails) throws FileNotFoundException, IOException {
//		
//		
//		if(invoiceHeaderDetails != null) {
//			String currentYear = new SimpleDateFormat("MMyyyy").format(new Date());
//			String invoiceNumber = invoiceHeaderDetails.getInvoiceInitial().toLowerCase()+"/"+currentYear+"/"+(invoiceHeaderDetails.getSerialNumber()+1);
//			
//			//update donation details
//			donationDetails.setInvoiceNumber(invoiceNumber);
//			donationDetails.setInvoiceDownloadStatus("YES");
//			donationHelper.updateDonationDetails(donationDetails);
//			
//			//update serialNumber
//			invoiceHeaderDetails.setSerialNumber(invoiceHeaderDetails.getSerialNumber()+1);
//			invoiceHelper.updateInvoiceHeaderDetails(invoiceHeaderDetails);
//		}
//		
//		String basePath = filePath.getPathToUploadFile(Constant.receipt);
//		String path = basePath + File.separator + donationDetails.getInvoiceNumber().replace("/", "");
//		
//		HtmlConverter.convertToPdf(htmlInvoice(donationDetails, invoiceHeaderDetails), new FileOutputStream(path+".pdf"));
//	}
	
	
	public ByteArrayOutputStream generatePdfInvoice(DonationDetails donationDetails, InvoiceHeaderDetails invoiceHeaderDetails) throws IOException {
	    ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

	    UserDetails userDetails = userHelper.getUserDetailsByLoginIdAndSuperadminId(donationDetails.getTeamLeaderId(), donationDetails.getSuperadminId());
	    
	    if (invoiceHeaderDetails != null) {
	        String currentYear = new SimpleDateFormat("MMyyyy").format(new Date());
	        String invoiceNumber = userDetails.getUserCode()+"/"+invoiceHeaderDetails.getInvoiceInitial().toUpperCase() + "/" + currentYear + "/" + (invoiceHeaderDetails.getSerialNumber() + 1);

	        
	        if(donationDetails.getInvoiceNumber() == null) {
	        	 // Update donation details
		        donationDetails.setInvoiceNumber(invoiceNumber);
		        donationDetails.setInvoiceDownloadStatus("YES");
		        donationHelper.updateDonationDetails(donationDetails);

		        // Update serialNumber
		        invoiceHeaderDetails.setSerialNumber(invoiceHeaderDetails.getSerialNumber() + 1);
		        invoiceHelper.updateInvoiceHeaderDetails(invoiceHeaderDetails);
	        }
	       
	    }

//	    String basePath = filePath.getPathToUploadFile(Constant.receipt);

	    String htmlContent = htmlInvoice(donationDetails, invoiceHeaderDetails);

	    // Convert HTML to PDF and write it to the output stream
	    HtmlConverter.convertToPdf(htmlContent, pdfStream);

	    return pdfStream;
	}


	

	
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
