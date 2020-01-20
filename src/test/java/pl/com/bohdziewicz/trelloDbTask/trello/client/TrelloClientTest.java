package pl.com.bohdziewicz.trelloDbTask.trello.client;

import java.net.URI;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.trello.config.TrelloConfig;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @InjectMocks private TrelloClient trelloClient;
    @Mock private RestTemplate restTemplate;
    @Mock private TrelloConfig trelloConfig;
    @Mock private TrelloBoardDto trelloBoardDto;
    @Mock private TrelloCardDto trelloCardDto;

    @Before
    public void init() {

        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloUsername()).thenReturn("robertb57");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloAppToken()).thenReturn("test");
    }

    @Test
    public void shouldFetchTrelloBoards() throws BoardNotFoundException {

        // Given
        TrelloBoardDto[] trelloBoardDtos = new TrelloBoardDto[1];
        trelloBoardDtos[0] = new TrelloBoardDto("testId", "testBoard", new ArrayList<>());
        when(restTemplate.getForObject(getUrlForGettingTrelloBoards(), TrelloBoardDto[].class))
                .thenReturn(trelloBoardDtos);

        // When
        TrelloBoardDto[] fetchedTrelloBoards = trelloClient.getTrelloBoard();

        // Then
        assertEquals(1, fetchedTrelloBoards.length);
        assertEquals("testId", fetchedTrelloBoards[0].getId());
        assertEquals("testBoard", fetchedTrelloBoards[0].getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards[0].getTrelloListDtos());
    }

    private URI getUrlForGettingTrelloBoards() {

        return UriComponentsBuilder.fromHttpUrl(
                trelloConfig.getTrelloApiEndpoint()
                        + "/members/"
                        + trelloConfig.getTrelloUsername()
                        + "/boards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloAppToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build()
                .encode()
                .toUri();
    }
}
