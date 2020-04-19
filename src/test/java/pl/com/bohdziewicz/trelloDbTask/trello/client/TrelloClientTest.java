package pl.com.bohdziewicz.trelloDbTask.trello.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import pl.com.bohdziewicz.trelloDbTask.domain.BadgesFromCardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.CreatedTrelloCardDto;
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

  @Test
  public void shouldCreateCard() throws URISyntaxException {

    // Given

    URI uri =
            new URI(
                    "http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20description&pos=top"
                            + "&idList=test_id");
    TrelloCardDto trelloCardDto =
            new TrelloCardDto("Test task", "Test description", "top", "test_id");

      CreatedTrelloCardDto createdTrelloCardDto =
              new CreatedTrelloCardDto("1", "Test task", "http://test.com", new BadgesFromCardDto());

      when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class))
              .thenReturn(createdTrelloCardDto);

    // When
      CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

    // Then
    assertEquals("1", newCard.getId());
    assertEquals("Test task", newCard.getName());
    assertEquals("http://test.com", newCard.getShortUrl());
    assertEquals(new BadgesFromCardDto().getVotes(), newCard.getBadgesFromCardDto().getVotes());
  }

  @Test(expected = BoardNotFoundException.class)
  public void shouldThrowBoardNoFoundException() throws URISyntaxException, BoardNotFoundException {

    // Given
    URI url = new URI("http://badurl.com");

    // When
    trelloClient.getTrelloBoard();
    System.out.println("ui");
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
