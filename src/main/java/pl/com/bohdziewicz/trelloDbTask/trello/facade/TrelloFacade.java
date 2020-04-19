package pl.com.bohdziewicz.trelloDbTask.trello.facade;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.com.bohdziewicz.trelloDbTask.domain.CreatedTrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoard;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCard;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.mapper.TrelloMapper;
import pl.com.bohdziewicz.trelloDbTask.service.TrelloService;
import pl.com.bohdziewicz.trelloDbTask.trello.client.BoardNotFoundException;

public class TrelloFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloFacade.class);
    private TrelloService trelloService;
    private TrelloMapper trelloMapper;

    TrelloFacade(TrelloService trelloService, TrelloMapper trelloMapper) {

        this.trelloService = trelloService;
        this.trelloMapper = trelloMapper;
    }

    //TODO: zdecydować się czy zwracamy listę czy tablicę i to ujednolicić!
    //Fasada zwraca listę, a trello service z jakiegoś powodu (nie pamiętam jakiego) tablicę.

    public List<TrelloBoard> fetchTrelloBoard() throws BoardNotFoundException {

        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(Arrays.asList(trelloService.fetchTrelloBoardsDto()));
        LOGGER.info("Starting filtering boards...");
        List<TrelloBoard> filteredBoards = trelloBoards
                .stream()
                .filter(trelloBoard -> !trelloBoard.getName().equalsIgnoreCase("test"))
                .collect(Collectors.toList());
        LOGGER.info("Boards have been filtered. Current list size: " + filteredBoards.size());
        return filteredBoards;
    }

    public CreatedTrelloCardDto createdTrelloCardDto(final TrelloCardDto trelloCardDto) {

        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        if (trelloCard.getName().contains("test")) {
            LOGGER.info("Someone is testing my application.");
        } else {
            LOGGER.info("Just new card was created");
        }
        return trelloService.createTrelloCardDto(trelloMapper.mapToCardDto(trelloCard));
    }
}
