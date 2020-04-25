package pl.com.bohdziewicz.trelloDbTask.trello.facade;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoard;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloList;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloListDto;
import pl.com.bohdziewicz.trelloDbTask.mapper.TrelloMapper;
import pl.com.bohdziewicz.trelloDbTask.service.TrelloService;
import pl.com.bohdziewicz.trelloDbTask.trello.validator.TrelloValidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest_secondTest_AllMocks {

    @InjectMocks
    TrelloFacade trelloFacade;
    @Mock
    TrelloService trelloService;
    @Mock
    TrelloMapper trelloMapper;
    @Mock
    TrelloValidator trelloValidator;

    @Test
    public void mocksAreTricky() {

        List<TrelloListDto> trelloListDtosList = new ArrayList<>();
        trelloListDtosList.add(new TrelloListDto("1_List_dto", "test_list_dto", false));
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("1_board_dto", "test_board_dto", trelloListDtosList));
        List<TrelloList> mappedTrelloListsList = new ArrayList<>();
        mappedTrelloListsList.add(new TrelloList("1_List_dto", "test_list_dto", false));
        List<TrelloBoard> mappedTrelloBoardList = new ArrayList<>();
        mappedTrelloBoardList.add(new TrelloBoard("1_board_dto", "test_board_dto", mappedTrelloListsList));

        when(trelloService.fetchTrelloBoardsDto()).thenReturn(trelloBoardDtoList);
        when(trelloMapper.mapToBoards(trelloBoardDtoList)).thenReturn(mappedTrelloBoardList);
        when(trelloMapper.mapToBoardDto(anyList())).thenReturn(trelloBoardDtoList);
        when(trelloValidator.validateTrelloBoard(mappedTrelloBoardList)).thenReturn(mappedTrelloBoardList);

        //When
        List<TrelloBoardDto> trelloBoardDtoListToCheck = trelloFacade.fetchTrelloBoard();

        //Then
        assertNotNull(trelloBoardDtoListToCheck);
        assertEquals(1, trelloBoardDtoListToCheck.size());
    }
}