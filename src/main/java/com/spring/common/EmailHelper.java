package com.spring.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spring.dao.SmsTemplateDetailsDao;
import com.spring.entities.DonationDetails;
import com.spring.entities.SmsTemplateDetails;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



@Component
public class EmailHelper {
	
	@Autowired
	private SmsTemplateDetailsDao smsDetailsDao;
	
	
	@Transactional
	public SmsTemplateDetails getSmsDetailsBySuperadminId(String superadminId, String smsType) {

		CriteriaBuilder criteriaBuilder = smsDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<SmsTemplateDetails> criteriaQuery = criteriaBuilder.createQuery(SmsTemplateDetails.class);
		Root<SmsTemplateDetails> root = criteriaQuery.from(SmsTemplateDetails.class);
		Predicate restriction = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaBuilder.equal(root.get("smsType"), smsType);
		criteriaQuery.where(restriction);
		SmsTemplateDetails smsDetails = smsDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return smsDetails;
	}
	

    public static void sendEmailWithAttachments() throws MessagingException {

        // SMTP server configuration
        String host = "smtp-relay.brevo.com";
        String port = "587";
        String mailFrom = "datafusionlabs@gmail.com";
        String password = "pURY2Btx6a08EAQW";

        // Message details
        String mailTo = "vivekbharti333@gmail.com";
        String subject = "Testing";
        String message = "I have some attachments for you.";

        // Attachments
        String[] attachFiles = {"C:\\Users\\lenovo\\Downloads\\WhatsApp Image 2023-12-18 at 1.02.04 PM (1).jpeg"};

        // Set SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a session with an authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailFrom, password);
            }
        });

        // Create a new email message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress("invoice@mydonation.co.in"));
        InternetAddress[] toAddresses = {new InternetAddress(mailTo)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        // Create message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");

        // Create multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Add attachments
        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                multipart.addBodyPart(attachPart);
            }
        }

        // Set the multi-part as email content
        msg.setContent(multipart);

        // Send the email
        Transport.send(msg);
    }
 
	
	
	
}
