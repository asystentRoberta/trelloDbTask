package pl.com.bohdziewicz.trelloDbTask.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoard;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloBoardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCard;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloCardDto;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloList;
import pl.com.bohdziewicz.trelloDbTask.domain.TrelloListDto;

import static java.util.stream.Collectors.toList;

@Component
public class TrelloMapper {

    public List<TrelloBoard> mapToBoards(final List<TrelloBoardDto> listOfTrelloBoardsDTO) {

        return listOfTrelloBoardsDTO.stream()
                .map(trelloBoardDTO ->
                        new TrelloBoard(trelloBoardDTO.getId(), trelloBoardDTO.getName(),
                                mapToList(trelloBoardDTO.getTrelloListDtos())))
                .collect(toList());
    }

    public List<TrelloBoardDto> mapToBoardDto(final List<TrelloBoard> trelloBoards) {

        return trelloBoards
                .stream()
                .map(trelloBoard -> new TrelloBoardDto(trelloBoard.getId(), trelloBoard.getName(),
                        mapToListDto(trelloBoard.getLists())))
                .collect(toList());
    }

    private List<TrelloListDto> mapToListDto(final List<TrelloList> trelloLists) {

        return trelloLists
                .stream()
                .map(trelloList -> new TrelloListDto(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                .collect(toList());
    }

    private List<TrelloList> mapToList(final List<TrelloListDto> trelloListDtos) {

        return trelloListDtos.stream()
                .map(trelloList -> new TrelloList(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                .collect(toList());
    }

    public TrelloCardDto mapToCardDto(final TrelloCard trelloCard) {

        return new TrelloCardDto(trelloCard.getName(), trelloCard.getDescription(), trelloCard.getPos(),
                trelloCard.getListId());
    }

    public TrelloCard mapToCard(final TrelloCardDto trelloCardDto) {

        return new TrelloCard(trelloCardDto.getName(), trelloCardDto.getDescription(), trelloCardDto.getPos(),
                trelloCardDto.getListId());
    }
}
