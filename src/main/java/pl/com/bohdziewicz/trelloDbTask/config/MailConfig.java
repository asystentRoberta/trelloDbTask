package pl.com.bohdziewicz.trelloDbTask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter @Component
public class MailConfig {

    @Value("${spring.mail.username}")
    private String mailSender;
    @Value("${spring.mail.password}")
    private String mailPassword;
}
