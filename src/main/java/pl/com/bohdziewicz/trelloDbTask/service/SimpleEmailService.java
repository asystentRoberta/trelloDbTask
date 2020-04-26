package pl.com.bohdziewicz.trelloDbTask.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import pl.com.bohdziewicz.trelloDbTask.config.MailConfig;
import pl.com.bohdziewicz.trelloDbTask.domain.Mail;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);
    private final JavaMailSenderImpl javaMailSender;
    private final MailConfig mailConfig;
    private final MailCreatorService mailCreatorService;

    public SimpleEmailService(JavaMailSenderImpl javaMailSender, MailConfig mailConfig,
            MailCreatorService mailCreatorService) {

        this.mailConfig = mailConfig;
        this.javaMailSender = javaMailSender;
        this.mailCreatorService = mailCreatorService;
        javaMailSender.setUsername(mailConfig.getMailSender());
        javaMailSender.setPassword(mailConfig.getMailPassword());
    }

    public void send(final Mail mail) {

        LOGGER.info("Starting email preparation...");

        try {
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("Email has been sent.");
        } catch (MailException exception) {
            LOGGER.error("Failed to process email sending: " + exception.getMessage(), exception);
        }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail) {

        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    private SimpleMailMessage createSimpleMailMessageWithoutTemplates(final Mail mail) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()));
        return mailMessage;
    }
}
