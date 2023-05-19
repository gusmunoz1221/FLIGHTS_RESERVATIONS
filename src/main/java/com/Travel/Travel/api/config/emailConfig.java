package com.Travel.Travel.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

//@Configuration -----------se puede configurar de esta forma o en el priperties---------------
public class emailConfig {
/*
    @Bean
    public JavaMailSender getJavaMailSender() {
         JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
         mailSender.setHost("smtp.gmail.com");
         mailSender.setPort(584);
         mailSender.setUsername("Best Travel");
         mailSender.setPassword("vnvctavzvpjslqxn");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.debug","true");

        return mailSender;
    }

 */
}
