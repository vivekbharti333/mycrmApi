package com.ngo.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.constant.Constant;
import com.common.enums.Status;
import com.common.exceptions.BizException;
import com.ngo.dao.EmailServiceDetailsDao;
import com.ngo.entities.EmailServiceDetails;
import com.ngo.object.request.EmailServiceRequestObject;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Component
public class EmailHelper {

	@Autowired
	private EmailServiceDetailsDao emailServiceDetailsDao;


	public void validateEmailServiceRequest(EmailServiceRequestObject emailServiceRequestObject) throws BizException {
		if (emailServiceRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
		if (emailServiceRequestObject.getEmailType() == null || emailServiceRequestObject.getEmailType().equalsIgnoreCase("") ) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Select Email Type");
		}
	}

	@Transactional
	public EmailServiceDetails getEmailDetailsByEmailTypeAndSuperadinId(String emailType, String superadminId) {

		CriteriaBuilder criteriaBuilder = emailServiceDetailsDao.getSession().getCriteriaBuilder();
		CriteriaQuery<EmailServiceDetails> criteriaQuery = criteriaBuilder.createQuery(EmailServiceDetails.class);
		Root<EmailServiceDetails> root = criteriaQuery.from(EmailServiceDetails.class);
		Predicate restriction1 = criteriaBuilder.equal(root.get("emailType"), emailType);
		Predicate restriction2 = criteriaBuilder.equal(root.get("superadminId"), superadminId);
		criteriaQuery.where(restriction1, restriction2);
		EmailServiceDetails optionTypeDetails = emailServiceDetailsDao.getSession().createQuery(criteriaQuery).uniqueResult();
		return optionTypeDetails;
	}

	public EmailServiceDetails getEmailServiceDetailsByReqObj(EmailServiceRequestObject emailServiceRequest) {

		EmailServiceDetails emailServiceDetails = new EmailServiceDetails();

		emailServiceDetails.setEmailType(emailServiceRequest.getEmailType());
		emailServiceDetails.setStatus(Status.ACTIVE.name());
		emailServiceDetails.setHost(emailServiceRequest.getHost());
		emailServiceDetails.setPort(emailServiceRequest.getPort());
		emailServiceDetails.setEmailUserid(emailServiceRequest.getEmailUserid());
		emailServiceDetails.setEmailPassword(emailServiceRequest.getEmailPassword());
		emailServiceDetails.setEmailFrom(emailServiceRequest.getEmailFrom());
		emailServiceDetails.setSubject(emailServiceRequest.getSubject());
		emailServiceDetails.setEmailBody(emailServiceRequest.getEmailBody());
		emailServiceDetails.setRegards(emailServiceRequest.getRegards());
		emailServiceDetails.setWebsite(emailServiceRequest.getWebsite());
		emailServiceDetails.setServiceProvider(emailServiceRequest.getServiceProvider());
		
		emailServiceDetails.setSuperadminId(emailServiceRequest.getSuperadminId());
		emailServiceDetails.setCreatedAt(new Date());
		emailServiceDetails.setUpdatedAt(new Date());

		return emailServiceDetails;
	}

	@Transactional
	public EmailServiceDetails saveEmailServiceDetails(EmailServiceDetails emailServiceDetails) {
		emailServiceDetailsDao.persist(emailServiceDetails);
		return emailServiceDetails;
	}
	
	public EmailServiceDetails getUpdatedEmailServiceDetailsByReqObj(EmailServiceRequestObject emailServiceRequest, EmailServiceDetails emailServiceDetails) {

		emailServiceDetails.setEmailType(emailServiceRequest.getEmailType());
		emailServiceDetails.setStatus(Status.ACTIVE.name());
		emailServiceDetails.setHost(emailServiceRequest.getHost());
		emailServiceDetails.setPort(emailServiceRequest.getPort());
		emailServiceDetails.setEmailUserid(emailServiceRequest.getEmailUserid());
		emailServiceDetails.setEmailPassword(emailServiceRequest.getEmailPassword());
		emailServiceDetails.setEmailFrom(emailServiceRequest.getEmailFrom());
		emailServiceDetails.setSubject(emailServiceRequest.getSubject());
		emailServiceDetails.setEmailBody(emailServiceRequest.getEmailBody());
		emailServiceDetails.setSuperadminId(emailServiceRequest.getSuperadminId());
		emailServiceDetails.setUpdatedAt(new Date());

		return emailServiceDetails;
	}
	
	@Transactional
	public EmailServiceDetails updateEmailServiceDetails(EmailServiceDetails emailServiceDetails) {
		emailServiceDetailsDao.update(emailServiceDetails);
		return emailServiceDetails;
	}

	@SuppressWarnings("unchecked")
	public List<EmailServiceDetails> getEmailServiceDetailsList(EmailServiceRequestObject optionRequest) {
		List<EmailServiceDetails> results = new ArrayList<>();
		results = emailServiceDetailsDao.getEntityManager().createQuery(
				"SELECT PM FROM EmailServiceDetails PM WHERE PM.superadminId =:superadminId")
				.setParameter("superadminId", optionRequest.getSuperadminId()).getResultList();
		return results;
	}
	
	
	
	public void sendeMail() {
		
		System.out.println("Enter into email 1");
        // SMTP server details
        String host = "smtpout.secureserver.net";
        String port = "465";
        String username = "info@datfuslab.com";
        String password = "8800689752@Vb";
        
        System.out.println("Enter into email 2");

        // Email details
        String toEmail = "vivekbharti333@gmail.com";  // Replace with the recipient's email
        String subject = "Test Email from Java";
        String body = "Hello, this is a test email sent from Java using SMTP.";
        
        System.out.println("Enter into email 3");

        // Set properties for the email session
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.starttls.enable", "true");
        
        System.out.println("Enter into email 4");

        // Create the session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        System.out.println("Enter into email 5");

        try {
            // Create the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);
            
            System.out.println("Enter into email 6");

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
	
	
	
	public void SMTPBrevoEmailSender() {
        // SMTP server details
        String smtpHost = "smtp.gmail.com";
        String smtpPort = "587";
        String username = "datafusionlabs@gmail.com";
        String password = "xQDKU6hdW2af4Pbt";

        // Email details
        String fromEmail = "datafusionlabs@gmail.com";  // Sender's email
        String toEmail = "vivekbharti333@gmail.com";       // Recipient's email
        String subject = "Test Email via Brevo SMTP";
        String body = "Hello, this is a test email sent using Brevo SMTP in Java.";

        // Set properties for the SMTP session
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS for port 587

        // Create the session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Failed to send the email.");
            e.printStackTrace();
        }
    }
	
	

}
