package pl.com.bohdziewicz.trelloDbTask.service;

import org.springframework.stereotype.Service;

import pl.com.bohdziewicz.trelloDbTask.config.AdminConfig;
import pl.com.bohdziewicz.trelloDbTask.domain.CreatedTrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.Mail;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.trello.client.BoardNotFoundException;
import pl.com.bohdziewicz.trelloDbTask.trello.client.TrelloClient;

import static java.util.Optional.ofNullable;

@Service
public class TrelloService {

    private static final String SUBJECT = "Tasks: New Trello card";
    private final TrelloClient trelloClient;
    private final SimpleEmailService emailService;
    private final AdminConfig adminConfig;

    public TrelloService(SimpleEmailService emailService, TrelloClient trelloClient, AdminConfig adminConfig) {

        this.emailService = emailService;
        this.trelloClient = trelloClient;
        this.adminConfig = adminConfig;
    }

    public TrelloBoardDto[] fetchTrelloBoardsDto() throws BoardNotFoundException {

        return trelloClient.getTrelloBoard();
    }

    public CreatedTrelloCardDto createTrelloCardDto(final TrelloCardDto trelloCardDto) {

        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        ofNullable(newCard).ifPresent(card ->
                emailService.send(new Mail(
                        adminConfig.getAdminMail(),
                        SUBJECT,
                        "New card: " + trelloCardDto.getName() + " has been created on your Trello account")
                )
        );
        return newCard;
    }
}

