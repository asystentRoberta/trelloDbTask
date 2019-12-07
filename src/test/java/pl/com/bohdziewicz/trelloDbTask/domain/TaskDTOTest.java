package pl.com.bohdziewicz.trelloDbTask.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskDTOTest {

    private Long smallestLong = (long) (Math.pow(2, 63) * -1);
    private TaskDTO taskDTO = new TaskDTO(smallestLong, "title A", "content A");

    @Test
    void getId() {

        assertEquals(-9223372036854775808L, taskDTO.getId());
    }

    @Test
    void getTitle() {

        assertEquals("title A", taskDTO.getTitle());
    }

    @Test
    void getContent() {

        assertEquals("content A", taskDTO.getContent());
    }
}