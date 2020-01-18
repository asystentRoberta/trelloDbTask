package pl.com.bohdziewicz.trelloDbTask.trello.client;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;

@Component
public class TrelloClient {

    private final RestTemplate restTemplate;
    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloAppToken;
    @Value("${trello.app.username}")
    private String trelloUsername;

    @Autowired
    public TrelloClient(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    public TrelloBoardDto[] getTrelloBoard() throws BoardNotFoundException {

        URI url = getUri();

        return Optional.ofNullable(restTemplate.getForObject(url, TrelloBoardDto[].class))
                .orElseThrow(BoardNotFoundException::new);
    }

    private URI getUri() {

        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUsername + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloAppToken)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build()
                .encode()
                .toUri();
    }
}
