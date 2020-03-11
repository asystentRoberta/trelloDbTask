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
import pl.com.bohdziewicz.trelloDbTask.trello.client.BoardNotFoundException;
import pl.com.bohdziewicz.trelloDbTask.trello.client.TrelloClient;

@RestController
@RequestMapping("trello")

@CrossOrigin(origins = "*")

public class TrelloControler {

    private final TrelloClient trelloClient;

    @Autowired
    public TrelloControler(TrelloClient trelloClient) {

        this.trelloClient = trelloClient;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {

        List<TrelloBoardDto> trelloBoardDtos = null;
        try {
            trelloBoardDtos = Arrays.asList(trelloClient.getTrelloBoard());
        } catch (BoardNotFoundException e) {
            e.printStackTrace();
        }
        return trelloBoardDtos;
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTrelloCard")
    public CreatedTrelloCard createdTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {

        return trelloClient.createNewCard(trelloCardDto);
    }
}
