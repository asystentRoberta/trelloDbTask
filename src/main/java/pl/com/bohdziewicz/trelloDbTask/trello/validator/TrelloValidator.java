package pl.com.bohdziewicz.trelloDbTask.trello.validator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoard;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCard;

import static java.util.stream.Collectors.toList;

@Component
public class TrelloValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloValidator.class);

    public void validateCard(final TrelloCard trelloCard) {

        if (trelloCard.getName().toLowerCase().contains("test")) {
            LOGGER.info("Someone is testing my app.");
        } else {
            LOGGER.info("Someone just created new trello card");
        }
    }

    public List<TrelloBoard> validateTrelloBoard(final List<TrelloBoard> trelloBoards) {

        LOGGER.info("Starting filtering boards... Current size (before filter) is " + trelloBoards.size());
        List<TrelloBoard> filteredBoards = trelloBoards
                .stream()
                .filter(trelloBoard -> !trelloBoard.getName().equalsIgnoreCase("test"))
                .collect(toList());
        LOGGER.info("Boards has been filtered. Current list size is " + filteredBoards.size());
        return filteredBoards;
    }
}
