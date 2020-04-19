package pl.com.bohdziewicz.trelloDbTask.facade;

import java.util.Arrays;
import java.util.List;

import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoard;
import pl.com.bohdziewicz.trelloDbTask.mapper.TrelloMapper;
import pl.com.bohdziewicz.trelloDbTask.service.TrelloService;
import pl.com.bohdziewicz.trelloDbTask.trello.client.BoardNotFoundException;

public class TrelloFacade {

    private TrelloService trelloService;
    private TrelloMapper trelloMapper;

    TrelloFacade(TrelloService trelloService, TrelloMapper trelloMapper) {

        this.trelloService = trelloService;
        this.trelloMapper = trelloMapper;
    }

    public List<TrelloBoard> fetchTrelloBoard() throws BoardNotFoundException {

        return trelloMapper.mapToBoards(Arrays.asList(trelloService.fetchTrelloBoardsDto()));
    }
}
