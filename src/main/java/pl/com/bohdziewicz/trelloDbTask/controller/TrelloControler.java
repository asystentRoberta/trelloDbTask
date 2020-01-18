package pl.com.bohdziewicz.trelloDbTask.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;
import pl.com.bohdziewicz.trelloDbTask.trello.client.BoardNotFoundException;
import pl.com.bohdziewicz.trelloDbTask.trello.client.TrelloClient;

@RestController
@RequestMapping("trello")
public class TrelloControler {

    private final TrelloClient trelloClient;

    @Autowired
    public TrelloControler(TrelloClient trelloClient) {

        this.trelloClient = trelloClient;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public void getTrelloBoards() {

        List<TrelloBoardDto> trelloBoardDtos = null;
        try {
            trelloBoardDtos = Arrays.asList(trelloClient.getTrelloBoard());
        } catch (BoardNotFoundException e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(trelloBoardDtos).forEach(trelloBoardDto -> {
            System.out.println(trelloBoardDto.getName() + " - " + trelloBoardDto.getId());
            System.out.println("This board contains lists:");

            trelloBoardDto.getTrelloListDtos().forEach(trelloListDto -> System.out.println(trelloListDto.getName()
                    + " - "
                    + trelloListDto.getId()
                    + " - "
                    + trelloListDto.isClosed()));
        });
    }
}
