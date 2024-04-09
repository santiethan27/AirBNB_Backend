//Dev Duque
package com.airbnb.airbnb.servicies;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(String to, String subject, String text,String from) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("emanuelduque096@gmail.com");
        message.setFrom("sierracardozocarlosalexis@gmail.com");
        message.setSubject("Ay mamma");
        message.setText("Esta es la prueba del correo");
        emailSender.send(message);
    }
}
