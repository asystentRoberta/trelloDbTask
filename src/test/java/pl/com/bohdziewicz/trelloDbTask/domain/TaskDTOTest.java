package pl.com.bohdziewicz.trelloDbTask.domain;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskDTOTest {

    private final Long smallestLong = (long) (Math.pow(2, 63) * -1);
    private final TaskDTO taskDTO = new TaskDTO(smallestLong, "title A", "content A");

    @Test
    public void getId() {

        assertEquals(-9223372036854775808L, taskDTO.getId());
    }

    @Test
    public void getTitle() {

        assertEquals("title A", taskDTO.getTitle());
    }

    @Test
    public void getContent() {

        assertEquals("content A", taskDTO.getContent());
    }
}