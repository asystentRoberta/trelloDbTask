package pl.com.bohdziewicz.trelloDbTask.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.com.bohdziewicz.trelloDbTask.domain.CreatedTrelloCard;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.service.TrelloService;
import pl.com.bohdziewicz.trelloDbTask.trello.client.BoardNotFoundException;

@RestController
@RequestMapping("trello")

@CrossOrigin(origins = "*")

public class TrelloControler {

    private final TrelloService trelloService;

    @Autowired
    public TrelloControler(TrelloService trelloService) {

        this.trelloService = trelloService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {

        List<TrelloBoardDto> trelloBoardDtos = null;
        try {
            trelloBoardDtos = Arrays.asList(trelloService.fetchTrelloBoardsDto());
        } catch (BoardNotFoundException e) {
            e.printStackTrace();
        }
        return trelloBoardDtos;
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTrelloCard")
    public CreatedTrelloCard createdTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {

        return trelloService.createTrelloCard(trelloCardDto);
    }
}
