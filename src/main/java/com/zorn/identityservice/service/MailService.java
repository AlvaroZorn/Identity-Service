package com.zorn.identityservice.service;

import com.zorn.identityservice.exception.IdentityServiceException;
import com.zorn.identityservice.model.Email;
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
    public void sendMail(Email email) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(systemEmail);
            messageHelper.setTo(email.getTo());
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(email.getText());
        };

        try {
            mailSender.send(messagePreparator);
            log.info("Email was successfully sent!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new IdentityServiceException("Exception occurred when sending mail to " + email.getTo(), e);
        }
    }

    public String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
    }
}
