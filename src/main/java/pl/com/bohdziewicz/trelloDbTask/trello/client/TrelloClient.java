package pl.com.bohdziewicz.trelloDbTask.trello.client;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import pl.com.bohdziewicz.trelloDbTask.domain.CreatedTrelloCard;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.trello.config.TrelloConfig;

@Component
public class TrelloClient {

    private final RestTemplate restTemplate;
    private final TrelloConfig trelloConfig;

    @Autowired
    public TrelloClient(RestTemplate restTemplate, TrelloConfig trelloConfig) {

        this.restTemplate = restTemplate;
        this.trelloConfig = trelloConfig;
    }

    public TrelloBoardDto[] getTrelloBoard() throws BoardNotFoundException {

        URI url = getUri();

        return Optional.ofNullable(restTemplate.getForObject(url, TrelloBoardDto[].class))
                .orElseThrow(BoardNotFoundException::new);
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {

        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloAppToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);
    }

    private URI getUri() {

        return UriComponentsBuilder
                .fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/" + trelloConfig.getTrelloUsername() +
                        "/boards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloAppToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build()
                .encode()
                .toUri();
    }
}
