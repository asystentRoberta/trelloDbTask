package pl.com.bohdziewicz.trelloDbTask.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import pl.com.bohdziewicz.trelloDbTask.domain.BadgesFromCardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.CreatedTrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloListDto;
import pl.com.bohdziewicz.trelloDbTask.trello.facade.TrelloFacade;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrelloControler.class)
public class TrelloControlerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrelloFacade trelloFacade;

    @Test
    public void shouldFetchEmptyTrelloBoards() throws Exception {

        //Given
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        when(trelloFacade.fetchTrelloBoard()).thenReturn(trelloBoards);

        //When&Then
        mockMvc.perform(get("/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTrelloBoards() throws Exception {

        //Given
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(new TrelloListDto("1list", "Test dto list", false));
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("idBoard", "trello Board", trelloListDtoList));

        when(trelloFacade.fetchTrelloBoard()).thenReturn(trelloBoardDtoList);

        //When & Then
        mockMvc.perform(get("/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("idBoard")))
                .andExpect(jsonPath("$[0].name", is("trello Board")))
                .andExpect(jsonPath("$[0].lists", hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].id", is("1list")))
                .andExpect(jsonPath("$[0].lists[0].name", is("Test dto list")))
                .andExpect(jsonPath("$[0].lists[0].closed", is(false)));
    }

    @Test
    public void shouldCreateTrelloCard() throws Exception {

        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "TestCardDto",
                "Test description of Dto created card",
                "pos",
                "listId_1"
        );

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "createdCard_id",
                "created card name",
                "http://returnedUrlToCard.com",
                new BadgesFromCardDto()
        );

        when(trelloFacade.createdTrelloCardDto(ArgumentMatchers.any(TrelloCardDto.class)))
                .thenReturn(createdTrelloCardDto);

        //When & then
        Gson gson = new Gson();
        String ctreateTrelloCardRequestAsJson = gson.toJson(trelloCardDto);

        mockMvc.perform(post("/trello/createTrelloCard/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(ctreateTrelloCardRequestAsJson))
                .andExpect(jsonPath("$.id", is("createdCard_id")))
                .andExpect(jsonPath("$.name", is("created card name")))
                .andExpect(jsonPath("$.shortUrl", is("http://returnedUrlToCard.com")));
    }
}
