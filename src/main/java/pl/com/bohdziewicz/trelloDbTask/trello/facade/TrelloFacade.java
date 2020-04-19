package pl.com.bohdziewicz.trelloDbTask.trello.facade;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.com.bohdziewicz.trelloDbTask.domain.CreatedTrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoard;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCard;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.mapper.TrelloMapper;
import pl.com.bohdziewicz.trelloDbTask.service.TrelloService;
import pl.com.bohdziewicz.trelloDbTask.trello.validator.TrelloValidator;

@Component
public class TrelloFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloFacade.class);
    private TrelloService trelloService;
    private TrelloMapper trelloMapper;
    private TrelloValidator trelloValidator;

    TrelloFacade(TrelloService trelloService, TrelloMapper trelloMapper, TrelloValidator trelloValidator) {

        this.trelloService = trelloService;
        this.trelloMapper = trelloMapper;
        this.trelloValidator = trelloValidator;
    }

    public List<TrelloBoardDto> fetchTrelloBoard() {

        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(Arrays.asList(trelloService.fetchTrelloBoardsDto()));
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoard(trelloBoards);
        return trelloMapper.mapToBoardDto(filteredBoards);
    }

    public CreatedTrelloCardDto createdTrelloCardDto(final TrelloCardDto trelloCardDto) {

        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        trelloValidator.validateCard(trelloCard);
        return trelloService.createTrelloCardDto(trelloMapper.mapToCardDto(trelloCard));
    }
}
