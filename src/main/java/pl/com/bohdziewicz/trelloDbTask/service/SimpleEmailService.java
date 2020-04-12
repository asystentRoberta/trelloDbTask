package pl.com.bohdziewicz.trelloDbTask.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import pl.com.bohdziewicz.trelloDbTask.domain.Mail;

@Service
public class SimpleEmailService {

    private final JavaMailSender javaMailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    public SimpleEmailService(JavaMailSender javaMailSender) {

        this.javaMailSender = javaMailSender;
    }

    public void send(final Mail mail) {

        LOGGER.info("Starting email preparation...");

        try {
            javaMailSender.send(createMailMessage(mail));
            LOGGER.info("Email has been sent.");
        } catch (MailException exception) {
            LOGGER.error("Failed to process email sending: " + exception.getMessage(), exception);
        }
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }
}
