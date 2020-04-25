package pl.com.bohdziewicz.trelloDbTask.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoard;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCard;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloList;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloListDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TrelloMapperTest {

    private TrelloMapper trelloMapper = new TrelloMapper();
    private List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
    private List<TrelloListDto> trelloListDtoList = new ArrayList<>();
    private List<TrelloBoard> trelloBoards = new ArrayList<>();
    private List<TrelloList> trelloLists = new ArrayList<>();
    private TrelloCardDto trelloCardDto =
            new TrelloCardDto("DtoCardName", "DtoCardDescription", "DtoCardPos", "DtoCardListId");
    private TrelloCard trelloCard = new TrelloCard("CardName", "CardDescription", "CardPos", "CardListId");

    @Before
    public void setup() {

        trelloListDtoList.add(new TrelloListDto("1L", "listOnDtoBoard", false));
        trelloBoardDtoList.add(new TrelloBoardDto("1", "testDTOBoard", trelloListDtoList));

        trelloLists.add(new TrelloList("2L", "listOnBoard", false));
        trelloBoards.add(new TrelloBoard("2", "testBoard", trelloLists));
    }

    @Test
    public void shouldReturnListOfTrelloBoardWithListTrelloList() {

        final List<TrelloBoard> trelloBoardsAfterMap = trelloMapper.mapToBoards(trelloBoardDtoList);
        assertEquals(1, trelloBoards.size());
        assertEquals(1, trelloBoardsAfterMap.get(0).getLists().size());
        assertEquals("1", trelloBoardsAfterMap.get(0).getId());
        assertEquals("testDTOBoard", trelloBoardsAfterMap.get(0).getName());
        assertEquals("1L", trelloBoardsAfterMap.get(0).getLists().get(0).getId());
        assertEquals("listOnDtoBoard", trelloBoardsAfterMap.get(0).getLists().get(0).getName());
        assertFalse(trelloBoardsAfterMap.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void shouldReturnListOfTrelloBoardDTOwithListTrelloListDTO() {

        final List<TrelloBoardDto> trelloBoardDtoListAfterMap = trelloMapper.mapToBoardDto(trelloBoards);

        assertEquals(1, trelloBoardDtoListAfterMap.size());
        assertEquals(1, trelloBoardDtoListAfterMap.get(0).getTrelloListDtos().size());
        assertEquals("2", trelloBoardDtoListAfterMap.get(0).getId());
        assertEquals("testBoard", trelloBoardDtoListAfterMap.get(0).getName());
        assertEquals("listOnBoard", trelloBoardDtoListAfterMap.get(0).getTrelloListDtos().get(0).getName());
        assertEquals("2L", trelloBoardDtoListAfterMap.get(0).getTrelloListDtos().get(0).getId());
        assertFalse(trelloBoardDtoListAfterMap.get(0).getTrelloListDtos().get(0).isClosed());
    }

    @Test
    public void shouldMapDTOCardToDomainCard() {

        final TrelloCard trelloCardAfterMap = trelloMapper.mapToCard(trelloCardDto);

        assertEquals("DtoCardName", trelloCardAfterMap.getName());
        assertEquals("DtoCardDescription", trelloCardAfterMap.getDescription());
        assertEquals("DtoCardPos", trelloCardAfterMap.getPos());
        assertEquals("DtoCardListId", trelloCardAfterMap.getListId());
        assertNotNull(trelloCardAfterMap);
    }

    @Test
    public void shouldMapCardToDTOCard() {

        final TrelloCardDto trelloCardDtoAfterMap = trelloMapper.mapToCardDto(trelloCard);

        assertEquals("CardName", trelloCardDtoAfterMap.getName());
        assertEquals("CardDescription", trelloCardDtoAfterMap.getDescription());
        assertEquals("CardPos", trelloCardDtoAfterMap.getPos());
        assertEquals("CardListId", trelloCardDtoAfterMap.getListId());
        assertNotNull(trelloCardDtoAfterMap);
    }
}