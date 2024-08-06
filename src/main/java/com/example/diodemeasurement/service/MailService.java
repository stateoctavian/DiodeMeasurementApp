package com.example.diodemeasurement.service;


import com.example.diodemeasurement.exception.DiodeMeasurementException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendMailNoTrapMail(String toEmail,
                                   String subject,
                                   String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("stateoctavian22@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        try{
            mailSender.send(message);
            log.info("Activation email sent!");

        } catch (MailException e){
            log.error("Exception occurred when sending mail", e);
            throw  new DiodeMeasurementException("Exception occurred when sending mail to  " + toEmail, e);
        }



    }
}
