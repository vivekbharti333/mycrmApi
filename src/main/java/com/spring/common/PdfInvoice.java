package com.spring.common;

import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.*;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.spring.entities.DonationDetails;
import com.spring.entities.InvoiceHeaderDetails;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletResponse;

@Component
public class PdfInvoice {
	
	
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
		
		String[] companyName = invoiceHeaderDetails.getCompanyName().split(" ");
		String companyNameFirst = companyName[0];
		String companyNameSecond = companyName[1];

		String pattern = "dd/MM/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(donationDetails.getCreatedAt());
		
				

//		String word = convertNumber((long)donationDetails.getAmount());
		String word = "(One Hhousand Only/";

		String HTML = "<div class=\"body-Container\" style=\" width: 100%; border: 1px solid black;\">\n"
				+ "<table width=\"100%\">\n" + "<tr>\n" + "<td width=\"16%\" height=\"140px\">\n"
				+ "<img src=\"D:\\cef.png\" width=\"100%\" height=\"100%\">\n"
				+ "</td>\n" + "<td width=\"60%\">\n" + "<center>\n"
				+ "<h1 style=\"font-size: 40px; margin: 0;\"><strong style=\"color: #0477c5;\">"+companyNameFirst+"</strong> <strong style=\"color: #8aca49;\">"+companyNameSecond+"</strong></h1>\n"
				+ "<h5 style=\"margin: 2px;font-size: 14px;\">Trust Registration: E-34254(M)&nbsp; PAN NO."+invoiceHeaderDetails.getPanNumber()+"</h5>\n"
				+ "<h6 style=\"margin: 2px; font-size: 14px;\">Off. Add:"+invoiceHeaderDetails.getOfficeAddress()+"</h6>\n"
				+ "<h6 style=\"margin: 2px; font-size: 14px;\">Reg. Add:"+invoiceHeaderDetails.getRegAddress()+"</h6>"
				+ "</td></tr></table></center>"
				+ "<center><p><strong style=\"font-size: 18px;\">Tax Benefit Receipt</strong> </p>\n</center>\n"
				+ "<div class=\"form-container\" style=\"width: 100%; height: auto; background-color: #8aca49;\">\n"
				+ "<hr>\n" + "<p style=\"padding: 10px;font-size: 22px \">Receipt No: <strong>"
				+ donationDetails.getInvoiceNumber()
				+ "</strong> <span style=\"text-align: right; float: right;margin-right: 10px;\">Date:" + date
				+ "</span> </p>\n"
				+ "<p style=\"font-size: 19px;margin-right: 40px; margin-left: 40px;line-height: 30px; text-align: justify;\"><strong>"+invoiceHeaderDetails.getCompanyName()+"</strong> is thankful to <strong>"
				+ donationDetails.getDonorName() + "</strong> &nbsp; &nbsp; Address:<strong> "
				+ donationDetails.getAddress() + "</strong>&nbsp; &nbsp; Email:<strong> "
				+ donationDetails.getEmailId() + "</strong>&nbsp;&nbsp;Contact No: <strong>"
				+ donationDetails.getMobileNumber() + "</strong>&nbsp; &nbsp;Pan No: <strong>"
				+ donationDetails.getPanNumber()
				+ "</strong>&nbsp; &nbsp;<strong>for kind</strong> donation of <strong>Rs: " + donationDetails.getAmount()
				+ "/-</strong>. " + word + " Only/-) for "+donationDetails.getProgramName()+".\n" + "\n" + "\n" + "<br>\n"
				+ "<img src=\"D:\\stamp.png\" style=\"width: 115px;margin-left: 100px; height: 115px;\">\n"
				+ "<p style=\"font-size: 20px;letter-spacing: 0.6px;margin-top: -30px; margin-left:110px;\">Authorised Sign.</p>\n"
				+ "\n" + "\n" + "<center><h1 style=\"border-top: 2px solid black;\">Thank You Letter</h1> </center>\n"
				+ "\n"
				+ "<p style=\"margin: 20px;\">Cef Internatinal is Govt. Registered organization working for Welfare of Women & Children since 2017. We are continuously supporting in the field of Education, Health, Youth , Poverty, Livelihood and Community Development. Our aim is to make every individual in the Society should be Self-dependent and raise the quality of life in every aspect. Your Donation would help us to run the skill Development center, Digital Education Center and Community Development center for weaker sections of our society.</p>\n"
				+ "<p style=\"margin-right: 20px; margin-left: 20px;margin-top: 0px;\">Your Donation will go a long way to inspire the people to donate to the NGO who are. Donors like you have harnessed the potential of our young staff and encouraged us to work with sincerity and commitment. We are enclosing a receipt against your donation, along with this letter. We wish to have a long term relationship and good trust with you to serve the society.<strong> Thank for your Support. Keep Supporting Us.</strong> </p>\n"
				+ "<hr>\n" + "</div>\n" + "<div style=\"\">\n"
				+ "<p style=\"font-weight: bold;margin-right: 20px; letter-spacing: 0.5px;line-height: 1.6; margin-left: 20px; text-align: justify;\">Your Donation is eligible for 50&#37; tax benefit under section 80G of Income tax act. Approval No. CIT (Exemption) Mumbai/80G/2020-21/A/10069</p>\n"
				+ "</div>\n" + "</div>\n" + "<center>\n"
				+ "<h6 style=\"margin: 2px;font-size: 14px;\">Mobile No.</strong> "+donationDetails.getMobileNumber()+", &nbsp;&nbsp;<strong>Email:</strong> "+invoiceHeaderDetails.getEmailId()+", &nbsp;&nbsp; Website: "+invoiceHeaderDetails.getWebsite()+"</h6>\n"
				+ "</center>";

		return HTML;
	}
	

	public void generatePdfInvoice(DonationDetails donationDetails, InvoiceHeaderDetails invoiceHeaderDetails) throws FileNotFoundException, IOException {
//		String basePath = userHelper.getPathToUploadFile("RECIEPT");
//		String path = basePath + File.separator + donationDetails.getReferenceNo() + ".pdf";
		
		HtmlConverter.convertToPdf(htmlInvoice(donationDetails, invoiceHeaderDetails), new FileOutputStream("D:\\abc.pdf"));
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
