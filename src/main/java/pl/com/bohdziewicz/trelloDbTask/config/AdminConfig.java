package pl.com.bohdziewicz.trelloDbTask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class AdminConfig {

    @Value("${admin.mail}")
    private String adminMail;
    @Value("${admin.name}")
    private String adminName;
}
