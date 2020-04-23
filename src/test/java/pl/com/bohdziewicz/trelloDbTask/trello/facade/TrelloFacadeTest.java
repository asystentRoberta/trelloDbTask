package pl.com.bohdziewicz.trelloDbTask.trello.facade;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import pl.com.bohdziewicz.trelloDbTask.domain.BadgesFromCardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.CreatedTrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloListDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {

    @Mock
    private TrelloFacade trelloFacade;

    @Test
    public void shouldFetchEmptyList() {

        //When
        List<TrelloBoardDto> trelloBoardDtoList = trelloFacade.fetchTrelloBoard();

        //Then
        assertNotNull(trelloBoardDtoList);
        assertEquals(0, trelloBoardDtoList.size());
    }

    @Test
    public void shouldFetchTrelloBoards() {

        //Given
        List<TrelloListDto> trelloListDtosList = new ArrayList<>();
        trelloListDtosList.add(new TrelloListDto("1_List_dto", "test_list_dto", false));
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("1_board_dto", "test_board_dto", trelloListDtosList));

        when(trelloFacade.fetchTrelloBoard()).thenReturn(trelloBoardDtoList);

        List<TrelloBoardDto> trelloBoardDtoListToCheck = trelloFacade.fetchTrelloBoard();

        assertNotNull(trelloBoardDtoListToCheck);
        assertEquals(1, trelloBoardDtoListToCheck.size());
        assertEquals(1, trelloBoardDtoListToCheck.get(0).getTrelloListDtos().size());

        trelloBoardDtoListToCheck.forEach(trelloBoardDto -> {
            assertEquals("1_board_dto", trelloBoardDto.getId());
            assertEquals("test_board_dto", trelloBoardDto.getName());
            trelloBoardDto.getTrelloListDtos().forEach(trelloListDto -> {
                assertEquals("1_List_dto", trelloListDto.getId());
                assertEquals("test_list_dto", trelloListDto.getName());
                assertFalse(trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void shouldCreateTrelloCard() {

        //Given
        CreatedTrelloCardDto createdTrelloCardDtoMock = new CreatedTrelloCardDto("Id", "testCardDto_name", "shortUrl",
                new BadgesFromCardDto());

        //when
        when(trelloFacade.createdTrelloCardDto(any())).thenReturn(createdTrelloCardDtoMock);
        final CreatedTrelloCardDto createdTrelloCardDto =
                trelloFacade.createdTrelloCardDto(new TrelloCardDto("testCardDto_name", "testCardDto_des", "top",
                        "testList"));

        //Then
        assertNotNull(createdTrelloCardDto);
        assertEquals("testCardDto_name", createdTrelloCardDto.getName());
        assertEquals("Id", createdTrelloCardDto.getId());
        assertEquals("shortUrl", createdTrelloCardDto.getShortUrl());
        assertSame(BadgesFromCardDto.class, createdTrelloCardDto.getBadgesFromCardDto().getClass());
    }
}