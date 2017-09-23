package com.nokia.charts.util;

import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class MailUtils {

	// Mail server host
	private static final String HOST = "172.24.146.133";

	// Server address
	private static final String FROM = "operation_control@monitoring.com";
	
	private static Logger logger = Logger.getLogger(MailUtils.class); 

	/**
	 * Send mail
	 * 
	 * @param to
	 *            Target Mail Address --multiple users format ：
	 *            "xxx@xxx.com,xxx@xxx.com,xxx@xxx.com"
	 * @param cc
	 *            Carbon copy mail address
	 * @param subject
	 *            Mail Subject
	 * @param content
	 *            The mail body details
	 * @author xigao
	 */
	public static String sendMail(String to, String cc, String subject,
			String content) {

		String status = "0";
		// Setup mail server
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", HOST);

		// Get the session object
		Session session = Session.getDefaultInstance(properties);

		// compose the message
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(FROM));

			if (null != to && !to.isEmpty()) {
				// Set To: header field of the header.
				@SuppressWarnings("static-access")
				InternetAddress[] internetAddressTo = new InternetAddress().parse(to);
				message.addRecipients(Message.RecipientType.TO, internetAddressTo);
			}

			if (null != cc && !cc.isEmpty()) {
				// Set CC: cc field of the header.
				@SuppressWarnings("static-access")
				InternetAddress[] internetAddressCC = new InternetAddress().parse(cc);
				message.addRecipients(Message.RecipientType.CC, internetAddressCC);
			}

			// Set Mail Subject
			message.setSubject(subject);

			// Set Send Date
			message.setSentDate(new Date());

			// Now set the actual message
			message.setContent(content, "text/html");

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");
			logger.info("Mail to : " + to);
			logger.info("Mail Cc : " + cc);
			return status;
		} catch (AddressException e) {
			e.printStackTrace();
			logger.error(e);
			status = "-2";
			return status;
		} catch (MessagingException e) {
			e.printStackTrace();
			logger.error(e);
			status = "-3";
			return status;
		}
	}
	
	/**
	 * Check mail address
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			logger.error(e);
			flag = false;
		}
		return flag;
	}

}
