package pl.com.bohdziewicz.trelloDbTask.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pl.com.bohdziewicz.trelloDbTask.config.AdminConfig;
import pl.com.bohdziewicz.trelloDbTask.domain.CreatedTrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.Mail;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.trello.client.BoardNotFoundException;
import pl.com.bohdziewicz.trelloDbTask.trello.client.TrelloClient;
import pl.com.bohdziewicz.trelloDbTask.utils.MailTypes;

import static java.util.Optional.ofNullable;

@Service
public class TrelloService {

    private static final String SUBJECT = "Tasks: New Trello card";
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloService.class);
    private final TrelloClient trelloClient;
    private final SimpleEmailService emailService;
    private final AdminConfig adminConfig;

    public TrelloService(SimpleEmailService emailService, TrelloClient trelloClient, AdminConfig adminConfig) {

        this.emailService = emailService;
        this.trelloClient = trelloClient;
        this.adminConfig = adminConfig;
    }

    public List<TrelloBoardDto> fetchTrelloBoardsDto() {

        try {
            return trelloClient.getTrelloBoard();
        } catch (BoardNotFoundException e) {
            e.printStackTrace();
            LOGGER.info("Problem with getting boards from trello service.");
        }
        LOGGER.info("TrelloService returned empty array.");
        return new ArrayList<>();
    }

    public CreatedTrelloCardDto createTrelloCardDto(final TrelloCardDto trelloCardDto) {

        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        ofNullable(newCard).ifPresent(card ->
                emailService.send(new Mail(
                        adminConfig.getAdminMail(),
                        MailTypes.EMAIL_NEW_TRELLO_CARD.getSubjectOfMail(),
                        "New card: " + trelloCardDto.getName() + " has been created on your Trello account.",
                        MailTypes.EMAIL_NEW_TRELLO_CARD)
                )
        );
        return newCard;
    }
}

