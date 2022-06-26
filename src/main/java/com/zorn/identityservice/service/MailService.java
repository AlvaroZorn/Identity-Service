package com.zorn.identityservice.service;

import com.zorn.identityservice.exception.IdentityServiceException;
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
    public void sendMail(Context context, String template, String to, String subject) {

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(systemEmail);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(templateEngine.process(template, context), true);
        };

        try {
            mailSender.send(messagePreparator);
            log.info("Email was successfully sent!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new IdentityServiceException("Exception occurred when sending mail to " + to, e);
        }

    }
}
