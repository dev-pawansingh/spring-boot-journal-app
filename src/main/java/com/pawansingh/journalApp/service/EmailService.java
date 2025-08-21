package com.pawansingh.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    /// java mail sender bean is null until we write some code in .yml file
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String body){
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom("developerpawansingh108@gmail.com");
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);
        }catch (Exception e){
            log.error("Exception while sending mail "+ e);
        }
    }



}
