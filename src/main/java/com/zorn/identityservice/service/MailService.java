package com.zorn.identityservice.service;

import com.zorn.identityservice.exception.IdentityServiceException;
import com.zorn.identityservice.model.User;
import com.zorn.identityservice.model.VerificationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${email.systemEmail}")
    private String systemEmail;

    public MailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendRegistrationMail(User user, VerificationToken verificationToken) {
        Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("verificationLink", "http://localhost:8080/api/v1/auth/accountVerification/" + verificationToken.getToken());

        String body = templateEngine.process("mail-templates/registration", context);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(systemEmail);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Please Activate your Account");
            messageHelper.setText(body, true);
        };

        try {
            mailSender.send(messagePreparator);
            log.info("Email was successfully sent!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new IdentityServiceException("Exception occurred when sending mail to " + user.getEmail(), e);
        }
    }
}
