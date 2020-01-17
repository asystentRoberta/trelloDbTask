package pl.com.bohdziewicz.trelloDbTask.trello.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloAppToken;
    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoard() {

        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/robertb57/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloAppToken)
                .queryParam("fields", "name,id")
                .build()
                .encode()
                .toUri();

        TrelloBoardDto[] boardsRespponse =
                restTemplate.getForObject(url, TrelloBoardDto[].class);

        if (boardsRespponse != null) {
            return Arrays.asList(boardsRespponse);
        }
        return new ArrayList<>();
    }
}
