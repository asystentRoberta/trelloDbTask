package pl.com.bohdziewicz.trelloDbTask.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class TrelloBoard {

    private String id;
    private String name;
    private List<TrelloList> lists;
}
