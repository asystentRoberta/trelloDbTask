package pl.com.bohdziewicz.trelloDbTask.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    private Long biggestLong = (long) Math.pow(2, 63);
    private Task task = new Task(biggestLong, "testTitle", "testContent");

    @Test
    void getId() {

        assertEquals(9223372036854775807L, task.getId());
    }

    @Test
    void getTitle() {

        assertEquals("testTitle", task.getTitle());
    }

    @Test
    void getContent() {

        assertEquals("testContent", task.getContent());
    }
}