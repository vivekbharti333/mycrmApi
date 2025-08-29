package com.spring.email.template;

import org.springframework.stereotype.Component;
import com.spring.entities.UserDetails;

@Component
public class DatfuslabWelcome {
    
    public String getTemplate(UserDetails userDetails) {
        
        String template = "<table cellSpacing=\"0\" cellPadding=\"0\" style=\"margin:0 auto; width: 100%; background-color:#ffffff;\">\r\n"
            + "  <tbody>\r\n"
            + "    <tr>\r\n"
            + "      <td>\r\n"
            + "        <div style=\"background-color: #ffffff; border: 1px solid #eee; font-family: Lato, Helvetica, Arial, sans-serif; margin: auto; max-width: 600px; width: 600px;\">\r\n"
            + "          <div style=\"padding: 20px 40px; background-color: #f9f9f9; text-align: center; border-bottom: 1px solid #eee;\">\r\n"
            + "            <p style=\"font-size: 14px; color: #1B3E71; font-style: italic; margin: 0;\">“Every Donation Counts – Together, We Create Change”</p>\r\n"
            + "          </div>\r\n"
            + "          <div style=\"padding: 40px 40px 20px; background: linear-gradient(135deg, #1B3E71 0%, #4A90E2 100%); color:#ffffff;\">\r\n"
            + "            <h4 style=\"font-size: 16px; margin: 0 0 10px;\">Hi " + userDetails.getFirstName() + " " + userDetails.getLastName() + ",</h4>\r\n"
            + "            <h2 style=\"font-size: 24px; margin: 0; font-weight: bold;\">Welcome to MyDonation – Empowering Your NGO Journey!</h2>\r\n"
            + "          </div>\r\n"
            + "          <div style=\"padding: 30px 40px; color:#333;\">\r\n"
            + "            <p style=\"font-size: 15px; line-height: 22px;\">\r\n"
            + "              Thank you for joining <strong>Datfuslab Technologies</strong>. We're excited to help your NGO streamline donations, engage donors, and amplify your mission effortlessly.\r\n"
            + "            </p>\r\n"
            + "            <p style=\"font-size: 15px; margin-top: 20px; color: #1B3E71; font-weight:bold;\">Key Features at Your Fingertips:</p>\r\n"
            + "            <ul style=\"font-size: 14px; line-height: 22px; margin: 15px 0 25px 20px; color:#555;\">\r\n"
            + "              <li>Donation Management – Collect & track donations with ease</li>\r\n"
            + "              <li>Automated Receipts – Email, WhatsApp & SMS receipts instantly</li>\r\n"
            + "              <li>Donor Engagement – Thank donors & build lasting relationships</li>\r\n"
            + "              <li>Reports & Analytics – Gain real-time insights for smarter decisions</li>\r\n"
            + "            </ul>\r\n"
            + "            <p style=\"font-size: 15px; color:#333;\">Your account details:</p>\r\n"
            + "            <div style=\"background-color: #F5F7FA; border-radius: 8px; font-size: 14px; padding: 20px; margin: 20px 0;\">\r\n"
            + "              <div style=\"margin-bottom: 15px;\">\r\n"
            + "                <strong style=\"display: inline-block; width: 80px; color:#555;\">Login URL</strong>\r\n"
            + "                <span style=\"color:#999;\">:</span>\r\n"
            + "                <a style=\"color: #1B3E71; text-decoration: none; font-weight: bold;\" href=\"https://mydonation.co.in\">Click here</a>\r\n"
            + "              </div>\r\n"
            + "              <div>\r\n"
            + "                <strong style=\"display: inline-block; width: 80px; color:#555;\">Username</strong>\r\n"
            + "                <span style=\"color:#999;\">:</span>\r\n"
            + "                <span style=\"color: #1B3E71; font-weight: bold;\">" + userDetails.getLoginId() + "</span>\r\n"
            + "              </div>\r\n"
            + "            </div>\r\n"
            + "          </div>\r\n"
            + "          <div style=\"padding: 20px 40px; background-color: #f9f9f9; color: #777; font-size: 13px; text-align: center; border-top: 1px solid #eee;\">\r\n"
            + "            <p>Need help? <a href=\"mailto:support@datfuslab.com\" style=\"color: #1B3E71; text-decoration: none;\">Contact Support</a> | <a href=\"https://mydonation.co.in/help\" style=\"color: #1B3E71; text-decoration: none;\">Help Center</a></p>\r\n"
            + "            <p>© Datfuslab Technologies Pvt. Ltd.</p>\r\n"
            + "          </div>\r\n"
            + "        </div>\r\n"
            + "      </td>\r\n"
            + "    </tr>\r\n"
            + "  </tbody>\r\n"
            + "</table>";
        
        return template;
    }
}
