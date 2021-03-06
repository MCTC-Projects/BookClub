/**
 * Created by daryl on 2/24/2015.
 */
//You need to download and add the libraries for javamail API and JavaBeans Activation Framework for this to run
//javamail API:   http://www.oracle.com/technetwork/java/index-138643.html
//JAF:            http://www.oracle.com/technetwork/java/jaf11-139815.html
//Files should already be included in repository (BookClub/jaf-1.1.1, BookClub/javamail-1.4.7)


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//username: BookClubGroup1234@gmail.com
//password: GroupProject2901

public class Emailer {


    public static void sendEmail(String senderEmail, String senderPassword, String recipient,
                                 String subject, String emailBody) {
        // Recipient's email from member array
        String to = recipient;//change accordingly

        // Sender's email ID needs to be mentioned
        String from = senderEmail;//sender email address
        final String username = senderEmail;//sender email address for password authentication
        final String password = senderPassword;//sender password for password authentication

        // set the host smtp server
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(emailBody);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            Validator.messageBox(e + "\n" + e.getMessage(), "Error");
            throw new RuntimeException(e);
        }
    }

}

