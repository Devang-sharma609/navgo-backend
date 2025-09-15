/**
 * 
 */
package com.navgo.service;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

import com.navgo.util.OTPService;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
/**
 * @author Akash Bais
 *
 */
public class EmailService {	


    private final String username = "akashbais41203";
    private final String password = "fzje mctr qifp krsj";

    public void sendEmail(String to, String subject, String htmlContent) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        // session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            ExecutorService executorService = Executors.newCachedThreadPool();

            executorService.submit(() -> {
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
                    message.setFrom(new InternetAddress(username));
                    message.setSubject(subject);
                    message.setContent(htmlContent,"text/html");

                    Transport.send(message);
                    System.out.println("Email sent!!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            executorService.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
