package com.Travel.Travel.infraestructure.helpers;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Component
public class EmailHelper {
    private final JavaMailSender mailSender;
    //ruta del archivo html
    private final Path TEMPLATE_PATH = Paths.get("src/main/resources/email/email_template.html");

    public EmailHelper(JavaMailSender javaMailSender) {
        this.mailSender = javaMailSender;
    }

    public void sendMail(String to, String name, String product){
        MimeMessage message = mailSender.createMimeMessage();
        String htmlContent = readHTMLTemplate(name, product);
        try {
            message.setFrom(new InternetAddress("gustavito12217@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO,to);
            message.setContent(htmlContent, MediaType.TEXT_HTML_VALUE);//ENUMERADOR QUE DEVUEVE LA RUTA "text/html; charset=utf-8"
            mailSender.send(message);
        }catch (MessagingException e){}
    }

    private String readHTMLTemplate(String name, String product){
     try(var lines = Files.lines(TEMPLATE_PATH)){
         var html = lines.collect(Collectors.joining());
            return html.replace("{name}",name).replace("{product}",product);
     }catch (IOException e){
         throw new RuntimeException();
     }
    }
}