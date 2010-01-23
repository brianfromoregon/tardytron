package com.tardytron.server.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class PostalService {
	private static final Logger log = Logger.getLogger(PostalService.class.getName());
	public void sendMail(String fromEmail, String fromName, String toEmail,
			String toName, String subject, String msgBody) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromEmail, fromName));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					toEmail, toName));
			msg.setSubject(subject);
			msg.setText(msgBody);
			Transport.send(msg);
			log.info("Email body: " + msgBody);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
