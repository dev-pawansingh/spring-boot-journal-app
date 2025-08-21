package com.pawansingh.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testMailSender(){
        emailService.sendEmail("pawansinghpst108@gmail.com",
                "Test email sender",
                "Hey there!!,\n I am learning spring boot and today I am testing the email sending feature of spring boot");
    }

}
