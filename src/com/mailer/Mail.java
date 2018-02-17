package com.mailer;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;



@Path("/Mailer")
public class Mail {
	@POST
	@Path("/sendMail")
	@Consumes(MediaType.APPLICATION_JSON)
	public void sendMail(BeanClass request)  {
		String username = request.From;
		String password = request.Password;
		
		String host = username.substring(username.lastIndexOf("@"), username.lastIndexOf("."));
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp."+host+".com");
		props.put("mail.smtp.host", "smtp."+host+".com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(request.From));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(request.To));
			message.setSubject(request.Subject);
			message.setText(request.Message);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}