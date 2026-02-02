package com.common.email;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.entities.InvoiceHeaderDetails;
import com.ngo.dao.SmsTemplateDetailsDao;
import com.ngo.entities.DonationDetails;
import com.ngo.entities.EmailServiceDetails;
import com.ngo.entities.SmsTemplateDetails;
import com.spring.common.PdfInvoice;

@Component
public class BrevoEmail {


	@Autowired
	private SmsTemplateDetailsDao smsDetailsDao;

	@Autowired
	private PdfInvoice pdfInvoice;

	
//	StringUtils.substring(RandomStringUtils.random(64, false, true), 0,6)

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

	public void sendEmailWithAttachments() throws MessagingException {

		// SMTP server configuration
		String host = "smtp-relay.brevo.com";
		String port = "587";
		String mailFrom = "datafusionlabs@gmail.com";
		String password = "pURY2Btx6a08EAQW";

		// Message details
		String mailTo = "vivekbharti333@gmail.com";
		String subject = "Testing";
		String message = "<p><strong>Dear Vivek, </strong></p>\r\n"
				+ "\r\n"
				+ "<p><strong>I want to extend our deepest gratitude for your generous donation. Please find the attached invoice of your donation .</strong></p>";

		// Attachments
		String[] attachFiles = { "C:\\Users\\lenovo\\Downloads\\WhatsApp Image 2023-12-18 at 1.02.04 PM (1).jpeg" };

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
		InternetAddress[] toAddresses = { new InternetAddress(mailTo) };
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


	
	public void sendEmailWithInvoice(InvoiceHeaderDetails invoiceHeader, DonationDetails donationDetails, EmailServiceDetails emailServiceDetails) throws MessagingException, IOException {
		
		//Get details and generate pdf

         ByteArrayOutputStream pdfContent = pdfInvoice.generatePdfInvoice(donationDetails, invoiceHeader);
		
		
		// SMTP server configuration
		String host = emailServiceDetails.getHost();
		String port = emailServiceDetails.getPort();
		String userId = emailServiceDetails.getEmailUserid();
		String password = emailServiceDetails.getEmailPassword();

		// Message details
		String mailFrom = emailServiceDetails.getEmailFrom();
		String mailTo = donationDetails.getEmailId();
		String subject = emailServiceDetails.getSubject();
		String message = emailServiceDetails.getEmailBody();

		// Attachments
		String[] attachFiles = {};

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
				return new PasswordAuthentication(userId, password);
			}
		});

		// Create a new email message
		Message msg = new MimeMessage(session);

		InternetAddress fromAddress = new InternetAddress(mailFrom);
		msg.setFrom(fromAddress);
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		// Create message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		// Create multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// Add PDF attachment
		MimeBodyPart pdfAttachment = new MimeBodyPart();
		pdfAttachment.setDisposition(MimeBodyPart.ATTACHMENT); // Set disposition to attachment
		DataSource pdfDataSource = new ByteArrayDataSource(pdfContent.toByteArray(), "application/pdf");
		pdfAttachment.setDataHandler(new DataHandler(pdfDataSource));
		pdfAttachment.setFileName("ThankYou.pdf");
		multipart.addBodyPart(pdfAttachment);

		// Add other attachments
		if (attachFiles != null && attachFiles.length > 0) {
			for (String filePath : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();
				attachPart.attachFile(filePath);
				multipart.addBodyPart(attachPart);
			}
		}

		// Set the multi-part as email content
		msg.setContent(multipart);

		// Send the email
		try (Transport transport = session.getTransport("smtp")) {
			transport.connect(host, userId, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			
			System.out.println("transport : "+transport);
		}
	}

}
