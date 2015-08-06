package com.example.jonas.shoppinglist.communication;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailSender {

    private String body;

    public void send(){
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("devylder3@outlook.com", "Shopping list guru"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("jonas.audenaert@gmail.com", "boss"));
            message.setSubject("test message");
            message.setText("test");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
