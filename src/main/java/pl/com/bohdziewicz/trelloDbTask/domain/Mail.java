package pl.com.bohdziewicz.trelloDbTask.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.com.bohdziewicz.trelloDbTask.utils.MailTypes;

@Getter @AllArgsConstructor
public class Mail {

    private final String mailTo;
    private final String subject;
    private final String message;
    private final MailTypes mailTypes;
}
