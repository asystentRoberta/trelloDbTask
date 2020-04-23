package pl.com.bohdziewicz.trelloDbTask.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import pl.com.bohdziewicz.trelloDbTask.config.MailConfig;
import pl.com.bohdziewicz.trelloDbTask.domain.Mail;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;
    @Mock
    private JavaMailSenderImpl javaMailSender;
    @Mock
    private MailConfig mailConfig;

    @Test
    public void shouldSenEmail() {

        //Given
        Mail mail = new Mail("test@test.com", "Test", "Test Message");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        //When
        simpleEmailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
        verify(mailConfig, atLeast(1)).getMailPassword();
        verify(mailConfig, atLeast(1)).getMailSender();
    }
}