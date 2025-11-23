package com.spring.email.template;

import org.springframework.stereotype.Component;

import com.spring.entities.DonationDetails;

@Component
public class DonationThankYou {
	
public String getDonationThankYouTemplate(DonationDetails donationDetails) {
        
        String template = "<body style=\"font-family: Arial, sans-serif; background-color: #f5f6fa; margin: 0; padding: 0;\">\r\n"
        		+ "  <div style=\"max-width:600px; background-color:#ffffff; margin:30px auto; padding:30px; border-radius:10px; box-shadow:0 2px 8px rgba(0,0,0,0.1);\">\r\n"
        		+ "    \r\n"
        		+ "    <div style=\"text-align:center; border-bottom:2px solid #4CAF50; padding-bottom:10px; margin-bottom:20px;\">\r\n"
        		+ "      <h2 style=\"color:#4CAF50; margin:0;\">Thank You for Your Support!</h2>\r\n"
        		+ "    </div>\r\n"
        		+ "\r\n"
        		+ "    <div style=\"font-size:16px; line-height:1.6; color:#333333;\">\r\n"
        		+ "      <p style=\"margin-bottom:15px;\">Dear <strong>"+donationDetails.getDonorName()+"</strong>,</p>\r\n"
        		+ "\r\n"
        		+ "      <p style=\"margin-bottom:15px;\">\r\n"
        		+ "        I hope this message finds you well. On behalf of everyone at <strong>Cef International</strong>, \r\n"
        		+ "        I would like to extend our heartfelt gratitude for your generous contribution of \r\n"
        		+ "        <strong>₹"+donationDetails.getAmount()+"</strong>. Your support makes a significant impact on our mission to <strong>"+donationDetails.getProgramName()+"</strong>.\r\n"
        		+ "      </p>\r\n"
        		+ "\r\n"
        		+ "      <p style=\"margin-bottom:15px;\">\r\n"
        		+ "        In appreciation of your support, we are pleased to inform you that your donation is eligible for \r\n"
        		+ "        <strong>tax exemption under Section 80G</strong> of the Income Tax Act. This means you can claim \r\n"
        		+ "        a tax deduction for your contribution, making your generosity even more impactful.\r\n"
        		+ "      </p>\r\n"
        		+ "\r\n"
        		+ "      <p style=\"margin-bottom:15px;\">To access and download your donation receipt, please click the button below:</p>\r\n"
        		+ "\r\n"
        		+ "      <div style=\"text-align:center; margin:30px 0;\">\r\n"
        		+ "        <a href=\"https://mydonation.in/#/thank-you/receipt?receiptNo="+donationDetails.getReceiptNumber()
        		+ "           style=\"background-color:#4CAF50; color:#ffffff; text-decoration:none; padding:12px 25px; border-radius:6px; font-weight:bold; display:inline-block;\">\r\n"
        		+ "          Download Receipt\r\n"
        		+ "        </a>\r\n"
        		+ "      </div>\r\n"
        		+ "\r\n"
        		+ "      <p style=\"margin-bottom:15px;\">Once again, thank you for your continued trust and support. Together, we are making a real difference.</p>\r\n"
        		+ "\r\n"
        		+ "      <p style=\"margin-bottom:15px;\">Warm regards,</p>\r\n"
        		+ "\r\n"
        		+ "      <p style=\"margin-bottom:15px;\">\r\n"
        		+ "        <strong>Cef International</strong><br/>\r\n"
        		+ "        <a href=\"https://www.cefinternational.org\" style=\"color:black; text-decoration:none;\">www.cefinternational.org</a>\r\n"
        		+ "      </p>\r\n"
        		+ "    </div>\r\n"
        		+ "\r\n"
        		+ "    <div style=\"text-align:center; font-size:13px; color:#777777; border-top:1px solid #e0e0e0; padding-top:15px; margin-top:20px;\">\r\n"
        		+ "      © 2025 Cef International | All Rights Reserved<br/>\r\n"
        		+ "      This is an automated email, please do not reply.\r\n"
        		+ "    </div>\r\n"
        		+ "  </div>\r\n"
        		+ "</body>\r\n"
        		+ "";
        
        return template;
    }

}
