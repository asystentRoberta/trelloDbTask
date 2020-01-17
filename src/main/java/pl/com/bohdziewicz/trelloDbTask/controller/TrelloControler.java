package pl.com.bohdziewicz.trelloDbTask.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;
import pl.com.bohdziewicz.trelloDbTask.trello.client.TrelloClient;

@RestController
@RequestMapping("trello")
public class TrelloControler {

    @Autowired
    TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public void getTrelloBoards() {

        List<TrelloBoardDto> trelloBoardDtos = trelloClient.getTrelloBoard();

        trelloBoardDtos
                .forEach(trelloBoardDto ->
                        System.out.println(trelloBoardDto.getId()
                                + " "
                                + trelloBoardDto.getName()));
    }
}
