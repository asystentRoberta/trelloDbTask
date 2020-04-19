package pl.com.bohdziewicz.trelloDbTask.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.com.bohdziewicz.trelloDbTask.domain.CreatedTrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.trello.facade.TrelloFacade;

@RestController
@RequestMapping("trello")

@CrossOrigin(origins = "*")

public class TrelloControler {

    private final TrelloFacade trelloFacade;

    @Autowired
    public TrelloControler(TrelloFacade trelloFacade) {

        this.trelloFacade = trelloFacade;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {

        return trelloFacade.fetchTrelloBoard();
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTrelloCard")
    public CreatedTrelloCardDto createdTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {

        return trelloFacade.createdTrelloCardDto(trelloCardDto);
    }
}
